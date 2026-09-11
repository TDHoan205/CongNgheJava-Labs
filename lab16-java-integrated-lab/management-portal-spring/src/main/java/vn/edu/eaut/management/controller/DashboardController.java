package vn.edu.eaut.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.edu.eaut.management.entity.Order;
import vn.edu.eaut.management.entity.OrderStatus;
import vn.edu.eaut.management.service.DashboardService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/")
    public String root() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Order counts
        long totalOrders = dashboardService.countAllOrders();
        Map<String, Long> ordersByStatus = dashboardService.ordersByStatus();
        
        // Revenue from completed orders
        BigDecimal completedRevenue = dashboardService.sumTotalAmountByStatus(OrderStatus.COMPLETED);
        
        // Top products
        List<Object[]> top5Products = dashboardService.topProducts(5);
        
        // Top customers
        List<Object[]> top5Customers = dashboardService.topCustomers(5);
        
        // Recent orders
        List<Order> recentOrders = dashboardService.getRecentOrders(10);

        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("ordersByStatus", ordersByStatus);
        model.addAttribute("completedRevenue", completedRevenue);
        model.addAttribute("top5Products", top5Products);
        model.addAttribute("top5Customers", top5Customers);
        model.addAttribute("recentOrders", recentOrders);

        return "dashboard";
    }
}
