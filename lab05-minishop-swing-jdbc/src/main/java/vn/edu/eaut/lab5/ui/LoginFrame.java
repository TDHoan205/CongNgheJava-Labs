package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.TaiKhoanBUS;
import vn.edu.eaut.lab5.model.TaiKhoan;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final JTextField txtUsername =
            new JTextField();

    private final JPasswordField txtPassword =
            new JPasswordField();

    private final JButton btnLogin =
            new JButton("Đăng nhập");

    private final JCheckBox chkShowPassword =
            new JCheckBox("Hiển thị mật khẩu");

    private final TaiKhoanBUS bus =
            new TaiKhoanBUS();

    public LoginFrame() {

        setTitle("MiniShop - Đăng nhập");

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setSize(420, 280);

        setLocationRelativeTo(null);

        buildUI();
    }

    private void buildUI() {

        JPanel panel =
                new JPanel(
                        new GridBagLayout()
                );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(8, 8, 8, 8);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        JLabel title =
                new JLabel(
                        "MINISHOP",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        panel.add(title, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;

        panel.add(
                new JLabel("Username:"),
                gbc
        );

        gbc.gridx = 1;

        panel.add(
                txtUsername,
                gbc
        );

        gbc.gridx = 0;
        gbc.gridy = 2;

        panel.add(
                new JLabel("Password:"),
                gbc
        );

        gbc.gridx = 1;

        panel.add(
                txtPassword,
                gbc
        );

        gbc.gridx = 1;
        gbc.gridy = 3;

        panel.add(
                chkShowPassword,
                gbc
        );

        gbc.gridx = 1;
        gbc.gridy = 4;

        panel.add(
                btnLogin,
                gbc
        );

        add(panel);

        char defaultEcho =
                txtPassword.getEchoChar();

        chkShowPassword.addActionListener(
                e -> {

                    if (chkShowPassword.isSelected()) {

                        txtPassword.setEchoChar(
                                (char) 0
                        );

                    } else {

                        txtPassword.setEchoChar(
                                defaultEcho
                        );
                    }
                }
        );

        btnLogin.addActionListener(
                e -> login()
        );

        txtPassword.addActionListener(
                e -> login()
        );
    }

    private void login() {

        String username =
                txtUsername.getText().trim();

        String password =
                new String(
                        txtPassword.getPassword()
                );

        try {

            TaiKhoan account =
                    bus.dangNhap(
                            username,
                            password
                    );

            if (account == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Username hoặc mật khẩu không đúng!",
                        "Đăng nhập thất bại",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Đăng nhập thành công!\n"
                            + "Xin chào: "
                            + account.getHoTen()
                            + "\nVai trò: "
                            + account.getVaiTro(),
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

            SwingUtilities.invokeLater(
                    () -> new MainFrame(account).setVisible(true)
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi đăng nhập:\n"
                            + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new LoginFrame().setVisible(true)
        );
    }
}