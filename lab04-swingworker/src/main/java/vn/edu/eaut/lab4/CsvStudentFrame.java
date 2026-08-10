package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import javax.swing.SwingWorker;
import java.util.ArrayList;
import java.util.List;

public class CsvStudentFrame extends JFrame {

    private File selectedFile;

    private JLabel lblFile;
    private JButton btnChoose;
    private JButton btnLoad;

    private JTable table;
    private DefaultTableModel tableModel;

    private JLabel lblAverage;
    private JLabel lblHighest;

    public CsvStudentFrame() {

        setTitle("Bài 8 - Đọc CSV điểm sinh viên");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        lblFile = new JLabel("Chưa chọn file");

        btnChoose = new JButton("Chọn CSV");

        btnLoad = new JButton("Đọc dữ liệu");

        JPanel topPanel = new JPanel(
                new GridLayout(2, 2, 10, 10)
        );

        topPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        topPanel.add(new JLabel("File CSV:"));
        topPanel.add(lblFile);

        topPanel.add(btnChoose);
        topPanel.add(btnLoad);

        add(topPanel, BorderLayout.NORTH);

        tableModel =
                new DefaultTableModel(
                        new String[]{
                                "Mã SV",
                                "Họ tên",
                                "Điểm"
                        },
                        0
                );

        table = new JTable(tableModel);

        add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );

        lblAverage =
                new JLabel("Điểm trung bình: ");

        lblHighest =
                new JLabel("Sinh viên cao nhất: ");

        JPanel bottomPanel =
                new JPanel(
                        new GridLayout(2, 1)
                );

        bottomPanel.add(lblAverage);
        bottomPanel.add(lblHighest);

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        btnChoose.addActionListener(
                e -> chooseFile()
        );

        btnLoad.addActionListener(
                e -> loadStudents()
        );
    }

    private void chooseFile() {

        JFileChooser chooser =
                new JFileChooser();

        int result =
                chooser.showOpenDialog(this);

        if (
                result ==
                        JFileChooser.APPROVE_OPTION
        ) {

            selectedFile =
                    chooser.getSelectedFile();

            lblFile.setText(
                    selectedFile.getAbsolutePath()
            );
        }
    }

    private void loadStudents() {

        if (selectedFile == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn file CSV"
            );

            return;
        }

        btnChoose.setEnabled(false);
        btnLoad.setEnabled(false);

        tableModel.setRowCount(0);

        lblAverage.setText(
                "Điểm trung bình: Đang tính..."
        );

        lblHighest.setText(
                "Sinh viên cao nhất: Đang tính..."
        );

        SwingWorker<List<Student>, Void> worker =
                new SwingWorker<>() {

            @Override
            protected List<Student> doInBackground()
                    throws Exception {

                List<Student> students =
                        new ArrayList<>();

                List<String> lines =
                        Files.readAllLines(
                                selectedFile.toPath(),
                                StandardCharsets.UTF_8
                        );

                for (int i = 1;
                     i < lines.size();
                     i++) {

                    String line =
                            lines.get(i).trim();

                    if (line.isEmpty()) {
                        continue;
                    }

                    String[] parts =
                            line.split(",");

                    if (parts.length < 3) {
                        continue;
                    }

                    String maSV =
                            parts[0].trim();

                    String hoTen =
                            parts[1].trim();

                    double diem =
                            Double.parseDouble(
                                    parts[2].trim()
                            );

                    students.add(
                            new Student(
                                    maSV,
                                    hoTen,
                                    diem
                            )
                    );
                }

                return students;
            }

            @Override
            protected void done() {

                try {

                    List<Student> students =
                            get();

                    double sum = 0;

                    Student highest = null;

                    for (Student student :
                            students) {

                        tableModel.addRow(
                                new Object[]{
                                        student.maSV,
                                        student.hoTen,
                                        student.diem
                                }
                        );

                        sum += student.diem;

                        if (
                                highest == null
                                        ||
                                        student.diem
                                                > highest.diem
                        ) {

                            highest = student;
                        }
                    }

                    double average =
                            students.isEmpty()
                                    ? 0
                                    : sum / students.size();

                    lblAverage.setText(
                            String.format(
                                    "Điểm trung bình: %.2f",
                                    average
                            )
                    );

                    if (highest != null) {

                        lblHighest.setText(
                                String.format(
                                        "Sinh viên cao nhất: %s - %s (%.2f)",
                                        highest.maSV,
                                        highest.hoTen,
                                        highest.diem
                                )
                        );

                    } else {

                        lblHighest.setText(
                                "Sinh viên cao nhất: Không có dữ liệu"
                        );
                    }

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(
                            CsvStudentFrame.this,
                            "Lỗi đọc CSV:\n"
                                    + ex.getMessage()
                    );
                }

                btnChoose.setEnabled(true);
                btnLoad.setEnabled(true);
            }
        };

        worker.execute();
    }

    private static class Student {

        String maSV;

        String hoTen;

        double diem;

        Student(
                String maSV,
                String hoTen,
                double diem
        ) {

            this.maSV = maSV;
            this.hoTen = hoTen;
            this.diem = diem;
        }
    }
}