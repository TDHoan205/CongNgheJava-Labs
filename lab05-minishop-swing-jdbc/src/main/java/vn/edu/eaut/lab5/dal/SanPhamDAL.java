package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.SanPham;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAL {

    private SanPham mapRow(ResultSet rs) throws SQLException {
        SanPham sp = new SanPham();
        sp.setMaSp(rs.getInt("ma_sp"));
        sp.setTenSp(rs.getString("ten_sp"));
        sp.setDonGia(rs.getBigDecimal("don_gia"));
        sp.setSoLuong(rs.getInt("so_luong"));
        
        int maDm = rs.getInt("ma_dm");
        if (!rs.wasNull()) {
            sp.setMaDm(maDm);
        } else {
            sp.setMaDm(null);
        }
        sp.setTenDm(rs.getString("ten_dm"));
        return sp;
    }

    public List<SanPham> findAll() throws SQLException {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT sp.ma_sp, sp.ten_sp, sp.don_gia, sp.so_luong, sp.ma_dm, dm.ten_dm " +
                     "FROM san_pham sp " +
                     "LEFT JOIN danh_muc dm ON sp.ma_dm = dm.ma_dm " +
                     "ORDER BY sp.ma_sp";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public List<SanPham> findByDanhMuc(int maDm) throws SQLException {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT sp.ma_sp, sp.ten_sp, sp.don_gia, sp.so_luong, sp.ma_dm, dm.ten_dm " +
                     "FROM san_pham sp " +
                     "LEFT JOIN danh_muc dm ON sp.ma_dm = dm.ma_dm " +
                     "WHERE sp.ma_dm = ? " +
                     "ORDER BY sp.ma_sp";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maDm);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    public boolean insert(SanPham sp) throws SQLException {
        String sql = "INSERT INTO san_pham(ten_sp, don_gia, so_luong, ma_dm) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sp.getTenSp());
            ps.setBigDecimal(2, sp.getDonGia());
            ps.setInt(3, sp.getSoLuong());
            if (sp.getMaDm() != null && sp.getMaDm() > 0) {
                ps.setInt(4, sp.getMaDm());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(SanPham sp) throws SQLException {
        String sql = "UPDATE san_pham SET ten_sp = ?, don_gia = ?, so_luong = ?, ma_dm = ? WHERE ma_sp = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sp.getTenSp());
            ps.setBigDecimal(2, sp.getDonGia());
            ps.setInt(3, sp.getSoLuong());
            if (sp.getMaDm() != null && sp.getMaDm() > 0) {
                ps.setInt(4, sp.getMaDm());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            ps.setInt(5, sp.getMaSp());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int maSp) throws SQLException {
        String sql = "DELETE FROM san_pham WHERE ma_sp = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maSp);
            return ps.executeUpdate() > 0;
        }
    }

    public List<SanPham> searchByName(String keyword) throws SQLException {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT sp.ma_sp, sp.ten_sp, sp.don_gia, sp.so_luong, sp.ma_dm, dm.ten_dm " +
                     "FROM san_pham sp " +
                     "LEFT JOIN danh_muc dm ON sp.ma_dm = dm.ma_dm " +
                     "WHERE sp.ten_sp LIKE ? " +
                     "ORDER BY sp.ma_sp";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    public int getSoLuongTon(int maSp) throws SQLException {
        String sql = "SELECT so_luong FROM san_pham WHERE ma_sp = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maSp);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("so_luong");
                }
            }
        }
        return 0;
    }

    public boolean truKho(int maSp, int soLuong) throws SQLException {
        String sql = "UPDATE san_pham SET so_luong = so_luong - ? WHERE ma_sp = ? AND so_luong >= ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soLuong);
            ps.setInt(2, maSp);
            ps.setInt(3, soLuong);
            return ps.executeUpdate() > 0;
        }
    }
}