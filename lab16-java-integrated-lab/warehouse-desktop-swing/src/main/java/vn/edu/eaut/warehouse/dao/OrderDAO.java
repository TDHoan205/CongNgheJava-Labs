package vn.edu.eaut.warehouse.dao;

import vn.edu.eaut.warehouse.config.DatabaseConnection;
import vn.edu.eaut.warehouse.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    private static final String PLATFORM = "JAVA_SWING";

    public List<Order> findOrdersByStatus(String status) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.id, o.customer_id, u.full_name AS customer_name, " +
                     "o.created_at, o.total_amount, o.status, o.note, o.version " +
                     "FROM orders o " +
                     "JOIN users u ON u.id = o.customer_id " +
                     "WHERE o.status = ? " +
                     "ORDER BY o.created_at ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getLong("id"));
                    order.setCustomerId(rs.getLong("customer_id"));
                    order.setCustomerName(rs.getString("customer_name"));
                    order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                    order.setNote(rs.getString("note"));
                    order.setVersion(rs.getInt("version"));
                    orders.add(order);
                }
            }
        }
        return orders;
    }

    public List<OrderItem> findItemsByOrderId(long orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT id, order_id, product_id, product_name, quantity, unit_price, subtotal " +
                     "FROM order_items WHERE order_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, orderId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getLong("id"));
                    item.setOrderId(rs.getLong("order_id"));
                    item.setProductId(rs.getLong("product_id"));
                    item.setProductName(rs.getString("product_name"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getDouble("unit_price"));
                    item.setSubtotal(rs.getDouble("subtotal"));
                    items.add(item);
                }
            }
        }
        return items;
    }

    public List<OrderStatusHistory> findHistoryByOrderId(long orderId) throws SQLException {
        List<OrderStatusHistory> history = new ArrayList<>();
        String sql = "SELECT id, order_id, old_status, new_status, changed_by, " +
                     "changed_at, platform, note " +
                     "FROM order_status_history WHERE order_id = ? ORDER BY changed_at ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, orderId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderStatusHistory h = new OrderStatusHistory();
                    h.setId(rs.getLong("id"));
                    h.setOrderId(rs.getLong("order_id"));
                    h.setOldStatus(rs.getString("old_status"));
                    h.setNewStatus(rs.getString("new_status"));
                    h.setChangedBy(rs.getLong("changed_by"));
                    h.setChangedAt(rs.getTimestamp("changed_at").toLocalDateTime());
                    h.setPlatform(rs.getString("platform"));
                    h.setNote(rs.getString("note"));
                    history.add(h);
                }
            }
        }
        return history;
    }

    public List<Product> findAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, code, name, price, stock, active, version FROM products WHERE active = true";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setCode(rs.getString("code"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                product.setActive(rs.getBoolean("active"));
                product.setVersion(rs.getInt("version"));
                products.add(product);
            }
        }
        return products;
    }

    public Order findOrderByIdForUpdate(Connection conn, long orderId) throws SQLException {
        String sql = "SELECT id, customer_id, status, version FROM orders WHERE id = ? FOR UPDATE";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getLong("id"));
                    order.setCustomerId(rs.getLong("customer_id"));
                    order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                    order.setVersion(rs.getInt("version"));
                    return order;
                }
            }
        }
        return null;
    }

    public void acceptOrder(long orderId, long employeeId) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Lock and validate order
            Order order = findOrderByIdForUpdate(conn, orderId);
            if (order == null) {
                throw new SQLException("Order not found: " + orderId);
            }
            if (order.getStatus() != OrderStatus.PENDING) {
                throw new SQLException("Order status must be PENDING, current: " + order.getStatus());
            }

            // Get order items
            List<OrderItem> items = findItemsByOrderIdInternal(conn, orderId);

            // Deduct stock for each item with optimistic locking
            String updateStockSql = "UPDATE products SET stock = stock - ?, version = version + 1 " +
                                    "WHERE id = ? AND stock >= ? AND active = true AND version = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateStockSql)) {
                for (OrderItem item : items) {
                    stmt.setInt(1, item.getQuantity());
                    stmt.setLong(2, item.getProductId());
                    stmt.setInt(3, item.getQuantity());
                    stmt.setInt(4, item.getVersion());
                    int affected = stmt.executeUpdate();
                    if (affected == 0) {
                        conn.rollback();
                        throw new SQLException("Failed to update stock for product: " + item.getProductName() +
                                ". Either insufficient stock, product inactive, or version mismatch.");
                    }
                }
            }

            // Update order status with optimistic locking
            String updateOrderSql = "UPDATE orders SET status = 'PROCESSING', version = version + 1 " +
                                   "WHERE id = ? AND status = 'PENDING' AND version = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateOrderSql)) {
                stmt.setLong(1, orderId);
                stmt.setInt(2, order.getVersion());
                int affected = stmt.executeUpdate();
                if (affected == 0) {
                    conn.rollback();
                    throw new SQLException("Order was modified by another process. Please retry.");
                }
            }

            // Insert status history
            String insertHistorySql = "INSERT INTO order_status_history " +
                                      "(order_id, old_status, new_status, changed_by, platform, note) " +
                                      "VALUES (?, 'PENDING', 'PROCESSING', ?, 'JAVA_SWING', 'Kho tiep nhan don')";
            try (PreparedStatement stmt = conn.prepareStatement(insertHistorySql)) {
                stmt.setLong(1, orderId);
                stmt.setLong(2, employeeId);
                stmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public void packOrder(long orderId, long employeeId) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Lock and validate order
            Order order = findOrderByIdForUpdate(conn, orderId);
            if (order == null) {
                throw new SQLException("Order not found: " + orderId);
            }
            if (order.getStatus() != OrderStatus.PROCESSING) {
                throw new SQLException("Order status must be PROCESSING, current: " + order.getStatus());
            }

            // Update order status
            String updateOrderSql = "UPDATE orders SET status = 'READY', version = version + 1 " +
                                   "WHERE id = ? AND status = 'PROCESSING' AND version = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateOrderSql)) {
                stmt.setLong(1, orderId);
                stmt.setInt(2, order.getVersion());
                int affected = stmt.executeUpdate();
                if (affected == 0) {
                    conn.rollback();
                    throw new SQLException("Order was modified by another process. Please retry.");
                }
            }

            // Insert status history
            String insertHistorySql = "INSERT INTO order_status_history " +
                                      "(order_id, old_status, new_status, changed_by, platform, note) " +
                                      "VALUES (?, 'PROCESSING', 'READY', ?, 'JAVA_SWING', 'Da dong goi')";
            try (PreparedStatement stmt = conn.prepareStatement(insertHistorySql)) {
                stmt.setLong(1, orderId);
                stmt.setLong(2, employeeId);
                stmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public void cancelOrder(long orderId, long employeeId, String reason) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Lock and validate order
            Order order = findOrderByIdForUpdate(conn, orderId);
            if (order == null) {
                throw new SQLException("Order not found: " + orderId);
            }
            if (order.getStatus() != OrderStatus.PROCESSING) {
                throw new SQLException("Order status must be PROCESSING to cancel, current: " + order.getStatus());
            }

            // Get order items
            List<OrderItem> items = findItemsByOrderIdInternal(conn, orderId);

            // Restore stock for each item
            String restoreStockSql = "UPDATE products SET stock = stock + ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(restoreStockSql)) {
                for (OrderItem item : items) {
                    stmt.setInt(1, item.getQuantity());
                    stmt.setLong(2, item.getProductId());
                    stmt.executeUpdate();
                }
            }

            // Update order status
            String updateOrderSql = "UPDATE orders SET status = 'CANCELLED', version = version + 1 " +
                                   "WHERE id = ? AND status = 'PROCESSING' AND version = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateOrderSql)) {
                stmt.setLong(1, orderId);
                stmt.setInt(2, order.getVersion());
                int affected = stmt.executeUpdate();
                if (affected == 0) {
                    conn.rollback();
                    throw new SQLException("Order was modified by another process. Please retry.");
                }
            }

            // Insert status history
            String insertHistorySql = "INSERT INTO order_status_history " +
                                      "(order_id, old_status, new_status, changed_by, platform, note) " +
                                      "VALUES (?, 'PROCESSING', 'CANCELLED', ?, 'JAVA_SWING', ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertHistorySql)) {
                stmt.setLong(1, orderId);
                stmt.setLong(2, employeeId);
                stmt.setString(3, reason != null ? reason : "Huy bo");
                stmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    private List<OrderItem> findItemsByOrderIdInternal(Connection conn, long orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT oi.id, oi.order_id, oi.product_id, oi.product_name, oi.quantity, " +
                     "oi.unit_price, oi.subtotal, p.version " +
                     "FROM order_items oi " +
                     "JOIN products p ON p.id = oi.product_id " +
                     "WHERE oi.order_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getLong("id"));
                    item.setOrderId(rs.getLong("order_id"));
                    item.setProductId(rs.getLong("product_id"));
                    item.setProductName(rs.getString("product_name"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getDouble("unit_price"));
                    item.setSubtotal(rs.getDouble("subtotal"));
                    item.setVersion(rs.getInt("version"));
                    items.add(item);
                }
            }
        }
        return items;
    }
}
