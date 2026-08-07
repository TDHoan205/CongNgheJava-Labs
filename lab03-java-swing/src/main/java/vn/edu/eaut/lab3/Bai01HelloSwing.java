package vn.edu.eaut.lab3;

import javax.swing.*;

public class Bai01HelloSwing extends JFrame {

    public Bai01HelloSwing() {

        setTitle("Bài 1 - Hello Swing");
        setSize(350,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lbl = new JLabel("Xin chào Java Swing!", SwingConstants.CENTER);

        add(lbl);

        setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Bai01HelloSwing();
        });

    }

}