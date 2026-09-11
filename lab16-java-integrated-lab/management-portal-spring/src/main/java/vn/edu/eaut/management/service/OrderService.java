package vn.edu.eaut.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.eaut.management.entity.Order;
import vn.edu.eaut.management.entity.OrderItem;
import vn.edu.eaut.management.entity.OrderStatus;
import vn.edu.eaut.management.entity.OrderStatusHistory;
import vn.edu.eaut.management.entity.Product;
import vn.edu.eaut.management.repository.OrderRepository;
import vn.edu.eaut.management.repository.OrderStatusHistoryRepository;
import vn.edu.eaut.management.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private static final String PLATFORM_SPRING_BOOT = "SPRING_BOOT";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusHistoryRepository historyRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public List<Order> findByStatus(OrderStatus status) {
        return orderRepository.findByStatusOrderByCreatedAtAsc(status);
    }

    public Order approveShipping(Long orderId, Long managerId) {
        Order order = findById(orderId);
        
        if (order.getStatus() != OrderStatus.READY) {
            throw new IllegalStateException("Order must be in READY status to approve shipping. Current status: " + order.getStatus());
        }

        OrderStatus oldStatus = order.getStatus();
        order.setStatus(OrderStatus.SHIPPING);
        order = orderRepository.save(order);

        // Save history
        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrderId(order.getId());
        history.setOldStatus(oldStatus.name());
        history.setNewStatus(OrderStatus.SHIPPING.name());
        history.setChangedBy(managerId);
        history.setChangedAt(LocalDateTime.now());
        history.setPlatform(PLATFORM_SPRING_BOOT);
        history.setNote("Quan ly phe duyet giao hang");
        historyRepository.save(history);

        return order;
    }

    public Order cancelOrder(Long orderId, Long managerId, String reason) {
        Order order = findById(orderId);

        if (order.getStatus() == OrderStatus.COMPLETED || order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot cancel order with status: " + order.getStatus());
        }

        OrderStatus oldStatus = order.getStatus();

        // Restore stock for each item
        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProduct().getId()));
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

        order.setStatus(OrderStatus.CANCELLED);
        order = orderRepository.save(order);

        // Save history
        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrderId(order.getId());
        history.setOldStatus(oldStatus.name());
        history.setNewStatus(OrderStatus.CANCELLED.name());
        history.setChangedBy(managerId);
        history.setChangedAt(LocalDateTime.now());
        history.setPlatform(PLATFORM_SPRING_BOOT);
        history.setNote(reason != null ? reason : "Order cancelled");
        historyRepository.save(history);

        return order;
    }

    public Order markCompleted(Long orderId, Long managerId) {
        Order order = findById(orderId);

        if (order.getStatus() != OrderStatus.SHIPPING) {
            throw new IllegalStateException("Order must be in SHIPPING status to mark as completed. Current status: " + order.getStatus());
        }

        OrderStatus oldStatus = order.getStatus();
        order.setStatus(OrderStatus.COMPLETED);
        order = orderRepository.save(order);

        // Save history
        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrderId(order.getId());
        history.setOldStatus(oldStatus.name());
        history.setNewStatus(OrderStatus.COMPLETED.name());
        history.setChangedBy(managerId);
        history.setChangedAt(LocalDateTime.now());
        history.setPlatform(PLATFORM_SPRING_BOOT);
        history.setNote("Order marked as completed");
        historyRepository.save(history);

        return order;
    }

    public List<OrderStatusHistory> getOrderHistory(Long orderId) {
        return historyRepository.findByOrderIdOrderByChangedAtAsc(orderId);
    }
}
