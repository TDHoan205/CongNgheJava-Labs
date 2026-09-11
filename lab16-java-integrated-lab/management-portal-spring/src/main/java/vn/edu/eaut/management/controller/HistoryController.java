package vn.edu.eaut.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.eaut.management.entity.OrderStatusHistory;
import vn.edu.eaut.management.repository.OrderStatusHistoryRepository;

import java.util.List;

@Controller
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private OrderStatusHistoryRepository historyRepository;

    @GetMapping
    public String listHistory(
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) String platform,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("changedAt").descending());
        
        Page<OrderStatusHistory> historyPage;
        
        if (orderId != null) {
            List<OrderStatusHistory> history = historyRepository.findByOrderIdOrderByChangedAtAsc(orderId);
            model.addAttribute("history", history);
        } else if (platform != null && !platform.isEmpty()) {
            List<OrderStatusHistory> history = historyRepository.findByPlatformOrderByChangedAtDesc(platform);
            model.addAttribute("history", history);
        } else {
            Page<OrderStatusHistory> allHistory = historyRepository.findAll(pageable);
            model.addAttribute("historyPage", allHistory);
        }

        model.addAttribute("orderId", orderId);
        model.addAttribute("platform", platform);

        return "history/list";
    }
}
