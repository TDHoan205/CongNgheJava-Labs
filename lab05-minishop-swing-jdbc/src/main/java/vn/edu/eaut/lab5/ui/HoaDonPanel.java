package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.HoaDonBUS;
import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.HoaDon;
import vn.edu.eaut.lab5.model.KhachHang;
import vn.edu.eaut.lab5.model.SanPham;
import vn.edu.eaut.lab5.util.HoaDonExportUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HoaDonPanel extends JPanel {

    // Sub-tab 1: Lập hóa đơn mới
    private final JComboBox<KhachHang> cboKhachHang = new JComboBox<>();
    private final JComboBox<SanPham> cboSanPham = new JComboBox<>();
    private final JTextField txtTonKho = new JTextField();
    private final JTextField txtSoLuong = new JTextField();
    private final JLabel lblStockWarning = new JLabel(" ");
    private final JLabel lblTongTien = new JLabel("Tổng tiền: 0 VND");

    private final DefaultTableModel modelNewInvoice = new DefaultTableModel(
            new Object[]{"Mã SP", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable tableNewInvoice = new JTable(modelNewInvoice);
    private final List<ChiTietHoaDon> chiTietList = new ArrayList<>();

    // Sub-tab 2: Danh sách hóa đơn & Xuất file
    private final DefaultTableModel modelInvoiceList = new DefaultTableModel(
            new Object[]{"Mã HĐ", "Ngày lập", "Tên khách hàng", "SĐT", "Tổng tiền (VND)"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable tableInvoiceList = new JTable(modelInvoiceList);

    private final DefaultTableModel modelInvoiceDetails = new DefaultTableModel(
            new Object[]{"Mã SP", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable tableInvoiceDetails = new JTable(modelInvoiceDetails);

    private final KhachHangBUS khachHangBUS = new KhachHangBUS();
    private final SanPhamBUS sanPhamBUS = new SanPhamBUS();
    private final HoaDonBUS hoaDonBUS = new HoaDonBUS();

    public HoaDonPanel() {
        setLayout(new BorderLayout());

        JTabbedPane subTabs = new JTabbedPane();
        subTabs.addTab("Lập hóa đơn mới", createNewInvoiceTab());
        subTabs.addTab("Danh sách & Xuất file HĐ (TXT/CSV)", createInvoiceHistoryTab());

        add(subTabs, BorderLayout.CENTER);

        txtTonKho.setEditable(false);
        cboSanPham.addActionListener(e -> updateStockDisplay());

        loadCombobox();
        loadInvoiceHistory();
    }

    // ==========================================
    // SUB-TAB 1: LẬP HÓA ĐƠN MỚI
    // ==========================================
    private JPanel createNewInvoiceTab() {
        JPanel main = new JPanel(new BorderLayout(10, 10));

        JPanel top = new JPanel(new GridLayout(3, 4, 8, 8));
        top.setBorder(BorderFactory.createTitledBorder("Thông tin lập hóa đơn & Tồn kho sản phẩm"));

        top.add(new JLabel("Khách hàng:"));
        top.add(cboKhachHang);

        top.add(new JLabel("Sản phẩm:"));
        top.add(cboSanPham);

        top.add(new JLabel("Tồn kho hiện tại:"));
        top.add(txtTonKho);

        top.add(new JLabel("Số lượng bán:"));
        top.add(txtSoLuong);

        JButton btnThem = new JButton("Thêm sản phẩm");
        btnThem.addActionListener(e -> addProduct());
        top.add(btnThem);

        JButton btnXoa = new JButton("Xóa dòng");
        btnXoa.addActionListener(e -> removeSelected());
        top.add(btnXoa);

        lblStockWarning.setFont(new Font("Arial", Font.BOLD, 12));
        top.add(new JLabel("Trạng thái tồn:"));
        top.add(lblStockWarning);

        main.add(top, BorderLayout.NORTH);
        main.add(new JScrollPane(tableNewInvoice), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 16));
        bottom.add(lblTongTien, BorderLayout.WEST);

        JButton btnLuu = new JButton("Lưu hóa đơn & Xuất File");
        btnLuu.setFont(new Font("Arial", Font.BOLD, 13));
        btnLuu.setBackground(new Color(40, 167, 69));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.addActionListener(e -> saveInvoice());
        bottom.add(btnLuu, BorderLayout.EAST);

        main.add(bottom, BorderLayout.SOUTH);
        return main;
    }

    private void updateStockDisplay() {
        SanPham sp = (SanPham) cboSanPham.getSelectedItem();
        if (sp == null) {
            txtTonKho.setText("0");
            lblStockWarning.setText(" ");
            return;
        }

        txtTonKho.setText(String.valueOf(sp.getSoLuong()));

        int ton = sp.getSoLuong();
        if (ton == 0) {
            lblStockWarning.setText("HẾT HÀNG! (Không thể bán)");
            lblStockWarning.setForeground(Color.RED);
        } else if (ton < 5) {
            lblStockWarning.setText("CẢNH BÁO: Tồn kho thấp (" + ton + ")");
            lblStockWarning.setForeground(new Color(220, 53, 69));
        } else {
            lblStockWarning.setText("Bình thường (Tồn: " + ton + ")");
            lblStockWarning.setForeground(new Color(40, 167, 69));
        }
    }

    private void loadCombobox() {
        try {
            cboKhachHang.removeAllItems();
            for (KhachHang kh : khachHangBUS.findAll()) {
                cboKhachHang.addItem(kh);
            }

            cboSanPham.removeAllItems();
            for (SanPham sp : sanPhamBUS.findAll()) {
                cboSanPham.addItem(sp);
            }

            updateStockDisplay();
        } catch (Exception e) {
            showError(e);
        }
    }

    private void addProduct() {
        try {
            SanPham sp = (SanPham) cboSanPham.getSelectedItem();
            if (sp == null) {
                throw new IllegalArgumentException("Vui lòng chọn sản phẩm.");
            }

            if (sp.getSoLuong() == 0) {
                throw new IllegalArgumentException("Sản phẩm '" + sp.getTenSp() + "' đã hết hàng! Không thể lập hóa đơn.");
            }

            int soLuongBan = Integer.parseInt(txtSoLuong.getText().trim());
            if (soLuongBan <= 0) {
                throw new IllegalArgumentException("Số lượng bán phải lớn hơn 0.");
            }

            ChiTietHoaDon existing = findChiTietByMaSp(sp.getMaSp());
            int daChon = (existing != null) ? existing.getSoLuong() : 0;
            int tongYeuCau = daChon + soLuongBan;

            if (tongYeuCau > sp.getSoLuong()) {
                throw new IllegalArgumentException("Không được bán vượt quá số lượng tồn kho! (Tồn kho: "
                        + sp.getSoLuong() + ", Đã chọn: " + daChon + ", Nhập thêm: " + soLuongBan + ")");
            }

            if (sp.getSoLuong() < 5) {
                JOptionPane.showMessageDialog(this,
                        "Cảnh báo: Sản phẩm '" + sp.getTenSp() + "' hiện có tồn kho thấp (" + sp.getSoLuong() + " sản phẩm)!",
                        "Cảnh báo tồn kho", JOptionPane.WARNING_MESSAGE);
            }

            if (existing != null) {
                existing.setSoLuong(tongYeuCau);
                existing.setThanhTien(sp.getDonGia().multiply(BigDecimal.valueOf(tongYeuCau)));
                updateRowInTable(existing, findRowIndexByMaSp(sp.getMaSp()));
            } else {
                ChiTietHoaDon ct = new ChiTietHoaDon(sp.getMaSp(), sp.getTenSp(), soLuongBan, sp.getDonGia());
                chiTietList.add(ct);
                modelNewInvoice.addRow(new Object[]{ct.getMaSp(), ct.getTenSp(), ct.getSoLuong(), ct.getDonGia(), ct.getThanhTien()});
            }

            updateTotal();
            txtSoLuong.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng bán phải là số nguyên.", "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            showError(e);
        }
    }

    private ChiTietHoaDon findChiTietByMaSp(int maSp) {
        for (ChiTietHoaDon ct : chiTietList) {
            if (ct.getMaSp() == maSp) return ct;
        }
        return null;
    }

    private int findRowIndexByMaSp(int maSp) {
        for (int i = 0; i < modelNewInvoice.getRowCount(); i++) {
            Object value = modelNewInvoice.getValueAt(i, 0);
            if (value instanceof Number && ((Number) value).intValue() == maSp) return i;
        }
        return -1;
    }

    private void updateRowInTable(ChiTietHoaDon ct, int rowIndex) {
        if (rowIndex < 0) return;
        modelNewInvoice.setValueAt(ct.getMaSp(), rowIndex, 0);
        modelNewInvoice.setValueAt(ct.getTenSp(), rowIndex, 1);
        modelNewInvoice.setValueAt(ct.getSoLuong(), rowIndex, 2);
        modelNewInvoice.setValueAt(ct.getDonGia(), rowIndex, 3);
        modelNewInvoice.setValueAt(ct.getThanhTien(), rowIndex, 4);
    }

    private void removeSelected() {
        int row = tableNewInvoice.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Hãy chọn dòng cần xóa.");
            return;
        }
        chiTietList.remove(row);
        modelNewInvoice.removeRow(row);
        updateTotal();
    }

    private void updateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ChiTietHoaDon ct : chiTietList) total = total.add(ct.getThanhTien());
        lblTongTien.setText("Tổng tiền: " + total + " VND");
    }

    private void saveInvoice() {
        try {
            KhachHang kh = (KhachHang) cboKhachHang.getSelectedItem();
            if (kh == null) {
                throw new IllegalArgumentException("Vui lòng chọn khách hàng.");
            }

            if (chiTietList.isEmpty()) {
                throw new IllegalArgumentException("Hóa đơn chưa có sản phẩm.");
            }

            int maHd = hoaDonBUS.save(kh.getMaKh(), chiTietList);
            BigDecimal total = BigDecimal.ZERO;
            for (ChiTietHoaDon ct : chiTietList) total = total.add(ct.getThanhTien());

            // Tồn kho đã giảm trong DB, tải lại ComboBox & Lịch sử
            List<ChiTietHoaDon> savedList = new ArrayList<>(chiTietList);
            clearInvoice();
            loadInvoiceHistory();

            // Hiển thị lựa chọn Xuất File TXT / CSV cho người dùng
            Object[] options = {"Xuất File TXT", "Xuất File CSV", "Đóng"};
            int choice = JOptionPane.showOptionDialog(
                    this,
                    "Lưu hóa đơn thành công! Mã HĐ: " + maHd + "\nTồn kho sản phẩm đã được trừ tự động.\n\nBạn có muốn xuất hóa đơn ra tệp TXT/CSV ngay bây giờ không?",
                    "Thành công & Xuất Hóa Đơn (Bài 8)",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == 0) {
                exportSingleInvoiceTXT(maHd, LocalDate.now(), kh.getTenKh(), kh.getSdt(), savedList, total);
            } else if (choice == 1) {
                exportSingleInvoiceCSV(maHd, LocalDate.now(), kh.getTenKh(), kh.getSdt(), savedList, total);
            }

        } catch (Exception e) {
            showError(e);
        }
    }

    private void clearInvoice() {
        chiTietList.clear();
        modelNewInvoice.setRowCount(0);
        updateTotal();
        txtSoLuong.setText("");
        loadCombobox();
    }

    // ==========================================
    // SUB-TAB 2: DANH SÁCH & XUẤT HÓA ĐƠN (TXT / CSV)
    // ==========================================
    private JPanel createInvoiceHistoryTab() {
        JPanel main = new JPanel(new BorderLayout(8, 8));

        tableInvoiceList.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedInvoiceDetails();
            }
        });

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        JPanel pnlList = new JPanel(new BorderLayout());
        pnlList.setBorder(BorderFactory.createTitledBorder("Danh sách hóa đơn đã lập"));
        pnlList.add(new JScrollPane(tableInvoiceList), BorderLayout.CENTER);

        JPanel pnlDetails = new JPanel(new BorderLayout());
        pnlDetails.setBorder(BorderFactory.createTitledBorder("Chi tiết sản phẩm của hóa đơn chọn"));
        pnlDetails.add(new JScrollPane(tableInvoiceDetails), BorderLayout.CENTER);

        split.setTopComponent(pnlList);
        split.setBottomComponent(pnlDetails);
        split.setDividerLocation(200);

        main.add(split, BorderLayout.CENTER);

        JPanel bottomBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        JButton btnReload = new JButton("Tải lại danh sách");
        btnReload.addActionListener(e -> loadInvoiceHistory());
        bottomBtn.add(btnReload);

        JButton btnExportTXT = new JButton("Xuất File TXT (HoaDon_MaHD.txt)");
        btnExportTXT.addActionListener(e -> exportSelectedInvoiceTXT());
        bottomBtn.add(btnExportTXT);

        JButton btnExportCSV = new JButton("Xuất File CSV (HoaDon_MaHD.csv)");
        btnExportCSV.addActionListener(e -> exportSelectedInvoiceCSV());
        bottomBtn.add(btnExportCSV);

        main.add(bottomBtn, BorderLayout.SOUTH);
        return main;
    }

    private void loadInvoiceHistory() {
        try {
            List<HoaDon> list = hoaDonBUS.findAllInvoices();
            modelInvoiceList.setRowCount(0);
            modelInvoiceDetails.setRowCount(0);

            for (HoaDon hd : list) {
                modelInvoiceList.addRow(new Object[]{
                        hd.getMaHd(),
                        hd.getNgayLap(),
                        hd.getTenKh() != null ? hd.getTenKh() : ("Mã KH: " + hd.getMaKh()),
                        hd.getSdtKh() != null ? hd.getSdtKh() : "",
                        hd.getTongTien()
                });
            }
        } catch (Exception e) {
            showError(e);
        }
    }

    private void loadSelectedInvoiceDetails() {
        int row = tableInvoiceList.getSelectedRow();
        if (row < 0) return;

        int maHd = Integer.parseInt(modelInvoiceList.getValueAt(row, 0).toString());
        try {
            List<ChiTietHoaDon> list = hoaDonBUS.getChiTietHoaDon(maHd);
            modelInvoiceDetails.setRowCount(0);
            for (ChiTietHoaDon ct : list) {
                modelInvoiceDetails.addRow(new Object[]{
                        ct.getMaSp(),
                        ct.getTenSp(),
                        ct.getSoLuong(),
                        ct.getDonGia(),
                        ct.getThanhTien()
                });
            }
        } catch (Exception e) {
            showError(e);
        }
    }

    private void exportSelectedInvoiceTXT() {
        int row = tableInvoiceList.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn từ bảng để xuất.");
            return;
        }

        try {
            int maHd = Integer.parseInt(modelInvoiceList.getValueAt(row, 0).toString());
            LocalDate ngayLap = (LocalDate) modelInvoiceList.getValueAt(row, 1);
            String tenKh = modelInvoiceList.getValueAt(row, 2).toString();
            String sdt = modelInvoiceList.getValueAt(row, 3).toString();
            BigDecimal tongTien = (BigDecimal) modelInvoiceList.getValueAt(row, 4);

            List<ChiTietHoaDon> details = hoaDonBUS.getChiTietHoaDon(maHd);
            exportSingleInvoiceTXT(maHd, ngayLap, tenKh, sdt, details, tongTien);
        } catch (Exception e) {
            showError(e);
        }
    }

    private void exportSelectedInvoiceCSV() {
        int row = tableInvoiceList.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn từ bảng để xuất.");
            return;
        }

        try {
            int maHd = Integer.parseInt(modelInvoiceList.getValueAt(row, 0).toString());
            LocalDate ngayLap = (LocalDate) modelInvoiceList.getValueAt(row, 1);
            String tenKh = modelInvoiceList.getValueAt(row, 2).toString();
            String sdt = modelInvoiceList.getValueAt(row, 3).toString();
            BigDecimal tongTien = (BigDecimal) modelInvoiceList.getValueAt(row, 4);

            List<ChiTietHoaDon> details = hoaDonBUS.getChiTietHoaDon(maHd);
            exportSingleInvoiceCSV(maHd, ngayLap, tenKh, sdt, details, tongTien);
        } catch (Exception e) {
            showError(e);
        }
    }

    private void exportSingleInvoiceTXT(int maHd, LocalDate ngayLap, String tenKh, String sdt, List<ChiTietHoaDon> details, BigDecimal tongTien) {
        try {
            File file = HoaDonExportUtil.exportTXT(maHd, ngayLap, tenKh, sdt, details, tongTien);
            JOptionPane.showMessageDialog(this, "Xuất file TXT thành công!\nFile đã lưu tại: " + file.getAbsolutePath());
            openFile(file);
        } catch (Exception e) {
            showError(e);
        }
    }

    private void exportSingleInvoiceCSV(int maHd, LocalDate ngayLap, String tenKh, String sdt, List<ChiTietHoaDon> details, BigDecimal tongTien) {
        try {
            File file = HoaDonExportUtil.exportCSV(maHd, ngayLap, tenKh, sdt, details, tongTien);
            JOptionPane.showMessageDialog(this, "Xuất file CSV thành công!\nFile đã lưu tại: " + file.getAbsolutePath());
            openFile(file);
        } catch (Exception e) {
            showError(e);
        }
    }

    private void openFile(File file) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        } catch (Exception e) {
            // Ignore if OS cannot open file automatically
        }
    }

    private void showError(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}