package vn.edu.eaut.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.eaut.management.entity.Order;
import vn.edu.eaut.management.entity.OrderStatus;
import vn.edu.eaut.management.entity.OrderStatusHistory;
import vn.edu.eaut.management.service.OrderPageService;
import vn.edu.eaut.management.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderPageService orderPageService;

    @GetMapping
    public String listOrders(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Order> orders = orderPageService.findOrders(status, keyword, startDate, endDate, pageable);

        model.addAttribute("orders", orders);
        model.addAttribute("status", status);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "orders/list";
    }

    @GetMapping("/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        Order order = orderService.findById(id);
        List<OrderStatusHistory> history = orderService.getOrderHistory(id);

        model.addAttribute("order", order);
        model.addAttribute("history", history);
        model.addAttribute("canApprove", order.getStatus() == OrderStatus.READY);
        model.addAttribute("canCancel", order.getStatus() != OrderStatus.COMPLETED && order.getStatus() != OrderStatus.CANCELLED);

        return "orders/detail";
    }

    @PostMapping("/{id}/approve")
    public String approveShipping(@PathVariable Long id) {
        Long managerId = getCurrentUserId();
        orderService.approveShipping(id, managerId);
        return "redirect:/orders/" + id;
    }

    @PostMapping("/{id}/cancel")
    public String cancelOrder(@PathVariable Long id, @RequestParam(required = false) String reason) {
        Long managerId = getCurrentUserId();
        orderService.cancelOrder(id, managerId, reason);
        return "redirect:/orders/" + id;
    }

    @GetMapping("/ready")
    public String readyOrders(Model model) {
        List<Order> readyOrders = orderService.findByStatus(OrderStatus.READY);
        model.addAttribute("orders", readyOrders);
        return "orders/ready-list";
    }

    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // For simplicity, return 1L as default manager ID
        // In production, you'd look up the user by username
        return 1L;
    }
}
