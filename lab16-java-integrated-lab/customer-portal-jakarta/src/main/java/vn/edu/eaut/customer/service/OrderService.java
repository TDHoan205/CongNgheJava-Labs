package vn.edu.eaut.customer.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import vn.edu.eaut.customer.config.JPAUtil;
import vn.edu.eaut.customer.dto.CartItem;
import vn.edu.eaut.customer.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for order operations with manual transaction management.
 */
public class OrderService {

    private static final String PLATFORM = "JAKARTA_EE";

    /**
     * Create a new order from cart items.
     * Uses manual transaction management with EntityTransaction.
     *
     * @param customerId the customer ID
     * @param cartItems  the list of cart items
     * @param note       optional order note
     * @return the created order ID
     * @throws IllegalStateException if stock is insufficient or any transaction error
     */
    public Long createOrder(Long customerId, List<CartItem> cartItems, String note) {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart items cannot be empty");
        }

        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        jakarta.persistence.EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Create and persist the order
            Order order = new Order(customerId, note);
            order.setStatus(OrderStatus.PENDING);
            order.setTotalAmount(BigDecimal.ZERO);
            em.persist(order);

            BigDecimal totalAmount = BigDecimal.ZERO;

            // Process each cart item
            for (CartItem cartItem : cartItems) {
                // Load product and check stock
                Product product = em.find(Product.class, cartItem.getProduct().getId());
                if (product == null) {
                    tx.rollback();
                    throw new IllegalStateException("Product not found: " + cartItem.getProduct().getId());
                }

                if (product.getStock() < cartItem.getQuantity()) {
                    tx.rollback();
                    throw new IllegalStateException("Insufficient stock for product: " + product.getName() +
                            ". Available: " + product.getStock() + ", Requested: " + cartItem.getQuantity());
                }

                // Deduct stock
                product.setStock(product.getStock() - cartItem.getQuantity());

                // Create order item
                OrderItem orderItem = new OrderItem(
                        order,
                        product,
                        cartItem.getQuantity(),
                        product.getPrice()
                );
                orderItem.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
                em.persist(orderItem);

                // Add to order's items list
                order.getItems().add(orderItem);

                // Accumulate total
                totalAmount = totalAmount.add(orderItem.getSubtotal());
            }

            // Update order total
            order.setTotalAmount(totalAmount);

            // Create status history entry
            OrderStatusHistory history = new OrderStatusHistory(
                    order.getId(),
                    null,
                    OrderStatus.PENDING.name(),
                    customerId,
                    PLATFORM,
                    "Order created"
            );
            em.persist(history);

            tx.commit();

            return order.getId();

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new IllegalStateException("Failed to create order: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    /**
     * Find all orders for a customer.
     *
     * @param customerId the customer ID
     * @return list of orders
     */
    @SuppressWarnings("unchecked")
    public List<Order> findByCustomer(Long customerId) {
        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            Query query = em.createQuery(
                    "SELECT o FROM Order o WHERE o.customerId = :customerId ORDER BY o.createdAt DESC");
            query.setParameter("customerId", customerId);
            return (List<Order>) query.getResultList();
        }
    }

    /**
     * Find an order by ID with its items initialized.
     *
     * @param orderId the order ID
     * @return the order with items, or null if not found
     */
    public Order findByIdWithItems(Long orderId) {
        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            Order order = em.find(Order.class, orderId);
            if (order != null) {
                // Initialize the items collection
                order.getItems().size();
            }
            return order;
        }
    }

    /**
     * Cancel an order if it's still pending.
     * Restores stock for all items in the order.
     *
     * @param orderId    the order ID
     * @param customerId the customer ID (for verification)
     * @param note       optional cancellation note
     * @throws IllegalStateException if order cannot be cancelled
     */
    public void cancelOrder(Long orderId, Long customerId, String note) {
        if (orderId == null || customerId == null) {
            throw new IllegalArgumentException("Order ID and Customer ID cannot be null");
        }

        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        jakarta.persistence.EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Find order with lock
            Order order = em.find(Order.class, orderId);
            if (order == null) {
                tx.rollback();
                throw new IllegalStateException("Order not found: " + orderId);
            }

            // Verify customer ownership
            if (!order.getCustomerId().equals(customerId)) {
                tx.rollback();
                throw new IllegalStateException("Order does not belong to this customer");
            }

            // Verify status is PENDING
            if (order.getStatus() != OrderStatus.PENDING) {
                tx.rollback();
                throw new IllegalStateException("Only pending orders can be cancelled. Current status: " + order.getStatus());
            }

            // Restore stock for each item
            for (OrderItem item : order.getItems()) {
                Product product = em.find(Product.class, item.getProduct().getId());
                if (product != null) {
                    product.setStock(product.getStock() + item.getQuantity());
                }
            }

            // Update order status
            order.setStatus(OrderStatus.CANCELLED);

            // Create status history entry
            OrderStatusHistory history = new OrderStatusHistory(
                    order.getId(),
                    OrderStatus.PENDING.name(),
                    OrderStatus.CANCELLED.name(),
                    customerId,
                    PLATFORM,
                    note != null ? note : "Order cancelled by customer"
            );
            em.persist(history);

            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new IllegalStateException("Failed to cancel order: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    /**
     * Confirm order receipt when status is SHIPPING.
     *
     * @param orderId    the order ID
     * @param customerId the customer ID (for verification)
     * @throws IllegalStateException if order cannot be confirmed
     */
    public void confirmReceived(Long orderId, Long customerId) {
        if (orderId == null || customerId == null) {
            throw new IllegalArgumentException("Order ID and Customer ID cannot be null");
        }

        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        jakarta.persistence.EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Find order
            Order order = em.find(Order.class, orderId);
            if (order == null) {
                tx.rollback();
                throw new IllegalStateException("Order not found: " + orderId);
            }

            // Verify customer ownership
            if (!order.getCustomerId().equals(customerId)) {
                tx.rollback();
                throw new IllegalStateException("Order does not belong to this customer");
            }

            // Verify status is SHIPPING
            if (order.getStatus() != OrderStatus.SHIPPING) {
                tx.rollback();
                throw new IllegalStateException("Only shipping orders can be confirmed. Current status: " + order.getStatus());
            }

            // Update order status
            order.setStatus(OrderStatus.COMPLETED);

            // Create status history entry
            OrderStatusHistory history = new OrderStatusHistory(
                    order.getId(),
                    OrderStatus.SHIPPING.name(),
                    OrderStatus.COMPLETED.name(),
                    customerId,
                    PLATFORM,
                    "Order received and confirmed by customer"
            );
            em.persist(history);

            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new IllegalStateException("Failed to confirm order: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    /**
     * Get the status history for an order.
     *
     * @param orderId the order ID
     * @return list of status history entries
     */
    @SuppressWarnings("unchecked")
    public List<OrderStatusHistory> getHistory(Long orderId) {
        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            Query query = em.createQuery(
                    "SELECT h FROM OrderStatusHistory h WHERE h.orderId = :orderId ORDER BY h.changedAt ASC");
            query.setParameter("orderId", orderId);
            return (List<OrderStatusHistory>) query.getResultList();
        }
    }

    /**
     * Find an order by ID.
     *
     * @param orderId the order ID
     * @return the order or null if not found
     */
    public Order findById(Long orderId) {
        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            return em.find(Order.class, orderId);
        }
    }
}
