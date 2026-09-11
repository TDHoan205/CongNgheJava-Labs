package vn.edu.eaut.customer.model;

/**
 * Order status enumeration representing the lifecycle states of an order.
 */
public enum OrderStatus {
    PENDING,
    PROCESSING,
    READY,
    SHIPPING,
    COMPLETED,
    CANCELLED
}
