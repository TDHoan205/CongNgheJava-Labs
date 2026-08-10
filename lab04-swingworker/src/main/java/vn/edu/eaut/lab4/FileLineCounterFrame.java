package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import javax.swing.SwingWorker;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileLineCounterFrame extends JFrame {

    private JButton btnChoose;

    private JButton btnCount;

    private JLabel lblFile;

    private JLabel lblResult;

    private JProgressBar progressBar;

    private File selectedFile;

    public FileLineCounterFrame() {

        setTitle(
                "Bài 5 - Đếm số dòng trong file"
        );

        setSize(700, 350);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        btnChoose =
                new JButton("Chọn file");

        btnCount =
                new JButton("Đếm số dòng");

        lblFile =
                new JLabel(
                        "Chưa chọn file"
                );

        lblResult =
                new JLabel(
                        "Kết quả:"
                );

        progressBar =
                new JProgressBar(0, 100);

        progressBar.setStringPainted(true);

        JPanel panel = new JPanel();

        panel.setLayout(
                new GridLayout(
                        5,
                        1,
                        10,
                        10
                )
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        30,
                        30,
                        30
                )
        );

        panel.add(btnChoose);

        panel.add(lblFile);

        panel.add(btnCount);

        panel.add(progressBar);

        panel.add(lblResult);

        add(panel);

        btnChoose.addActionListener(
                e -> chooseFile()
        );

        btnCount.addActionListener(
                e -> countLines()
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
                    "File: "
                            + selectedFile.getAbsolutePath()
            );

            lblResult.setText(
                    "Kết quả:"
            );

            progressBar.setValue(0);
        }
    }

    private void countLines() {

        if (selectedFile == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn file trước!"
            );

            return;
        }

        btnChoose.setEnabled(false);

        btnCount.setEnabled(false);

        progressBar.setValue(0);

        lblResult.setText(
                "Đang đọc file..."
        );

        SwingWorker<Long, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected Long doInBackground()
                            throws Exception {

                        long totalBytes =
                                Files.size(
                                        selectedFile.toPath()
                                );

                        long readBytes = 0;

                        long lineCount = 0;

                        try (
                                BufferedReader reader =
                                        Files.newBufferedReader(
                                                selectedFile.toPath(),
                                                StandardCharsets.UTF_8
                                        )
                        ) {

                            String line;

                            while (
                                    (
                                            line =
                                                    reader.readLine()
                                    ) != null
                            ) {

                                lineCount++;

                                readBytes +=
                                        line.getBytes(
                                                StandardCharsets.UTF_8
                                        ).length + 1;

                                if (
                                        totalBytes > 0
                                ) {

                                    int progress =
                                            (int) Math.min(
                                                    100,
                                                    readBytes
                                                            * 100
                                                            / totalBytes
                                            );

                                    setProgress(
                                            progress
                                    );
                                }
                            }
                        }

                        return lineCount;
                    }

                    @Override
                    protected void done() {

                        try {

                            long result =
                                    get();

                            lblResult.setText(
                                    "Kết quả: "
                                            + result
                                            + " dòng"
                            );

                            progressBar.setValue(
                                    100
                            );

                        } catch (Exception ex) {

                            JOptionPane.showMessageDialog(
                                    FileLineCounterFrame.this,
                                    "Không thể đọc file!"
                            );

                            lblResult.setText(
                                    "Có lỗi xảy ra!"
                            );
                        }

                        btnChoose.setEnabled(
                                true
                        );

                        btnCount.setEnabled(
                                true
                        );
                    }
                };

        worker.addPropertyChangeListener(
                event -> {

                    if (
                            "progress".equals(
                                    event.getPropertyName()
                            )
                    ) {

                        progressBar.setValue(
                                (int) event.getNewValue()
                        );
                    }
                }
        );

        worker.execute();
    }
}