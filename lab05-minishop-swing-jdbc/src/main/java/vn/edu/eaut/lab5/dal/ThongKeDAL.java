package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ThongKeDAL {

    // =========================================================
    // 1. TÍNH DOANH THU THEO KHOẢNG NGÀY
    // =========================================================

    public BigDecimal tinhDoanhThu(
            LocalDate tuNgay,
            LocalDate denNgay) throws SQLException {

        String sql =
                "SELECT COALESCE(SUM(tong_tien), 0) AS doanh_thu " +
                "FROM hoa_don " +
                "WHERE ngay_lap BETWEEN ? AND ?";

        try (
                Connection conn = DBHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setDate(1, Date.valueOf(tuNgay));
            ps.setDate(2, Date.valueOf(denNgay));

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    BigDecimal doanhThu =
                            rs.getBigDecimal("doanh_thu");

                    if (doanhThu != null) {
                        return doanhThu;
                    }
                }
            }
        }

        return BigDecimal.ZERO;
    }


    // =========================================================
    // 2. TÌM HÓA ĐƠN CÓ GIÁ TRỊ CAO NHẤT
    // =========================================================

    public String hoaDonCaoNhat()
            throws SQLException {

        String sql =
                "SELECT ma_hd, ngay_lap, ma_kh, tong_tien " +
                "FROM hoa_don " +
                "ORDER BY tong_tien DESC " +
                "LIMIT 1";

        try (
                Connection conn = DBHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {

                return "Mã HD: "
                        + rs.getInt("ma_hd")
                        + " | Ngày: "
                        + rs.getDate("ngay_lap")
                        + " | Mã KH: "
                        + rs.getInt("ma_kh")
                        + " | Tổng tiền: "
                        + rs.getBigDecimal("tong_tien")
                        + " VND";
            }
        }

        return "Chưa có hóa đơn.";
    }


    // =========================================================
    // 3. TÌM SẢN PHẨM BÁN CHẠY NHẤT
    // =========================================================

    public String sanPhamBanChayNhat()
            throws SQLException {

        String sql =
                "SELECT sp.ma_sp, " +
                "       sp.ten_sp, " +
                "       SUM(ct.so_luong) AS tong_so_luong " +
                "FROM chi_tiet_hoa_don ct " +
                "JOIN san_pham sp " +
                "     ON ct.ma_sp = sp.ma_sp " +
                "GROUP BY sp.ma_sp, sp.ten_sp " +
                "ORDER BY tong_so_luong DESC " +
                "LIMIT 1";

        try (
                Connection conn = DBHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {

                return "Mã SP: "
                        + rs.getInt("ma_sp")
                        + " | Tên: "
                        + rs.getString("ten_sp")
                        + " | Đã bán: "
                        + rs.getInt("tong_so_luong")
                        + " sản phẩm";
            }
        }

        return "Chưa có dữ liệu bán hàng.";
    }


    // =========================================================
    // 4. TÌM HÓA ĐƠN THEO KHOẢNG NGÀY
    //    VÀ CÓ THỂ LỌC THEO MÃ KHÁCH HÀNG
    // =========================================================

    public String timHoaDon(
            LocalDate tuNgay,
            LocalDate denNgay,
            int maKh) throws SQLException {

        String sql;

        if (maKh > 0) {

            sql =
                    "SELECT ma_hd, ngay_lap, ma_kh, tong_tien " +
                    "FROM hoa_don " +
                    "WHERE ngay_lap BETWEEN ? AND ? " +
                    "AND ma_kh = ? " +
                    "ORDER BY ma_hd DESC";

        } else {

            sql =
                    "SELECT ma_hd, ngay_lap, ma_kh, tong_tien " +
                    "FROM hoa_don " +
                    "WHERE ngay_lap BETWEEN ? AND ? " +
                    "ORDER BY ma_hd DESC";
        }


        StringBuilder result =
                new StringBuilder();

        try (
                Connection conn = DBHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            // Tham số ngày bắt đầu
            ps.setDate(
                    1,
                    Date.valueOf(tuNgay)
            );

            // Tham số ngày kết thúc
            ps.setDate(
                    2,
                    Date.valueOf(denNgay)
            );

            // Nếu có mã khách hàng
            if (maKh > 0) {
                ps.setInt(3, maKh);
            }

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    result.append("Mã HD: ")
                            .append(rs.getInt("ma_hd"))

                            .append(" | Ngày: ")
                            .append(rs.getDate("ngay_lap"))

                            .append(" | Mã KH: ")
                            .append(rs.getInt("ma_kh"))

                            .append(" | Tổng: ")
                            .append(rs.getBigDecimal("tong_tien"))

                            .append(" VND\n");
                }
            }
        }


        if (result.length() == 0) {
            return "Không tìm thấy hóa đơn.";
        }

        return result.toString();
    }
}