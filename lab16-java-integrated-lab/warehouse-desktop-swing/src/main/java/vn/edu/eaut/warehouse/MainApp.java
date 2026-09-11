package vn.edu.eaut.warehouse;

import vn.edu.eaut.warehouse.view.LoginView;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });
    }
}
