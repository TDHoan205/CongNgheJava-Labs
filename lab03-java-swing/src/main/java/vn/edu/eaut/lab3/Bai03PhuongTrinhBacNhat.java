package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai03PhuongTrinhBacNhat extends JFrame{

    JTextField txtA;
    JTextField txtB;
    JLabel lblKQ;

    public Bai03PhuongTrinhBacNhat(){

        setTitle("Bài 3 - Giải PT bậc nhất");

        setSize(400,250);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridLayout(4,2,10,10));

        add(new JLabel("a"));

        txtA=new JTextField();

        add(txtA);

        add(new JLabel("b"));

        txtB=new JTextField();

        add(txtB);

        JButton btn=new JButton("Giải");

        add(btn);

        lblKQ=new JLabel("");

        add(lblKQ);

        btn.addActionListener(e->{

            double a=Double.parseDouble(txtA.getText());

            double b=Double.parseDouble(txtB.getText());

            if(a==0){

                if(b==0)

                    lblKQ.setText("Vô số nghiệm");

                else

                    lblKQ.setText("Vô nghiệm");

            }

            else{

                double x=-b/a;

                lblKQ.setText("x = "+x);

            }

        });

        setVisible(true);

    }

    public static void main(String[] args){

        SwingUtilities.invokeLater(() -> new Bai03PhuongTrinhBacNhat());

    }

}