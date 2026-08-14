package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.HoaDon;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAL {

    public List<HoaDon> findAllInvoices() throws SQLException {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT hd.ma_hd, hd.ngay_lap, hd.ma_kh, kh.ten_kh, kh.sdt, hd.tong_tien " +
                     "FROM hoa_don hd " +
                     "LEFT JOIN khach_hang kh ON hd.ma_kh = kh.ma_kh " +
                     "ORDER BY hd.ma_hd DESC";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHd(rs.getInt("ma_hd"));
                Date ngay = rs.getDate("ngay_lap");
                if (ngay != null) {
                    hd.setNgayLap(ngay.toLocalDate());
                }
                hd.setMaKh(rs.getInt("ma_kh"));
                hd.setTenKh(rs.getString("ten_kh"));
                hd.setSdtKh(rs.getString("sdt"));
                hd.setTongTien(rs.getBigDecimal("tong_tien"));
                list.add(hd);
            }
        }
        return list;
    }

    public List<ChiTietHoaDon> getChiTietHoaDon(int maHd) throws SQLException {
        List<ChiTietHoaDon> list = new ArrayList<>();
        String sql = "SELECT ct.ma_hd, ct.ma_sp, sp.ten_sp, ct.so_luong, ct.don_gia, ct.thanh_tien " +
                     "FROM chi_tiet_hoa_don ct " +
                     "LEFT JOIN san_pham sp ON ct.ma_sp = sp.ma_sp " +
                     "WHERE ct.ma_hd = ?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHd);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietHoaDon ct = new ChiTietHoaDon();
                    ct.setMaHd(rs.getInt("ma_hd"));
                    ct.setMaSp(rs.getInt("ma_sp"));
                    ct.setTenSp(rs.getString("ten_sp"));
                    ct.setSoLuong(rs.getInt("so_luong"));
                    ct.setDonGia(rs.getBigDecimal("don_gia"));
                    ct.setThanhTien(rs.getBigDecimal("thanh_tien"));
                    list.add(ct);
                }
            }
        }
        return list;
    }

    public int insertHoaDon(
            int maKh,
            List<ChiTietHoaDon> chiTietList)
            throws SQLException {

        if (chiTietList == null || chiTietList.isEmpty()) {
            throw new SQLException(
                    "Hóa đơn phải có ít nhất một sản phẩm."
            );
        }

        String sqlHoaDon =
                "INSERT INTO hoa_don " +
                "(ngay_lap, ma_kh, tong_tien) " +
                "VALUES (?, ?, ?)";

        String sqlChiTiet =
                "INSERT INTO chi_tiet_hoa_don " +
                "(ma_hd, ma_sp, so_luong, don_gia, thanh_tien) " +
                "VALUES (?, ?, ?, ?, ?)";

        String sqlTonKho =
                "SELECT so_luong, don_gia " +
                "FROM san_pham " +
                "WHERE ma_sp = ? " +
                "FOR UPDATE";

        String sqlTruKho =
                "UPDATE san_pham " +
                "SET so_luong = so_luong - ? " +
                "WHERE ma_sp = ?";

        BigDecimal tongTien = BigDecimal.ZERO;

        for (ChiTietHoaDon ct : chiTietList) {
            if (ct.getSoLuong() <= 0) {
                throw new SQLException(
                        "Số lượng bán phải lớn hơn 0."
                );
            }
            tongTien = tongTien.add(ct.getThanhTien());
        }

        try (Connection conn = DBHelper.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // 1. Kiem tra va tru kho
                for (ChiTietHoaDon ct : chiTietList) {
                    try (PreparedStatement ps = conn.prepareStatement(sqlTonKho)) {
                        ps.setInt(1, ct.getMaSp());
                        try (ResultSet rs = ps.executeQuery()) {
                            if (!rs.next()) {
                                throw new SQLException(
                                        "Không tìm thấy sản phẩm mã: " + ct.getMaSp()
                                );
                            }

                            int tonKho = rs.getInt("so_luong");
                            BigDecimal donGia = rs.getBigDecimal("don_gia");

                            if (tonKho <= 0) {
                                throw new SQLException(
                                        "Sản phẩm mã " + ct.getMaSp() + " đã hết hàng."
                                );
                            }

                            if (ct.getSoLuong() > tonKho) {
                                throw new SQLException(
                                        "Không đủ tồn kho cho sản phẩm mã " + ct.getMaSp() +
                                        " (Tồn: " + tonKho + ", Yêu cầu: " + ct.getSoLuong() + ")"
                                );
                            }

                            ct.setDonGia(donGia);
                            ct.setThanhTien(donGia.multiply(BigDecimal.valueOf(ct.getSoLuong())));
                        }
                    }
                }

                // Tinh lai tong tien
                tongTien = BigDecimal.ZERO;
                for (ChiTietHoaDon ct : chiTietList) {
                    tongTien = tongTien.add(ct.getThanhTien());
                }

                // 2. Them hoa don
                int maHd;
                try (PreparedStatement ps = conn.prepareStatement(sqlHoaDon, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setDate(1, Date.valueOf(LocalDate.now()));
                    ps.setInt(2, maKh);
                    ps.setBigDecimal(3, tongTien);
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) {
                            throw new SQLException("Không tạo được mã hóa đơn.");
                        }
                        maHd = rs.getInt(1);
                    }
                }

                // 3. Them chi tiet
                for (ChiTietHoaDon ct : chiTietList) {
                    try (PreparedStatement ps = conn.prepareStatement(sqlChiTiet)) {
                        ps.setInt(1, maHd);
                        ps.setInt(2, ct.getMaSp());
                        ps.setInt(3, ct.getSoLuong());
                        ps.setBigDecimal(4, ct.getDonGia());
                        ps.setBigDecimal(5, ct.getThanhTien());
                        ps.executeUpdate();
                    }
                }

                // 4. Tru kho
                for (ChiTietHoaDon ct : chiTietList) {
                    try (PreparedStatement ps = conn.prepareStatement(sqlTruKho)) {
                        ps.setInt(1, ct.getSoLuong());
                        ps.setInt(2, ct.getMaSp());
                        ps.executeUpdate();
                    }
                }

                conn.commit();
                return maHd;

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}