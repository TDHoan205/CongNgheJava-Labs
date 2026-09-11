package vn.edu.eaut.warehouse.controller;

import vn.edu.eaut.warehouse.model.Employee;
import vn.edu.eaut.warehouse.model.User;
import vn.edu.eaut.warehouse.service.WarehouseOrderService;
import vn.edu.eaut.warehouse.view.LoginView;
import vn.edu.eaut.warehouse.view.MainView;

import javax.swing.*;

public class LoginController {

    private LoginView loginView;
    private WarehouseOrderService service;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        this.service = WarehouseOrderService.getInstance();
    }

    public void handleLogin(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(loginView, "Username cannot be empty", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (password == null || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginView, "Password cannot be empty", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            User user = service.login(username.trim(), password);
            if (user != null) {
                // Set current employee session
                Employee employee = new Employee(user.getId(), user.getUsername(), user.getFullName());
                service.setCurrentEmployee(employee);

                // Close login view and open main view
                loginView.dispose();
                SwingUtilities.invokeLater(() -> {
                    MainView mainView = new MainView();
                    mainView.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(loginView, 
                    "Invalid username or password, or you don't have WAREHOUSE role", 
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(loginView, 
                "Login error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
