package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.SwingWorker;
import java.awt.*;

public class App {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame =
                    new JFrame(
                            "LAB 4 - SwingWorker"
                    );

            frame.setSize(500, 600);

            frame.setDefaultCloseOperation(
                    JFrame.EXIT_ON_CLOSE
            );

            frame.setLocationRelativeTo(null);

            JPanel panel =
                    new JPanel(
                            new GridLayout(
                                    10,
                                    1,
                                    10,
                                    10
                            )
                    );

            panel.setBorder(
                    BorderFactory.createEmptyBorder(
                            20,
                            30,
                            20,
                            30
                    )
            );

            JButton btn1 =
                    new JButton(
                            "Bài 1 - Đồng hồ đếm ngược"
                    );

            JButton btn2 =
                    new JButton(
                            "Bài 2 - Mô phỏng tải dữ liệu"
                    );

            JButton btn3 =
                    new JButton(
                            "Bài 3 - Tổng số nguyên tố"
                    );

            JButton btn4 =
                    new JButton(
                            "Bài 4 - Fibonacci"
                    );

            JButton btn5 =
                    new JButton(
                            "Bài 5 - Đếm số dòng file"
                    );

            JButton btn6 =
                    new JButton(
                            "Bài 6 - Hủy tác vụ"
                    );

            JButton btn7 =
                    new JButton(
                            "Bài 7 - Tìm kiếm file"
                    );

            JButton btn8 =
                    new JButton(
                            "Bài 8 - Đọc CSV sinh viên"
                    );

            JButton btn9 =
                    new JButton(
                            "Bài 9 - Tải sản phẩm"
                    );

            JButton btn10 =
                    new JButton(
                            "Bài 10 - Quản lý sản phẩm CSV"
                    );

            panel.add(btn1);
            panel.add(btn2);
            panel.add(btn3);
            panel.add(btn4);
            panel.add(btn5);
            panel.add(btn6);
            panel.add(btn7);
            panel.add(btn8);
            panel.add(btn9);
            panel.add(btn10);

            btn1.addActionListener(
                    e -> new CountdownFrame()
                            .setVisible(true)
            );

            btn2.addActionListener(
                    e -> new ProgressDemoFrame()
                            .setVisible(true)
            );

            btn3.addActionListener(
                    e -> new PrimeSumFrame()
                            .setVisible(true)
            );

            btn4.addActionListener(
                    e -> new FibonacciFrame()
                            .setVisible(true)
            );

            btn5.addActionListener(
                    e -> new FileLineCounterFrame()
                            .setVisible(true)
            );

            btn6.addActionListener(
                    e -> new CancelTaskFrame()
                            .setVisible(true)
            );

            btn7.addActionListener(
                    e -> new FileSearchFrame()
                            .setVisible(true)
            );

            btn8.addActionListener(
                    e -> new CsvStudentFrame()
                            .setVisible(true)
            );

            btn9.addActionListener(
                    e -> new ProductLoaderFrame()
                            .setVisible(true)
            );

            btn10.addActionListener(
                    e -> new ProductCsvFrame()
                            .setVisible(true)
            );

            frame.add(panel);

            frame.setVisible(true);
        });
    }
}