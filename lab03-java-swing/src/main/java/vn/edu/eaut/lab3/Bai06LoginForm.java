package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai06LoginForm extends JFrame {

    private JTextField txtUser;

    private JPasswordField txtPass;

    private JComboBox<String> cboRole;

    private JCheckBox chkShow;

    private JButton btnLogin;

    public Bai06LoginForm(){

        setTitle("Bài 6 - Form đăng nhập");

        setSize(400,250);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridLayout(5,2,10,10));

        add(new JLabel("Tài khoản"));

        txtUser=new JTextField();

        add(txtUser);

        add(new JLabel("Mật khẩu"));

        txtPass=new JPasswordField();

        add(txtPass);

        add(new JLabel("Vai trò"));

        cboRole=new JComboBox<>();

        cboRole.addItem("Admin");

        cboRole.addItem("User");

        add(cboRole);

        chkShow=new JCheckBox("Hiển thị mật khẩu");

        add(chkShow);

        btnLogin=new JButton("Đăng nhập");

        add(btnLogin);

        chkShow.addActionListener(e->{

            if(chkShow.isSelected())

                txtPass.setEchoChar((char)0);

            else

                txtPass.setEchoChar('•');

        });

        btnLogin.addActionListener(e->dangNhap());

    }

    private void dangNhap(){

        String user=txtUser.getText().trim();

        String pass=new String(txtPass.getPassword());

        String role=cboRole.getSelectedItem().toString();

        if(user.equals("admin")
                && pass.equals("123456")
                && role.equals("Admin")){

            JOptionPane.showMessageDialog(this,
                    "Đăng nhập Admin thành công!");

        }

        else if(user.equals("user")
                && pass.equals("123456")
                && role.equals("User")){

            JOptionPane.showMessageDialog(this,
                    "Đăng nhập User thành công!");

        }

        else{

            JOptionPane.showMessageDialog(this,
                    "Sai tài khoản, mật khẩu hoặc vai trò!");

        }

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->

                new Bai06LoginForm().setVisible(true));

    }

}