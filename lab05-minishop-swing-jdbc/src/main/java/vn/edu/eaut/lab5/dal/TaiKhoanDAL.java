package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.TaiKhoan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaiKhoanDAL {

    public TaiKhoan dangNhap(
            String username,
            String password) throws SQLException {

        String sql =
                "SELECT username, password, ho_ten, vai_tro "
                        + "FROM tai_khoan "
                        + "WHERE username = ? "
                        + "AND password = ?";

        try (Connection conn =
                     DBHelper.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs =
                         ps.executeQuery()) {

                if (rs.next()) {

                    return new TaiKhoan(
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("ho_ten"),
                            rs.getString("vai_tro")
                    );
                }
            }
        }

        return null;
    }
}