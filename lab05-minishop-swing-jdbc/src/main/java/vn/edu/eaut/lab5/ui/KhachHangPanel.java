package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.model.KhachHang;
import vn.edu.eaut.lab5.util.PhoneDocumentFilter;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class KhachHangPanel extends JPanel {

    private final JTextField txtMaKh = new JTextField();
    private final JTextField txtTenKh = new JTextField();
    private final JTextField txtSdt = new JTextField();
    private final JTextField txtDiaChi = new JTextField();
    private final JTextField txtTimKiem = new JTextField();

    private final DefaultTableModel model =
            new DefaultTableModel(
                    new Object[]{
                            "Mã KH",
                            "Tên khách hàng",
                            "SĐT",
                            "Địa chỉ"
                    }, 0) {

                @Override
                public boolean isCellEditable(
                        int row,
                        int column) {
                    return false;
                }
            };

    private final JTable table =
            new JTable(model);

    private final KhachHangBUS bus =
            new KhachHangBUS();

    private final JButton btnThem = new JButton("Thêm");
    private final JButton btnSua = new JButton("Sửa");
    private final JButton btnXoa = new JButton("Xóa");
    private final JButton btnLamMoi = new JButton("Làm mới");

    public KhachHangPanel() {

        setLayout(new BorderLayout(10, 10));

        txtMaKh.setEditable(false);

        ((AbstractDocument) txtSdt.getDocument())
                .setDocumentFilter(
                        new PhoneDocumentFilter()
                );

        add(createForm(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(createButtons(), BorderLayout.SOUTH);

        table.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        loadSelectedRow();
                    }
                });

        loadData();
    }

    public void applyPermissions(boolean allowEdit) {
        btnThem.setEnabled(allowEdit);
        btnSua.setEnabled(allowEdit);
        btnXoa.setEnabled(allowEdit);
    }


    private JPanel createForm() {

        JPanel panel =
                new JPanel(
                        new GridLayout(3, 4, 8, 8)
                );

        panel.setBorder(
                BorderFactory.createTitledBorder(
                        "Thông tin khách hàng"
                )
        );

        panel.add(new JLabel("Mã KH:"));
        panel.add(txtMaKh);

        panel.add(new JLabel("Tên khách hàng:"));
        panel.add(txtTenKh);

        panel.add(new JLabel("Số điện thoại:"));
        panel.add(txtSdt);

        panel.add(new JLabel("Địa chỉ:"));
        panel.add(txtDiaChi);

        panel.add(new JLabel("Tìm kiếm:"));
        panel.add(txtTimKiem);

        JButton btnTim =
                new JButton("Tìm kiếm");

        btnTim.addActionListener(e -> search());

        panel.add(btnTim);

        JButton btnTatCa =
                new JButton("Hiển thị tất cả");

        btnTatCa.addActionListener(e -> loadData());

        panel.add(btnTatCa);

        return panel;
    }

    private JPanel createButtons() {

        JPanel panel = new JPanel();

        btnThem.addActionListener(e -> save());
        btnSua.addActionListener(e -> save());
        btnXoa.addActionListener(e -> delete());
        btnLamMoi.addActionListener(e -> clearForm());

        panel.add(btnThem);
        panel.add(btnSua);
        panel.add(btnXoa);
        panel.add(btnLamMoi);

        return panel;
    }


    private void loadData() {

        try {

            showData(bus.findAll());

        } catch (Exception e) {

            showError(e);
        }
    }

    private void search() {

        try {

            showData(
                    bus.search(
                            txtTimKiem.getText()
                    )
            );

        } catch (Exception e) {

            showError(e);
        }
    }

    private void showData(
            List<KhachHang> list) {

        model.setRowCount(0);

        for (KhachHang kh : list) {

            model.addRow(
                    new Object[]{
                            kh.getMaKh(),
                            kh.getTenKh(),
                            kh.getSdt(),
                            kh.getDiaChi()
                    }
            );
        }
    }

    private void loadSelectedRow() {

        int row =
                table.getSelectedRow();

        if (row < 0) {
            return;
        }

        txtMaKh.setText(
                model.getValueAt(row, 0).toString()
        );

        txtTenKh.setText(
                model.getValueAt(row, 1).toString()
        );

        txtSdt.setText(
                model.getValueAt(row, 2).toString()
        );

        txtDiaChi.setText(
                model.getValueAt(row, 3).toString()
        );
    }

    private void save() {

        try {

            KhachHang kh =
                    new KhachHang();

            if (!txtMaKh.getText()
                    .trim().isEmpty()) {

                kh.setMaKh(
                        Integer.parseInt(
                                txtMaKh.getText()
                        )
                );
            }

            kh.setTenKh(
                    txtTenKh.getText().trim()
            );

            kh.setSdt(
                    txtSdt.getText().trim()
            );

            kh.setDiaChi(
                    txtDiaChi.getText().trim()
            );

            if (bus.save(kh)) {

                JOptionPane.showMessageDialog(
                        this,
                        kh.getMaKh() == 0
                                ? "Thêm khách hàng thành công!"
                                : "Cập nhật khách hàng thành công!"
                );

                clearForm();
                loadData();
            }

        } catch (Exception e) {

            showError(e);
        }
    }

    private void delete() {

        try {

            if (txtMaKh.getText()
                    .trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Hãy chọn khách hàng cần xóa."
                );

                return;
            }

            int maKh =
                    Integer.parseInt(
                            txtMaKh.getText()
                    );

            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Bạn có chắc muốn xóa?",
                            "Xác nhận",
                            JOptionPane.YES_NO_OPTION
                    );

            if (choice != JOptionPane.YES_OPTION) {
                return;
            }

            if (bus.delete(maKh)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Xóa khách hàng thành công!"
                );

                clearForm();
                loadData();
            }

        } catch (Exception e) {

            showError(e);
        }
    }

    private void clearForm() {

        txtMaKh.setText("");
        txtTenKh.setText("");
        txtSdt.setText("");
        txtDiaChi.setText("");

        table.clearSelection();
    }

    private void showError(Exception e) {

        JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE
        );
    }
}