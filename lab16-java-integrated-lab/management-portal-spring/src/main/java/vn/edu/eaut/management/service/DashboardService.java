package vn.edu.eaut.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.eaut.management.entity.Order;
import vn.edu.eaut.management.entity.OrderStatus;
import vn.edu.eaut.management.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    @Autowired
    private OrderRepository orderRepository;

    public long countAllOrders() {
        return orderRepository.count();
    }

    public long countByStatus(OrderStatus status) {
        return orderRepository.countByStatus(status);
    }

    public BigDecimal sumTotalAmountByStatus(OrderStatus status) {
        List<Order> orders = orderRepository.findByStatusOrderByCreatedAtAsc(status);
        BigDecimal total = BigDecimal.ZERO;
        for (Order order : orders) {
            if (order.getTotalAmount() != null) {
                total = total.add(order.getTotalAmount());
            }
        }
        return total;
    }

    public List<Object[]> topProducts(int n) {
        // Get all orders and aggregate manually since we don't have native query
        List<Order> orders = orderRepository.findAll();
        Map<Long, Long> productQuantityMap = new HashMap<>();
        Map<Long, String> productNameMap = new HashMap<>();
        
        for (Order order : orders) {
            if (order.getItems() != null) {
                for (var item : order.getItems()) {
                    Long productId = item.getProduct().getId();
                    productQuantityMap.merge(productId, (long) item.getQuantity(), Long::sum);
                    productNameMap.putIfAbsent(productId, item.getProduct().getName());
                }
            }
        }
        
        return productQuantityMap.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(n)
                .map(e -> new Object[]{e.getKey(), productNameMap.get(e.getKey()), e.getValue()})
                .toList();
    }

    public List<Object[]> topCustomers(int n) {
        List<Order> orders = orderRepository.findAll();
        Map<Long, Long> customerOrderCountMap = new HashMap<>();
        
        for (Order order : orders) {
            if (order.getCustomerId() != null) {
                customerOrderCountMap.merge(order.getCustomerId(), 1L, Long::sum);
            }
        }
        
        return customerOrderCountMap.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(n)
                .map(e -> new Object[]{e.getKey(), e.getValue()})
                .toList();
    }

    public Map<String, Long> ordersByStatus() {
        Map<String, Long> result = new HashMap<>();
        for (OrderStatus status : OrderStatus.values()) {
            result.put(status.name(), orderRepository.countByStatus(status));
        }
        return result;
    }

    public List<Order> getRecentOrders(int limit) {
        return orderRepository.findTop10ByOrderByCreatedAtDesc().stream().limit(limit).toList();
    }
}
