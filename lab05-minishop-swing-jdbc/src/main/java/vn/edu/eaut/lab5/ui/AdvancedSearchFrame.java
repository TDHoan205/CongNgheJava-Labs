package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.AdvancedSearchBUS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class AdvancedSearchFrame extends JFrame {

    private final AdvancedSearchBUS bus = new AdvancedSearchBUS();

    public AdvancedSearchFrame() {
        setTitle("Bài 9 - Tìm kiếm nâng cao và Phân trang (SwingWorker)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(980, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Sản phẩm", createProductSearchPanel());
        tabs.addTab("Khách hàng", createCustomerSearchPanel());
        tabs.addTab("Hóa đơn", createInvoiceSearchPanel());

        add(tabs);
    }

    // ==========================================
    // TAB 1: SẢN PHẨM
    // ==========================================
    private JPanel createProductSearchPanel() {
        JPanel main = new JPanel(new BorderLayout(5, 5));

        JTextField txtTen = new JTextField(10);
        JTextField txtGiaTu = new JTextField(6);
        JTextField txtGiaDen = new JTextField(6);
        JTextField txtSlTu = new JTextField(4);
        JTextField txtSlDen = new JTextField(4);
        JComboBox<String> cboSort = new JComboBox<>(new String[]{"Mã", "Tên", "Đơn giá"});
        JCheckBox chkAsc = new JCheckBox("Tăng dần", true);

        JTable table = new JTable();
        JLabel lblPage = new JLabel("Trang 1 / 1");
        final int[] currentPage = {0};
        final int pageSize = 10;
        final int[] totalRows = {0};

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Tên SP:")); top.add(txtTen);
        top.add(new JLabel("Giá từ:")); top.add(txtGiaTu);
        top.add(new JLabel("đến:")); top.add(txtGiaDen);
        top.add(new JLabel("SL từ:")); top.add(txtSlTu);
        top.add(new JLabel("đến:")); top.add(txtSlDen);
        top.add(new JLabel("Sắp xếp:")); top.add(cboSort);
        top.add(chkAsc);

        JButton btnSearch = new JButton("Tìm kiếm");
        top.add(btnSearch);

        JPanel bottom = new JPanel(new FlowLayout());
        JButton btnFirst = new JButton("Đầu");
        JButton btnPrev = new JButton("Trước");
        JButton btnNext = new JButton("Sau");
        JButton btnLast = new JButton("Cuối");

        bottom.add(btnFirst); bottom.add(btnPrev);
        bottom.add(lblPage);
        bottom.add(btnNext); bottom.add(btnLast);

        main.add(top, BorderLayout.NORTH);
        main.add(new JScrollPane(table), BorderLayout.CENTER);
        main.add(bottom, BorderLayout.SOUTH);

        Runnable loadProductData = () -> {
            new SwingWorker<List<String[]>, Void>() {
                private int count;

                @Override
                protected List<String[]> doInBackground() throws Exception {
                    BigDecimal giaTu = parseDecimal(txtGiaTu.getText());
                    BigDecimal giaDen = parseDecimal(txtGiaDen.getText());
                    Integer slTu = parseInteger(txtSlTu.getText());
                    Integer slDen = parseInteger(txtSlDen.getText());

                    String sort = "ma";
                    int idx = cboSort.getSelectedIndex();
                    if (idx == 1) sort = "ten";
                    else if (idx == 2) sort = "gia";

                    count = bus.countProducts(txtTen.getText(), giaTu, giaDen, slTu, slDen);
                    return bus.searchProducts(txtTen.getText(), giaTu, giaDen, slTu, slDen,
                            currentPage[0], pageSize, sort, chkAsc.isSelected());
                }

                @Override
                protected void done() {
                    try {
                        List<String[]> data = get();
                        totalRows[0] = count;
                        DefaultTableModel m = new DefaultTableModel(
                                new String[]{"Mã SP", "Tên sản phẩm", "Đơn giá", "Số lượng", "Danh mục"}, 0);
                        for (String[] r : data) m.addRow(r);
                        table.setModel(m);

                        int totalPages = totalRows[0] == 0 ? 1 : (int) Math.ceil((double) totalRows[0] / pageSize);
                        lblPage.setText("Trang " + (currentPage[0] + 1) + " / " + totalPages + " (Tổng: " + totalRows[0] + ")");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(AdvancedSearchFrame.this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }.execute();
        };

        btnSearch.addActionListener(e -> { currentPage[0] = 0; loadProductData.run(); });
        btnFirst.addActionListener(e -> { currentPage[0] = 0; loadProductData.run(); });
        btnPrev.addActionListener(e -> { if (currentPage[0] > 0) { currentPage[0]--; loadProductData.run(); } });
        btnNext.addActionListener(e -> {
            int maxPage = Math.max(0, (totalRows[0] - 1) / pageSize);
            if (currentPage[0] < maxPage) { currentPage[0]++; loadProductData.run(); }
        });
        btnLast.addActionListener(e -> {
            if (totalRows[0] > 0) { currentPage[0] = (totalRows[0] - 1) / pageSize; loadProductData.run(); }
        });

        loadProductData.run();
        return main;
    }

    // ==========================================
    // TAB 2: KHÁCH HÀNG
    // ==========================================
    private JPanel createCustomerSearchPanel() {
        JPanel main = new JPanel(new BorderLayout(5, 5));

        JTextField txtTen = new JTextField(12);
        JTextField txtSdt = new JTextField(10);
        JTextField txtDiaChi = new JTextField(12);

        JTable table = new JTable();
        JLabel lblPage = new JLabel("Trang 1 / 1");
        final int[] currentPage = {0};
        final int pageSize = 10;
        final int[] totalRows = {0};

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Tên KH:")); top.add(txtTen);
        top.add(new JLabel("SĐT:")); top.add(txtSdt);
        top.add(new JLabel("Địa chỉ:")); top.add(txtDiaChi);

        JButton btnSearch = new JButton("Tìm kiếm");
        top.add(btnSearch);

        JPanel bottom = new JPanel(new FlowLayout());
        JButton btnFirst = new JButton("Đầu");
        JButton btnPrev = new JButton("Trước");
        JButton btnNext = new JButton("Sau");
        JButton btnLast = new JButton("Cuối");

        bottom.add(btnFirst); bottom.add(btnPrev);
        bottom.add(lblPage);
        bottom.add(btnNext); bottom.add(btnLast);

        main.add(top, BorderLayout.NORTH);
        main.add(new JScrollPane(table), BorderLayout.CENTER);
        main.add(bottom, BorderLayout.SOUTH);

        Runnable loadCustomerData = () -> {
            new SwingWorker<List<String[]>, Void>() {
                private int count;

                @Override
                protected List<String[]> doInBackground() throws Exception {
                    count = bus.countCustomers(txtTen.getText(), txtSdt.getText(), txtDiaChi.getText());
                    return bus.searchCustomers(txtTen.getText(), txtSdt.getText(), txtDiaChi.getText(), currentPage[0], pageSize);
                }

                @Override
                protected void done() {
                    try {
                        List<String[]> data = get();
                        totalRows[0] = count;
                        DefaultTableModel m = new DefaultTableModel(
                                new String[]{"Mã KH", "Tên khách hàng", "Số điện thoại", "Địa chỉ"}, 0);
                        for (String[] r : data) m.addRow(r);
                        table.setModel(m);

                        int totalPages = totalRows[0] == 0 ? 1 : (int) Math.ceil((double) totalRows[0] / pageSize);
                        lblPage.setText("Trang " + (currentPage[0] + 1) + " / " + totalPages + " (Tổng: " + totalRows[0] + ")");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(AdvancedSearchFrame.this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }.execute();
        };

        btnSearch.addActionListener(e -> { currentPage[0] = 0; loadCustomerData.run(); });
        btnFirst.addActionListener(e -> { currentPage[0] = 0; loadCustomerData.run(); });
        btnPrev.addActionListener(e -> { if (currentPage[0] > 0) { currentPage[0]--; loadCustomerData.run(); } });
        btnNext.addActionListener(e -> {
            int maxPage = Math.max(0, (totalRows[0] - 1) / pageSize);
            if (currentPage[0] < maxPage) { currentPage[0]++; loadCustomerData.run(); }
        });
        btnLast.addActionListener(e -> {
            if (totalRows[0] > 0) { currentPage[0] = (totalRows[0] - 1) / pageSize; loadCustomerData.run(); }
        });

        loadCustomerData.run();
        return main;
    }

    // ==========================================
    // TAB 3: HÓA ĐƠN
    // ==========================================
    private JPanel createInvoiceSearchPanel() {
        JPanel main = new JPanel(new BorderLayout(5, 5));

        JTextField txtTuNgay = new JTextField(8); // yyyy-MM-dd
        JTextField txtDenNgay = new JTextField(8);
        JTextField txtMaKh = new JTextField(5);
        JTextField txtTongTu = new JTextField(7);
        JTextField txtTongDen = new JTextField(7);

        JTable table = new JTable();
        JLabel lblPage = new JLabel("Trang 1 / 1");
        final int[] currentPage = {0};
        final int pageSize = 10;
        final int[] totalRows = {0};

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Từ ngày (yyyy-MM-dd):")); top.add(txtTuNgay);
        top.add(new JLabel("Đến ngày:")); top.add(txtDenNgay);
        top.add(new JLabel("Mã KH:")); top.add(txtMaKh);
        top.add(new JLabel("Tổng từ:")); top.add(txtTongTu);
        top.add(new JLabel("đến:")); top.add(txtTongDen);

        JButton btnSearch = new JButton("Tìm kiếm");
        top.add(btnSearch);

        JPanel bottom = new JPanel(new FlowLayout());
        JButton btnFirst = new JButton("Đầu");
        JButton btnPrev = new JButton("Trước");
        JButton btnNext = new JButton("Sau");
        JButton btnLast = new JButton("Cuối");

        bottom.add(btnFirst); bottom.add(btnPrev);
        bottom.add(lblPage);
        bottom.add(btnNext); bottom.add(btnLast);

        main.add(top, BorderLayout.NORTH);
        main.add(new JScrollPane(table), BorderLayout.CENTER);
        main.add(bottom, BorderLayout.SOUTH);

        Runnable loadInvoiceData = () -> {
            new SwingWorker<List<String[]>, Void>() {
                private int count;

                @Override
                protected List<String[]> doInBackground() throws Exception {
                    Date tuNgay = parseDate(txtTuNgay.getText());
                    Date denNgay = parseDate(txtDenNgay.getText());
                    Integer maKh = parseInteger(txtMaKh.getText());
                    BigDecimal tongTu = parseDecimal(txtTongTu.getText());
                    BigDecimal tongDen = parseDecimal(txtTongDen.getText());

                    count = bus.countInvoices(tuNgay, denNgay, maKh, tongTu, tongDen);
                    return bus.searchInvoices(tuNgay, denNgay, maKh, tongTu, tongDen, currentPage[0], pageSize);
                }

                @Override
                protected void done() {
                    try {
                        List<String[]> data = get();
                        totalRows[0] = count;
                        DefaultTableModel m = new DefaultTableModel(
                                new String[]{"Mã HĐ", "Ngày lập", "Khách hàng", "Tổng tiền (VND)"}, 0);
                        for (String[] r : data) m.addRow(r);
                        table.setModel(m);

                        int totalPages = totalRows[0] == 0 ? 1 : (int) Math.ceil((double) totalRows[0] / pageSize);
                        lblPage.setText("Trang " + (currentPage[0] + 1) + " / " + totalPages + " (Tổng: " + totalRows[0] + ")");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(AdvancedSearchFrame.this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }.execute();
        };

        btnSearch.addActionListener(e -> { currentPage[0] = 0; loadInvoiceData.run(); });
        btnFirst.addActionListener(e -> { currentPage[0] = 0; loadInvoiceData.run(); });
        btnPrev.addActionListener(e -> { if (currentPage[0] > 0) { currentPage[0]--; loadInvoiceData.run(); } });
        btnNext.addActionListener(e -> {
            int maxPage = Math.max(0, (totalRows[0] - 1) / pageSize);
            if (currentPage[0] < maxPage) { currentPage[0]++; loadInvoiceData.run(); }
        });
        btnLast.addActionListener(e -> {
            if (totalRows[0] > 0) { currentPage[0] = (totalRows[0] - 1) / pageSize; loadInvoiceData.run(); }
        });

        loadInvoiceData.run();
        return main;
    }

    private BigDecimal parseDecimal(String text) {
        if (text == null || text.trim().isEmpty()) return null;
        return new BigDecimal(text.trim());
    }

    private Integer parseInteger(String text) {
        if (text == null || text.trim().isEmpty()) return null;
        return Integer.parseInt(text.trim());
    }

    private Date parseDate(String text) {
        if (text == null || text.trim().isEmpty()) return null;
        return Date.valueOf(text.trim());
    }
}