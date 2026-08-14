package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.DanhMucBUS;
import vn.edu.eaut.lab5.model.DanhMuc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DanhMucPanel extends JPanel {

    private final JTextField txtMaDm = new JTextField();
    private final JTextField txtTenDm = new JTextField();

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Mã danh mục", "Tên danh mục"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final JTable table = new JTable(model);
    private final DanhMucBUS bus = new DanhMucBUS();

    private final JButton btnThem = new JButton("Thêm");
    private final JButton btnSua = new JButton("Sửa");
    private final JButton btnXoa = new JButton("Xóa");
    private final JButton btnLamMoi = new JButton("Làm mới");

    public DanhMucPanel() {
        setLayout(new BorderLayout(10, 10));
        txtMaDm.setEditable(false);

        add(createForm(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(createButtons(), BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(e -> {
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
        JPanel panel = new JPanel(new GridLayout(2, 2, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin danh mục sản phẩm"));

        panel.add(new JLabel("Mã danh mục:"));
        panel.add(txtMaDm);

        panel.add(new JLabel("Tên danh mục:"));
        panel.add(txtTenDm);

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
            List<DanhMuc> list = bus.findAll();
            model.setRowCount(0);
            for (DanhMuc dm : list) {
                model.addRow(new Object[]{dm.getMaDm(), dm.getTenDm()});
            }
        } catch (Exception e) {
            showError(e);
        }
    }

    private void loadSelectedRow() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        txtMaDm.setText(model.getValueAt(row, 0).toString());
        txtTenDm.setText(model.getValueAt(row, 1).toString());
    }

    private void save() {
        try {
            DanhMuc dm = new DanhMuc();
            if (!txtMaDm.getText().trim().isEmpty()) {
                dm.setMaDm(Integer.parseInt(txtMaDm.getText().trim()));
            }
            dm.setTenDm(txtTenDm.getText().trim());

            if (bus.save(dm)) {
                JOptionPane.showMessageDialog(this,
                        dm.getMaDm() == 0 ? "Thêm danh mục thành công!" : "Cập nhật danh mục thành công!");
                clearForm();
                loadData();
            }
        } catch (Exception e) {
            showError(e);
        }
    }

    private void delete() {
        try {
            if (txtMaDm.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hãy chọn danh mục cần xóa.");
                return;
            }

            int maDm = Integer.parseInt(txtMaDm.getText().trim());
            int choice = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn xóa danh mục này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (choice != JOptionPane.YES_OPTION) return;

            if (bus.delete(maDm)) {
                JOptionPane.showMessageDialog(this, "Xóa danh mục thành công!");
                clearForm();
                loadData();
            }
        } catch (Exception e) {
            showError(e);
        }
    }

    private void clearForm() {
        txtMaDm.setText("");
        txtTenDm.setText("");
        table.clearSelection();
    }

    private void showError(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
