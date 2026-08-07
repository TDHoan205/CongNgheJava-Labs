package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Bai02TongHaiSo extends JFrame {

    JTextField txtA;
    JTextField txtB;
    JTextField txtKQ;

    public Bai02TongHaiSo(){

        setTitle("Bài 2 - Tính tổng");
        setSize(400,250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridLayout(4,2,10,10));

        add(new JLabel("Số A"));

        txtA=new JTextField();
        add(txtA);

        add(new JLabel("Số B"));

        txtB=new JTextField();
        add(txtB);

        JButton btn=new JButton("Tính");

        add(btn);

        txtKQ=new JTextField();
        txtKQ.setEditable(false);

        add(txtKQ);

        btn.addActionListener((ActionEvent e)->{

            double a=Double.parseDouble(txtA.getText());

            double b=Double.parseDouble(txtB.getText());

            txtKQ.setText(String.valueOf(a+b));

        });

        setVisible(true);

    }

    public static void main(String[] args){

        SwingUtilities.invokeLater(() -> new Bai02TongHaiSo());

    }

}