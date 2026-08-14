package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.ThongKeBUS;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ThongKePanel extends JPanel {

    private final JTextField txtTuNgay =
            new JTextField(
                    LocalDate.now()
                            .withDayOfMonth(1)
                            .toString()
            );

    private final JTextField txtDenNgay =
            new JTextField(
                    LocalDate.now().toString()
            );

    private final JTextField txtMaKh =
            new JTextField();

    private final JLabel lblDoanhThu =
            new JLabel("Doanh thu: ");

    private final JTextArea txtKetQua =
            new JTextArea();

    private final ThongKeBUS bus =
            new ThongKeBUS();

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ISO_LOCAL_DATE;

    private JButton btnDoanhThu;
    private JButton btnTimHoaDon;
    private JButton btnCaoNhat;
    private JButton btnBanChay;

    public ThongKePanel() {

        setLayout(
                new BorderLayout(10, 10)
        );

        txtKetQua.setEditable(false);

        add(
                createTop(),
                BorderLayout.NORTH
        );

        add(
                new JScrollPane(txtKetQua),
                BorderLayout.CENTER
        );
    }

    private JPanel createTop() {

        JPanel panel =
                new JPanel(
                        new GridLayout(4, 4, 8, 8)
                );

        panel.setBorder(
                BorderFactory.createTitledBorder(
                        "Thống kê"
                )
        );

        panel.add(
                new JLabel("Từ ngày (yyyy-MM-dd):")
        );

        panel.add(txtTuNgay);

        panel.add(
                new JLabel("Đến ngày:")
        );

        panel.add(txtDenNgay);

        btnDoanhThu =
                new JButton("Tính doanh thu");

        btnDoanhThu.addActionListener(
                e -> tinhDoanhThu()
        );

        panel.add(btnDoanhThu);

        panel.add(lblDoanhThu);

        panel.add(
                new JLabel("Mã khách hàng:")
        );

        panel.add(txtMaKh);

        btnTimHoaDon =
                new JButton("Tìm hóa đơn");

        btnTimHoaDon.addActionListener(
                e -> timHoaDon()
        );

        panel.add(btnTimHoaDon);

        btnCaoNhat =
                new JButton("Hóa đơn cao nhất");

        btnCaoNhat.addActionListener(
                e -> hoaDonCaoNhat()
        );

        panel.add(btnCaoNhat);

        btnBanChay =
                new JButton("Sản phẩm bán chạy");

        btnBanChay.addActionListener(
                e -> sanPhamBanChay()
        );

        panel.add(btnBanChay);

        return panel;
    }

    // =========================================================
    // BÀI: TÍNH DOANH THU
    // =========================================================

    private void tinhDoanhThu() {

        try {

            LocalDate tuNgay =
                    LocalDate.parse(
                            txtTuNgay.getText().trim(),
                            formatter
                    );

            LocalDate denNgay =
                    LocalDate.parse(
                            txtDenNgay.getText().trim(),
                            formatter
                    );

            if (tuNgay.isAfter(denNgay)) {

                showError(
                        new Exception(
                                "Từ ngày không được lớn hơn đến ngày."
                        )
                );

                return;
            }

            setButtonsEnabled(false);

            SwingWorker<BigDecimal, Void> worker =
                    new SwingWorker<BigDecimal, Void>() {

                        @Override
                        protected BigDecimal doInBackground()
                                throws Exception {

                            return bus.tinhDoanhThu(
                                    tuNgay,
                                    denNgay
                            );
                        }

                        @Override
                        protected void done() {

                            try {

                                BigDecimal doanhThu = get();

                                lblDoanhThu.setText(
                                        "Doanh thu: "
                                                + doanhThu
                                                + " VND"
                                );

                            } catch (Exception e) {

                                showWorkerError(e);

                            } finally {

                                setButtonsEnabled(true);
                            }
                        }
                    };

            worker.execute();

        } catch (Exception e) {

            showError(e);
        }
    }

    // =========================================================
    // BÀI: TÌM HÓA ĐƠN
    // =========================================================

    private void timHoaDon() {

        try {

            LocalDate tuNgay =
                    LocalDate.parse(
                            txtTuNgay.getText().trim(),
                            formatter
                    );

            LocalDate denNgay =
                    LocalDate.parse(
                            txtDenNgay.getText().trim(),
                            formatter
                    );

            if (tuNgay.isAfter(denNgay)) {

                showError(
                        new Exception(
                                "Từ ngày không được lớn hơn đến ngày."
                        )
                );

                return;
            }

            final int maKh;

            String textMaKh =
                    txtMaKh.getText().trim();

            if (textMaKh.isEmpty()) {

                maKh = 0;

            } else {

                maKh =
                        Integer.parseInt(textMaKh);

                if (maKh < 0) {

                    showError(
                            new Exception(
                                    "Mã khách hàng không hợp lệ."
                            )
                    );

                    return;
                }
            }

            setButtonsEnabled(false);

            SwingWorker<String, Void> worker =
                    new SwingWorker<String, Void>() {

                        @Override
                        protected String doInBackground()
                                throws Exception {

                            return bus.timHoaDon(
                                    tuNgay,
                                    denNgay,
                                    maKh
                            );
                        }

                        @Override
                        protected void done() {

                            try {

                                txtKetQua.setText(
                                        get()
                                );

                            } catch (Exception e) {

                                showWorkerError(e);

                            } finally {

                                setButtonsEnabled(true);
                            }
                        }
                    };

            worker.execute();

        } catch (Exception e) {

            showError(e);
        }
    }

    // =========================================================
    // HÓA ĐƠN CAO NHẤT
    // =========================================================

    private void hoaDonCaoNhat() {

        setButtonsEnabled(false);

        SwingWorker<String, Void> worker =
                new SwingWorker<String, Void>() {

                    @Override
                    protected String doInBackground()
                            throws Exception {

                        return bus.hoaDonCaoNhat();
                    }

                    @Override
                    protected void done() {

                        try {

                            txtKetQua.setText(
                                    get()
                            );

                        } catch (Exception e) {

                            showWorkerError(e);

                        } finally {

                            setButtonsEnabled(true);
                        }
                    }
                };

        worker.execute();
    }

    // =========================================================
    // SẢN PHẨM BÁN CHẠY
    // =========================================================

    private void sanPhamBanChay() {

        setButtonsEnabled(false);

        SwingWorker<String, Void> worker =
                new SwingWorker<String, Void>() {

                    @Override
                    protected String doInBackground()
                            throws Exception {

                        return bus.sanPhamBanChayNhat();
                    }

                    @Override
                    protected void done() {

                        try {

                            txtKetQua.setText(
                                    get()
                            );

                        } catch (Exception e) {

                            showWorkerError(e);

                        } finally {

                            setButtonsEnabled(true);
                        }
                    }
                };

        worker.execute();
    }

    // =========================================================
    // KHÓA / MỞ BUTTON
    // =========================================================

    private void setButtonsEnabled(
            boolean enabled
    ) {

        if (btnDoanhThu != null) {
            btnDoanhThu.setEnabled(enabled);
        }

        if (btnTimHoaDon != null) {
            btnTimHoaDon.setEnabled(enabled);
        }

        if (btnCaoNhat != null) {
            btnCaoNhat.setEnabled(enabled);
        }

        if (btnBanChay != null) {
            btnBanChay.setEnabled(enabled);
        }
    }

    // =========================================================
    // XỬ LÝ LỖI SWINGWORKER
    // =========================================================

    private void showWorkerError(
            Exception e
    ) {

        Throwable cause =
                e.getCause();

        if (cause != null) {

            showError(
                    new Exception(
                            cause.getMessage(),
                            cause
                    )
            );

        } else {

            showError(e);
        }
    }

    // =========================================================
    // HIỂN THỊ LỖI
    // =========================================================

    private void showError(
            Exception e
    ) {

        String message =
                e.getMessage();

        if (message == null ||
                message.trim().isEmpty()) {

            message =
                    "Đã xảy ra lỗi không xác định.";
        }

        JOptionPane.showMessageDialog(
                this,
                message,
                "Lỗi",
                JOptionPane.ERROR_MESSAGE
        );
    }
}