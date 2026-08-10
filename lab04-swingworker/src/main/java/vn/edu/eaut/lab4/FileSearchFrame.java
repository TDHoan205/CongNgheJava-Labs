package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import javax.swing.SwingWorker;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileSearchFrame extends JFrame {

    private File selectedFile;

    private JLabel lblFile;
    private JTextField txtKeyword;
    private JButton btnChoose;
    private JButton btnSearch;
    private JTextArea txtResult;
    private JLabel lblStatus;

    public FileSearchFrame() {

        setTitle("Bài 7 - Tìm kiếm từ khóa trong file");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        lblFile = new JLabel("Chưa chọn file");

        txtKeyword = new JTextField();

        btnChoose = new JButton("Chọn file");

        btnSearch = new JButton("Tìm kiếm");

        txtResult = new JTextArea();
        txtResult.setEditable(false);
        txtResult.setLineWrap(true);
        txtResult.setWrapStyleWord(true);

        lblStatus = new JLabel("Trạng thái: Chưa tìm kiếm");

        JPanel topPanel = new JPanel(
                new GridLayout(3, 2, 10, 10)
        );

        topPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        topPanel.add(new JLabel("File:"));
        topPanel.add(lblFile);

        topPanel.add(new JLabel("Từ khóa:"));
        topPanel.add(txtKeyword);

        topPanel.add(btnChoose);
        topPanel.add(btnSearch);

        add(topPanel, BorderLayout.NORTH);

        add(
                new JScrollPane(txtResult),
                BorderLayout.CENTER
        );

        add(lblStatus, BorderLayout.SOUTH);

        btnChoose.addActionListener(
                e -> chooseFile()
        );

        btnSearch.addActionListener(
                e -> searchKeyword()
        );
    }

    private void chooseFile() {

        JFileChooser chooser =
                new JFileChooser();

        int result =
                chooser.showOpenDialog(this);

        if (result ==
                JFileChooser.APPROVE_OPTION) {

            selectedFile =
                    chooser.getSelectedFile();

            lblFile.setText(
                    selectedFile.getAbsolutePath()
            );
        }
    }

    private void searchKeyword() {

        if (selectedFile == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn file trước"
            );

            return;
        }

        String keyword =
                txtKeyword.getText().trim();

        if (keyword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập từ khóa"
            );

            txtKeyword.requestFocus();

            return;
        }

        btnSearch.setEnabled(false);
        btnChoose.setEnabled(false);

        txtResult.setText("");

        lblStatus.setText(
                "Trạng thái: Đang tìm kiếm..."
        );

        SwingWorker<SearchResult, Void> worker =
                new SwingWorker<>() {

            @Override
            protected SearchResult doInBackground()
                    throws Exception {

                SearchResult result =
                        new SearchResult();

                String lowerKeyword =
                        keyword.toLowerCase();

                try (
                        BufferedReader reader =
                                Files.newBufferedReader(
                                        selectedFile.toPath(),
                                        StandardCharsets.UTF_8
                                )
                ) {

                    String line;

                    while (
                            (line = reader.readLine())
                                    != null
                    ) {

                        result.totalLines++;

                        if (
                                line.toLowerCase()
                                        .contains(lowerKeyword)
                        ) {

                            result.matchLines++;

                            result.content
                                    .append(
                                            "Dòng "
                                                    + result.totalLines
                                                    + ": "
                                                    + line
                                                    + "\n"
                                    );
                        }
                    }
                }

                return result;
            }

            @Override
            protected void done() {

                try {

                    SearchResult result = get();

                    txtResult.setText(
                            result.content.toString()
                    );

                    lblStatus.setText(
                            "Tìm thấy "
                                    + result.matchLines
                                    + " dòng chứa từ khóa"
                    );

                } catch (Exception ex) {

                    lblStatus.setText(
                            "Lỗi khi đọc file"
                    );

                    JOptionPane.showMessageDialog(
                            FileSearchFrame.this,
                            "Không thể đọc file:\n"
                                    + ex.getMessage()
                    );
                }

                btnSearch.setEnabled(true);
                btnChoose.setEnabled(true);
            }
        };

        worker.execute();
    }

    private static class SearchResult {

        int totalLines = 0;

        int matchLines = 0;

        StringBuilder content =
                new StringBuilder();
    }
}