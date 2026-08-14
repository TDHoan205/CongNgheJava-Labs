package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdvancedSearchDAL {

    // =========================
    // TÌM KIẾM SẢN PHẨM
    // =========================
    public List<String[]> searchProducts(
            String ten,
            BigDecimal giaTu,
            BigDecimal giaDen,
            Integer soLuongTu,
            Integer soLuongDen,
            int page,
            int pageSize,
            String sortColumn,
            boolean ascending) throws SQLException {

        List<String[]> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT sp.ma_sp, sp.ten_sp, sp.don_gia, sp.so_luong, dm.ten_dm " +
                "FROM san_pham sp LEFT JOIN danh_muc dm ON sp.ma_dm = dm.ma_dm WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (ten != null && !ten.trim().isEmpty()) {
            sql.append("AND sp.ten_sp LIKE ? ");
            params.add("%" + ten.trim() + "%");
        }

        if (giaTu != null) {
            sql.append("AND sp.don_gia >= ? ");
            params.add(giaTu);
        }

        if (giaDen != null) {
            sql.append("AND sp.don_gia <= ? ");
            params.add(giaDen);
        }

        if (soLuongTu != null) {
            sql.append("AND sp.so_luong >= ? ");
            params.add(soLuongTu);
        }

        if (soLuongDen != null) {
            sql.append("AND sp.so_luong <= ? ");
            params.add(soLuongDen);
        }

        sql.append(" ORDER BY ");

        if ("ten".equalsIgnoreCase(sortColumn)) {
            sql.append("sp.ten_sp ");
        } else if ("gia".equalsIgnoreCase(sortColumn)) {
            sql.append("sp.don_gia ");
        } else {
            sql.append("sp.ma_sp ");
        }

        sql.append(ascending ? "ASC " : "DESC ");
        sql.append(" LIMIT ? OFFSET ?");

        int offset = page * pageSize;
        params.add(pageSize);
        params.add(offset);

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new String[]{
                            String.valueOf(rs.getInt("ma_sp")),
                            rs.getString("ten_sp"),
                            rs.getBigDecimal("don_gia").toString(),
                            String.valueOf(rs.getInt("so_luong")),
                            rs.getString("ten_dm") != null ? rs.getString("ten_dm") : "N/A"
                    });
                }
            }
        }

        return result;
    }

    public int countProducts(
            String ten,
            BigDecimal giaTu,
            BigDecimal giaDen,
            Integer soLuongTu,
            Integer soLuongDen) throws SQLException {

        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) FROM san_pham WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (ten != null && !ten.trim().isEmpty()) {
            sql.append("AND ten_sp LIKE ? ");
            params.add("%" + ten.trim() + "%");
        }

        if (giaTu != null) {
            sql.append("AND don_gia >= ? ");
            params.add(giaTu);
        }

        if (giaDen != null) {
            sql.append("AND don_gia <= ? ");
            params.add(giaDen);
        }

        if (soLuongTu != null) {
            sql.append("AND so_luong >= ? ");
            params.add(soLuongTu);
        }

        if (soLuongDen != null) {
            sql.append("AND so_luong <= ? ");
            params.add(soLuongDen);
        }

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    // =========================
    // TÌM KHÁCH HÀNG
    // =========================
    public List<String[]> searchCustomers(
            String ten,
            String sdt,
            String diaChi,
            int page,
            int pageSize) throws SQLException {

        List<String[]> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT ma_kh, ten_kh, sdt, dia_chi FROM khach_hang WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (ten != null && !ten.trim().isEmpty()) {
            sql.append("AND ten_kh LIKE ? ");
            params.add("%" + ten.trim() + "%");
        }

        if (sdt != null && !sdt.trim().isEmpty()) {
            sql.append("AND sdt LIKE ? ");
            params.add("%" + sdt.trim() + "%");
        }

        if (diaChi != null && !diaChi.trim().isEmpty()) {
            sql.append("AND dia_chi LIKE ? ");
            params.add("%" + diaChi.trim() + "%");
        }

        sql.append("ORDER BY ma_kh DESC LIMIT ? OFFSET ?");
        params.add(pageSize);
        params.add(page * pageSize);

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new String[]{
                            String.valueOf(rs.getInt("ma_kh")),
                            rs.getString("ten_kh"),
                            rs.getString("sdt"),
                            rs.getString("dia_chi")
                    });
                }
            }
        }

        return result;
    }

    public int countCustomers(
            String ten,
            String sdt,
            String diaChi) throws SQLException {

        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) FROM khach_hang WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (ten != null && !ten.trim().isEmpty()) {
            sql.append("AND ten_kh LIKE ? ");
            params.add("%" + ten.trim() + "%");
        }

        if (sdt != null && !sdt.trim().isEmpty()) {
            sql.append("AND sdt LIKE ? ");
            params.add("%" + sdt.trim() + "%");
        }

        if (diaChi != null && !diaChi.trim().isEmpty()) {
            sql.append("AND dia_chi LIKE ? ");
            params.add("%" + diaChi.trim() + "%");
        }

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    // =========================
    // TÌM HÓA ĐƠN
    // =========================
    public List<String[]> searchInvoices(
            Date tuNgay,
            Date denNgay,
            Integer maKh,
            BigDecimal tongTu,
            BigDecimal tongDen,
            int page,
            int pageSize) throws SQLException {

        List<String[]> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT hd.ma_hd, hd.ngay_lap, hd.ma_kh, kh.ten_kh, hd.tong_tien " +
                "FROM hoa_don hd LEFT JOIN khach_hang kh ON hd.ma_kh = kh.ma_kh WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (tuNgay != null) {
            sql.append("AND hd.ngay_lap >= ? ");
            params.add(tuNgay);
        }

        if (denNgay != null) {
            sql.append("AND hd.ngay_lap <= ? ");
            params.add(denNgay);
        }

        if (maKh != null && maKh > 0) {
            sql.append("AND hd.ma_kh = ? ");
            params.add(maKh);
        }

        if (tongTu != null) {
            sql.append("AND hd.tong_tien >= ? ");
            params.add(tongTu);
        }

        if (tongDen != null) {
            sql.append("AND hd.tong_tien <= ? ");
            params.add(tongDen);
        }

        sql.append("ORDER BY hd.ma_hd DESC LIMIT ? OFFSET ?");

        params.add(pageSize);
        params.add(page * pageSize);

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new String[]{
                            String.valueOf(rs.getInt("ma_hd")),
                            rs.getDate("ngay_lap").toString(),
                            rs.getString("ten_kh") != null ? rs.getString("ten_kh") : ("Mã KH: " + rs.getInt("ma_kh")),
                            rs.getBigDecimal("tong_tien").toString()
                    });
                }
            }
        }

        return result;
    }

    public int countInvoices(
            Date tuNgay,
            Date denNgay,
            Integer maKh,
            BigDecimal tongTu,
            BigDecimal tongDen) throws SQLException {

        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) FROM hoa_don WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (tuNgay != null) {
            sql.append("AND ngay_lap >= ? ");
            params.add(tuNgay);
        }

        if (denNgay != null) {
            sql.append("AND ngay_lap <= ? ");
            params.add(denNgay);
        }

        if (maKh != null && maKh > 0) {
            sql.append("AND ma_kh = ? ");
            params.add(maKh);
        }

        if (tongTu != null) {
            sql.append("AND tong_tien >= ? ");
            params.add(tongTu);
        }

        if (tongDen != null) {
            sql.append("AND tong_tien <= ? ");
            params.add(tongDen);
        }

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }
}