package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.SwingWorker;
import java.awt.*;

public class CancelTaskFrame extends JFrame {

    private JTextField txtSeconds;
    private JButton btnStart;
    private JButton btnCancel;
    private JProgressBar progressBar;
    private JLabel lblStatus;

    private SwingWorker<Void, Void> worker;

    public CancelTaskFrame() {

        setTitle("Bài 6 - Hủy tác vụ");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        txtSeconds = new JTextField();

        btnStart = new JButton("Bắt đầu");

        btnCancel = new JButton("Hủy");
        btnCancel.setEnabled(false);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        lblStatus = new JLabel("Trạng thái: Chưa chạy");
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        inputPanel.add(new JLabel("Số giây:"));
        inputPanel.add(txtSeconds);
        inputPanel.add(btnStart);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnCancel);

        JPanel mainPanel = new JPanel(
                new GridLayout(4, 1, 10, 10)
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        mainPanel.add(inputPanel);
        mainPanel.add(progressBar);
        mainPanel.add(lblStatus);
        mainPanel.add(buttonPanel);

        add(mainPanel);

        btnStart.addActionListener(e -> startTask());

        btnCancel.addActionListener(e -> cancelTask());
    }

    private void startTask() {

        int seconds;

        try {

            seconds = Integer.parseInt(
                    txtSeconds.getText().trim()
            );

            if (seconds <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Số giây phải lớn hơn 0"
                );

                return;
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập số nguyên hợp lệ"
            );

            return;
        }

        final int totalSeconds = seconds;

        btnStart.setEnabled(false);
        btnCancel.setEnabled(true);
        txtSeconds.setEnabled(false);

        progressBar.setValue(0);
        lblStatus.setText("Trạng thái: Đang chạy...");

        worker = new SwingWorker<>() {

            @Override
            protected Void doInBackground() throws Exception {

                for (int i = 0; i <= totalSeconds; i++) {

                    if (isCancelled()) {
                        return null;
                    }

                    int progress =
                            (int) (i * 100.0 / totalSeconds);

                    setProgress(progress);

                    Thread.sleep(1000);
                }

                return null;
            }

            @Override
            protected void done() {

                if (isCancelled()) {

                    lblStatus.setText(
                            "Trạng thái: Đã hủy tác vụ"
                    );

                } else {

                    progressBar.setValue(100);

                    lblStatus.setText(
                            "Trạng thái: Hoàn thành"
                    );
                }

                btnStart.setEnabled(true);
                btnCancel.setEnabled(false);
                txtSeconds.setEnabled(true);
            }
        };

        worker.addPropertyChangeListener(evt -> {

            if ("progress".equals(
                    evt.getPropertyName()
            )) {

                progressBar.setValue(
                        (int) evt.getNewValue()
                );
            }
        });

        worker.execute();
    }

    private void cancelTask() {

        if (worker != null && !worker.isDone()) {

            worker.cancel(true);

            lblStatus.setText(
                    "Trạng thái: Đang hủy..."
            );
        }
    }
}