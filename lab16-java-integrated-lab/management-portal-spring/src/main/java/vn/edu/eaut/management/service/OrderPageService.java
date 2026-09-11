package vn.edu.eaut.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.eaut.management.entity.Order;
import vn.edu.eaut.management.entity.OrderStatus;
import vn.edu.eaut.management.repository.OrderRepository;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class OrderPageService {

    @Autowired
    private OrderRepository orderRepository;

    public Page<Order> findOrders(OrderStatus status, String keyword,
                                   LocalDateTime startDate, LocalDateTime endDate,
                                   Pageable pageable) {
        return orderRepository.findByFilters(status, keyword, startDate, endDate, pageable);
    }

    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }
}
