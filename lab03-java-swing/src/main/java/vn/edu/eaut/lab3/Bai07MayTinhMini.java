package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai07MayTinhMini extends JFrame {

    private JTextField txtSo1;
    private JTextField txtSo2;
    private JLabel lblKetQua;

    public Bai07MayTinhMini() {

        setTitle("Bài 7 - Máy tính Mini");
        setSize(450,250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridLayout(4,2,10,10));

        add(new JLabel("Số thứ nhất"));

        txtSo1 = new JTextField();
        add(txtSo1);

        add(new JLabel("Số thứ hai"));

        txtSo2 = new JTextField();
        add(txtSo2);

        JPanel panelButton = new JPanel();

        JButton btnCong = new JButton("+");
        JButton btnTru = new JButton("-");
        JButton btnNhan = new JButton("*");
        JButton btnChia = new JButton("/");

        panelButton.add(btnCong);
        panelButton.add(btnTru);
        panelButton.add(btnNhan);
        panelButton.add(btnChia);

        add(panelButton);

        lblKetQua = new JLabel("Kết quả: ");

        add(lblKetQua);

        btnCong.addActionListener(e -> tinh('+'));
        btnTru.addActionListener(e -> tinh('-'));
        btnNhan.addActionListener(e -> tinh('*'));
        btnChia.addActionListener(e -> tinh('/'));

    }

    private void tinh(char phepToan){

        try{

            double a = Double.parseDouble(txtSo1.getText());

            double b = Double.parseDouble(txtSo2.getText());

            double kq = 0;

            switch (phepToan){

                case '+':
                    kq = a+b;
                    break;

                case '-':
                    kq = a-b;
                    break;

                case '*':
                    kq = a*b;
                    break;

                case '/':

                    if(b==0){

                        JOptionPane.showMessageDialog(this,
                                "Không thể chia cho 0");

                        return;
                    }

                    kq = a/b;
                    break;

            }

            lblKetQua.setText("Kết quả: "+kq);

        }

        catch (Exception ex){

            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đúng số.");

        }

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->

                new Bai07MayTinhMini().setVisible(true));

    }

}