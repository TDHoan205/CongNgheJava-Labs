package vn.edu.eaut.warehouse.controller;

import vn.edu.eaut.warehouse.model.Order;
import vn.edu.eaut.warehouse.model.OrderItem;
import vn.edu.eaut.warehouse.model.OrderStatus;
import vn.edu.eaut.warehouse.model.OrderStatusHistory;
import vn.edu.eaut.warehouse.service.WarehouseOrderService;
import vn.edu.eaut.warehouse.view.MainView;

import javax.swing.*;
import java.util.List;

public class MainController {

    private MainView mainView;
    private WarehouseOrderService service;

    public MainController(MainView mainView) {
        this.mainView = mainView;
        this.service = WarehouseOrderService.getInstance();
    }

    public List<Order> loadPendingOrders() {
        try {
            return service.findOrdersByStatus(OrderStatus.PENDING);
        } catch (Exception e) {
            showError("Failed to load PENDING orders: " + e.getMessage());
            return List.of();
        }
    }

    public List<Order> loadProcessingOrders() {
        try {
            return service.findOrdersByStatus(OrderStatus.PROCESSING);
        } catch (Exception e) {
            showError("Failed to load PROCESSING orders: " + e.getMessage());
            return List.of();
        }
    }

    public List<Order> loadReadyOrders() {
        try {
            return service.findOrdersByStatus(OrderStatus.READY);
        } catch (Exception e) {
            showError("Failed to load READY orders: " + e.getMessage());
            return List.of();
        }
    }

    public List<OrderItem> loadOrderItems(long orderId) {
        try {
            return service.findItemsByOrderId(orderId);
        } catch (Exception e) {
            showError("Failed to load order items: " + e.getMessage());
            return List.of();
        }
    }

    public List<OrderStatusHistory> loadOrderHistory(long orderId) {
        try {
            return service.findHistoryByOrderId(orderId);
        } catch (Exception e) {
            showError("Failed to load order history: " + e.getMessage());
            return List.of();
        }
    }

    public void acceptOrder(long orderId) {
        try {
            service.acceptOrder(orderId);
            JOptionPane.showMessageDialog(mainView, "Order accepted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            mainView.refreshAllTabs();
        } catch (Exception e) {
            showError("Failed to accept order: " + e.getMessage());
        }
    }

    public void packOrder(long orderId) {
        try {
            service.packOrder(orderId);
            JOptionPane.showMessageDialog(mainView, "Order packed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            mainView.refreshAllTabs();
        } catch (Exception e) {
            showError("Failed to pack order: " + e.getMessage());
        }
    }

    public void cancelOrder(long orderId) {
        String reason = JOptionPane.showInputDialog(mainView, 
            "Please enter reason for cancellation:", 
            "Cancel Order", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (reason == null) {
            return; // User cancelled
        }

        if (reason.trim().isEmpty()) {
            reason = "Huy bo";
        }

        try {
            service.cancelOrder(orderId, reason);
            JOptionPane.showMessageDialog(mainView, "Order cancelled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            mainView.refreshAllTabs();
        } catch (Exception e) {
            showError("Failed to cancel order: " + e.getMessage());
        }
    }

    public void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(mainView, 
            "Are you sure you want to logout?", 
            "Logout Confirmation", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            service.clearCurrentEmployee();
            mainView.dispose();
            SwingUtilities.invokeLater(() -> {
                vn.edu.eaut.warehouse.view.LoginView loginView = new vn.edu.eaut.warehouse.view.LoginView();
                loginView.setVisible(true);
            });
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(mainView, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
