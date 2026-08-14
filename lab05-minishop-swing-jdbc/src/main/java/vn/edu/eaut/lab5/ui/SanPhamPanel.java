package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.DanhMucBUS;
import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.DanhMuc;
import vn.edu.eaut.lab5.model.SanPham;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class SanPhamPanel extends JPanel {

    private final JTextField txtMaSp = new JTextField();
    private final JTextField txtTenSp = new JTextField();
    private final JTextField txtDonGia = new JTextField();
    private final JTextField txtSoLuong = new JTextField();
    private final JComboBox<DanhMuc> cboDanhMucForm = new JComboBox<>();
    
    private final JTextField txtTimKiem = new JTextField();
    private final JComboBox<DanhMuc> cboDanhMucFilter = new JComboBox<>();

    private final DefaultTableModel model =
            new DefaultTableModel(
                    new Object[]{
                            "Mã SP",
                            "Tên sản phẩm",
                            "Đơn giá",
                            "Số lượng",
                            "Danh mục"
                    }, 0) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

    private final JTable table = new JTable(model);
    private final SanPhamBUS bus = new SanPhamBUS();
    private final DanhMucBUS danhMucBUS = new DanhMucBUS();

    private final JButton btnThem = new JButton("Thêm");
    private final JButton btnSua = new JButton("Sửa");
    private final JButton btnXoa = new JButton("Xóa");
    private final JButton btnLamMoi = new JButton("Làm mới");

    public SanPhamPanel() {
        setLayout(new BorderLayout(10, 10));
        txtMaSp.setEditable(false);

        add(createForm(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(createButtons(), BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedRow();
            }
        });

        loadCategories();
        loadData();
    }

    public void refreshData() {
        loadCategories();
        loadData();
    }


    public void applyPermissions(boolean allowEdit) {
        btnThem.setEnabled(allowEdit);
        btnSua.setEnabled(allowEdit);
        btnXoa.setEnabled(allowEdit);
    }

    private void loadCategories() {
        try {
            cboDanhMucForm.removeAllItems();
            cboDanhMucFilter.removeAllItems();

            DanhMuc defaultFilter = new DanhMuc(0, "-- Tất cả danh mục --");
            cboDanhMucFilter.addItem(defaultFilter);

            cboDanhMucForm.addItem(new DanhMuc(0, "-- Chọn danh mục --"));

            List<DanhMuc> list = danhMucBUS.findAll();
            for (DanhMuc dm : list) {
                cboDanhMucForm.addItem(dm);
                cboDanhMucFilter.addItem(dm);
            }
        } catch (Exception e) {
            showError(e);
        }
    }

    private JPanel createForm() {
        JPanel panel = new JPanel(new GridLayout(4, 4, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));

        panel.add(new JLabel("Mã sản phẩm:"));
        panel.add(txtMaSp);

        panel.add(new JLabel("Tên sản phẩm:"));
        panel.add(txtTenSp);

        panel.add(new JLabel("Đơn giá:"));
        panel.add(txtDonGia);

        panel.add(new JLabel("Số lượng:"));
        panel.add(txtSoLuong);

        panel.add(new JLabel("Danh mục:"));
        panel.add(cboDanhMucForm);

        panel.add(new JLabel("Tìm tên SP:"));
        panel.add(txtTimKiem);

        panel.add(new JLabel("Lọc danh mục:"));
        panel.add(cboDanhMucFilter);

        JButton btnTim = new JButton("Lọc & Tìm kiếm");
        btnTim.addActionListener(e -> searchAndFilter());
        panel.add(btnTim);

        JButton btnTatCa = new JButton("Hiển thị tất cả");
        btnTatCa.addActionListener(e -> {
            cboDanhMucFilter.setSelectedIndex(0);
            txtTimKiem.setText("");
            loadData();
        });
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
            List<SanPham> list = bus.findAll();
            showData(list);
        } catch (Exception e) {
            showError(e);
        }
    }

    private void searchAndFilter() {
        try {
            DanhMuc selectedDm = (DanhMuc) cboDanhMucFilter.getSelectedItem();
            String keyword = txtTimKiem.getText().trim();

            List<SanPham> list;
            if (selectedDm != null && selectedDm.getMaDm() > 0) {
                list = bus.findByDanhMuc(selectedDm.getMaDm());
                if (!keyword.isEmpty()) {
                    list.removeIf(sp -> !sp.getTenSp().toLowerCase().contains(keyword.toLowerCase()));
                }
            } else {
                list = bus.searchByName(keyword);
            }

            showData(list);
        } catch (Exception e) {
            showError(e);
        }
    }

    private void showData(List<SanPham> list) {
        model.setRowCount(0);
        for (SanPham sp : list) {
            model.addRow(new Object[]{
                    sp.getMaSp(),
                    sp.getTenSp(),
                    sp.getDonGia(),
                    sp.getSoLuong(),
                    sp.getTenDm() != null ? sp.getTenDm() : "Chưa có"
            });
        }
    }

    private void loadSelectedRow() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        txtMaSp.setText(model.getValueAt(row, 0).toString());
        txtTenSp.setText(model.getValueAt(row, 1).toString());
        txtDonGia.setText(model.getValueAt(row, 2).toString());
        txtSoLuong.setText(model.getValueAt(row, 3).toString());

        String tenDm = model.getValueAt(row, 4).toString();
        cboDanhMucForm.setSelectedIndex(0);
        for (int i = 1; i < cboDanhMucForm.getItemCount(); i++) {
            DanhMuc dm = cboDanhMucForm.getItemAt(i);
            if (dm.getTenDm().equalsIgnoreCase(tenDm)) {
                cboDanhMucForm.setSelectedIndex(i);
                break;
            }
        }
    }

    private void save() {
        try {
            SanPham sp = new SanPham();
            if (!txtMaSp.getText().trim().isEmpty()) {
                sp.setMaSp(Integer.parseInt(txtMaSp.getText().trim()));
            }

            sp.setTenSp(txtTenSp.getText().trim());
            sp.setDonGia(new BigDecimal(txtDonGia.getText().trim()));
            sp.setSoLuong(Integer.parseInt(txtSoLuong.getText().trim()));

            DanhMuc dm = (DanhMuc) cboDanhMucForm.getSelectedItem();
            if (dm != null && dm.getMaDm() > 0) {
                sp.setMaDm(dm.getMaDm());
            } else {
                sp.setMaDm(null);
            }

            boolean result = bus.save(sp);
            if (result) {
                JOptionPane.showMessageDialog(this,
                        sp.getMaSp() == 0 ? "Thêm sản phẩm thành công!" : "Cập nhật sản phẩm thành công!");
                clearForm();
                loadData();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Đơn giá phải là số và số lượng phải là số nguyên.",
                    "Lỗi dữ liệu",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            showError(e);
        }
    }

    private void delete() {
        try {
            if (txtMaSp.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hãy chọn sản phẩm cần xóa.");
                return;
            }

            int maSp = Integer.parseInt(txtMaSp.getText().trim());
            int choice = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn xóa sản phẩm này?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION);

            if (choice != JOptionPane.YES_OPTION) return;

            if (bus.delete(maSp)) {
                JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
                clearForm();
                loadData();
            }
        } catch (Exception e) {
            showError(e);
        }
    }

    private void clearForm() {
        txtMaSp.setText("");
        txtTenSp.setText("");
        txtDonGia.setText("");
        txtSoLuong.setText("");
        cboDanhMucForm.setSelectedIndex(0);
        table.clearSelection();
    }

    private void showError(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}