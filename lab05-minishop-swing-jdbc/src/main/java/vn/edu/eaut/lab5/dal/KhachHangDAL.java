package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.KhachHang;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAL {

    public List<KhachHang> findAll() throws SQLException {

        List<KhachHang> list = new ArrayList<>();

        String sql = 
                "SELECT ma_kh, ten_kh, sdt, dia_chi" +
                " FROM khach_hang" +
                " ORDER BY ma_kh";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                list.add(mapResultSet(rs));
            }
        }

        return list;
    }

    public List<KhachHang> search(String keyword)
            throws SQLException {

        List<KhachHang> list = new ArrayList<>();

        String sql = 
                "SELECT ma_kh, ten_kh, sdt, dia_chi" +
                " FROM khach_hang" +
                " WHERE ten_kh LIKE ?" +
                "    OR sdt LIKE ?" +
                "    OR dia_chi LIKE ?" +
                " ORDER BY ma_kh";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            ps.setString(1, value);
            ps.setString(2, value);
            ps.setString(3, value);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
            }
        }

        return list;
    }

    public boolean insert(KhachHang kh)
            throws SQLException {

        String sql = 
                "INSERT INTO khach_hang" +
                " (ten_kh, sdt, dia_chi)" +
                " VALUES (?, ?, ?)" +
                "";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kh.getTenKh());
            ps.setString(2, kh.getSdt());
            ps.setString(3, kh.getDiaChi());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(KhachHang kh)
            throws SQLException {

        String sql = 
                "UPDATE khach_hang" +
                " SET ten_kh = ?, sdt = ?, dia_chi = ?" +
                " WHERE ma_kh = ?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kh.getTenKh());
            ps.setString(2, kh.getSdt());
            ps.setString(3, kh.getDiaChi());
            ps.setInt(4, kh.getMaKh());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int maKh)
            throws SQLException {

        String sql =
                "DELETE FROM khach_hang WHERE ma_kh = ?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maKh);

            return ps.executeUpdate() > 0;
        }
    }

    private KhachHang mapResultSet(ResultSet rs)
            throws SQLException {

        return new KhachHang(
                rs.getInt("ma_kh"),
                rs.getString("ten_kh"),
                rs.getString("sdt"),
                rs.getString("dia_chi")
        );
    }
}