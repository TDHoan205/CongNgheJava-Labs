package vn.edu.eaut.lab5;

import javax.swing.SwingUtilities;
import vn.edu.eaut.lab5.ui.LoginFrame;

public class App {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}