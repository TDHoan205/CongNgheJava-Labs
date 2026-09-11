package vn.edu.eaut.warehouse.service;

import vn.edu.eaut.warehouse.dao.OrderDAO;
import vn.edu.eaut.warehouse.dao.UserDAO;
import vn.edu.eaut.warehouse.model.*;

import java.sql.SQLException;
import java.util.List;

public class WarehouseOrderService {

    private static WarehouseOrderService instance;
    private final UserDAO userDAO;
    private final OrderDAO orderDAO;
    private Employee currentEmployee;

    private WarehouseOrderService() {
        this.userDAO = new UserDAO();
        this.orderDAO = new OrderDAO();
    }

    public static synchronized WarehouseOrderService getInstance() {
        if (instance == null) {
            instance = new WarehouseOrderService();
        }
        return instance;
    }

    public Employee getCurrentEmployee() {
        return currentEmployee;
    }

    public void setCurrentEmployee(Employee employee) {
        this.currentEmployee = employee;
    }

    public void clearCurrentEmployee() {
        this.currentEmployee = null;
    }

    // User operations
    public User login(String username, String password) {
        try {
            return userDAO.login(username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Database error during login: " + e.getMessage(), e);
        }
    }

    // Order operations
    public List<Order> findOrdersByStatus(OrderStatus status) {
        try {
            return orderDAO.findOrdersByStatus(status.name());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load orders: " + e.getMessage(), e);
        }
    }

    public List<OrderItem> findItemsByOrderId(long orderId) {
        try {
            return orderDAO.findItemsByOrderId(orderId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load order items: " + e.getMessage(), e);
        }
    }

    public List<OrderStatusHistory> findHistoryByOrderId(long orderId) {
        try {
            return orderDAO.findHistoryByOrderId(orderId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load order history: " + e.getMessage(), e);
        }
    }

    public List<Product> findAllProducts() {
        try {
            return orderDAO.findAllProducts();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load products: " + e.getMessage(), e);
        }
    }

    public void acceptOrder(long orderId) {
        if (currentEmployee == null) {
            throw new RuntimeException("No employee logged in");
        }
        try {
            orderDAO.acceptOrder(orderId, currentEmployee.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to accept order: " + e.getMessage(), e);
        }
    }

    public void packOrder(long orderId) {
        if (currentEmployee == null) {
            throw new RuntimeException("No employee logged in");
        }
        try {
            orderDAO.packOrder(orderId, currentEmployee.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to pack order: " + e.getMessage(), e);
        }
    }

    public void cancelOrder(long orderId, String reason) {
        if (currentEmployee == null) {
            throw new RuntimeException("No employee logged in");
        }
        try {
            orderDAO.cancelOrder(orderId, currentEmployee.getId(), reason);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to cancel order: " + e.getMessage(), e);
        }
    }
}
