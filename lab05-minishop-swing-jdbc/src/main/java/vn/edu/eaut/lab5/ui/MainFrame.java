package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.model.TaiKhoan;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final TaiKhoan taiKhoan;
    private final JTabbedPane tabbedPane = new JTabbedPane();

    public MainFrame(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;

        setTitle("MiniShop - " + taiKhoan.getHoTen() + " (" + taiKhoan.getVaiTro() + ")");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);

        buildUI();
    }

    private void buildUI() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel lblUser = new JLabel(
                "Người dùng: " + taiKhoan.getHoTen() + " | Vai trò: " + taiKhoan.getVaiTro()
        );
        lblUser.setFont(new Font("Arial", Font.BOLD, 14));
        header.add(lblUser, BorderLayout.WEST);

        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        JButton btnAdvSearch = new JButton("Tìm kiếm nâng cao");
        btnAdvSearch.addActionListener(e -> new AdvancedSearchFrame().setVisible(true));
        rightHeader.add(btnAdvSearch);

        JButton btnLogout = new JButton("Đăng xuất");
        btnLogout.addActionListener(e -> logout());
        rightHeader.add(btnLogout);

        header.add(rightHeader, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

        createTabs();
    }

    private void createTabs() {
        SanPhamPanel productPanel = new SanPhamPanel();
        DanhMucPanel categoryPanel = new DanhMucPanel();
        KhachHangPanel customerPanel = new KhachHangPanel();
        HoaDonPanel invoicePanel = new HoaDonPanel();
        ThongKePanel statisticPanel = new ThongKePanel();

        boolean canEdit = isAdmin() || isNhanVien();

        productPanel.applyPermissions(canEdit);
        categoryPanel.applyPermissions(canEdit);
        customerPanel.applyPermissions(canEdit);

        if (isAdmin() || isNhanVien()) {
            tabbedPane.addTab("Sản phẩm", productPanel);
            tabbedPane.addTab("Danh mục", categoryPanel);
            tabbedPane.addTab("Khách hàng", customerPanel);
        }

        if (isAdmin() || isNhanVien() || isKeToan()) {
            tabbedPane.addTab("Hóa đơn", invoicePanel);
        }

        if (isAdmin() || isKeToan()) {
            tabbedPane.addTab("Thống kê", statisticPanel);
        }

        tabbedPane.addChangeListener(e -> {
            Component selected = tabbedPane.getSelectedComponent();
            if (selected instanceof SanPhamPanel) {
                ((SanPhamPanel) selected).refreshData();
            }
        });
    }


    private boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(taiKhoan.getVaiTro());
    }

    private boolean isNhanVien() {
        return "NHANVIEN".equalsIgnoreCase(taiKhoan.getVaiTro());
    }

    private boolean isKeToan() {
        return "KETOAN".equalsIgnoreCase(taiKhoan.getVaiTro());
    }

    private void logout() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn đăng xuất?",
                "Đăng xuất",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            dispose();
            SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
        }
    }
}