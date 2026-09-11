package vn.edu.eaut.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.eaut.management.entity.OrderStatusHistory;

import java.util.List;

@Repository
public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long> {
    
    List<OrderStatusHistory> findByOrderIdOrderByChangedAtAsc(Long orderId);
    
    List<OrderStatusHistory> findByPlatformOrderByChangedAtDesc(String platform);
}
