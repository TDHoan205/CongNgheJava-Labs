package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai04TamGiacSwing extends JFrame{

    JTextField txtA;
    JTextField txtB;
    JTextField txtC;

    JLabel lblKQ;

    public Bai04TamGiacSwing(){

        setTitle("Bài 4 - Phân loại tam giác");

        setSize(450,300);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridLayout(5,2,10,10));

        add(new JLabel("Cạnh a"));

        txtA=new JTextField();

        add(txtA);

        add(new JLabel("Cạnh b"));

        txtB=new JTextField();

        add(txtB);

        add(new JLabel("Cạnh c"));

        txtC=new JTextField();

        add(txtC);

        JButton btn=new JButton("Kiểm tra");

        add(btn);

        lblKQ=new JLabel();

        add(lblKQ);

        btn.addActionListener(e->{

            double a=Double.parseDouble(txtA.getText());

            double b=Double.parseDouble(txtB.getText());

            double c=Double.parseDouble(txtC.getText());

            String kq;

            if(a+b<=c || a+c<=b || b+c<=a){

                kq="Không phải tam giác";

            }

            else if(a==b && b==c){

                kq="Tam giác đều";

            }

            else if(a==b || a==c || b==c){

                if(a*a+b*b==c*c || a*a+c*c==b*b || b*b+c*c==a*a)

                    kq="Tam giác vuông cân";

                else

                    kq="Tam giác cân";

            }

            else if(a*a+b*b==c*c || a*a+c*c==b*b || b*b+c*c==a*a){

                kq="Tam giác vuông";

            }

            else{

                kq="Tam giác thường";

            }

            lblKQ.setText(kq);

        });

        setVisible(true);

    }

    public static void main(String[] args){

        SwingUtilities.invokeLater(() -> new Bai04TamGiacSwing());

    }

}