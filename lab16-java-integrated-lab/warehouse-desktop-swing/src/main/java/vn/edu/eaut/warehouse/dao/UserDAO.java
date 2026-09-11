package vn.edu.eaut.warehouse.dao;

import vn.edu.eaut.warehouse.config.DatabaseConnection;
import vn.edu.eaut.warehouse.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private static final String WAREHOUSE_ROLE = "WAREHOUSE";

    public User login(String username, String password) throws SQLException {
        String sql = "SELECT id, username, password_hash, full_name, role, enabled " +
                     "FROM users WHERE username = ? AND role = ? AND enabled = true";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, WAREHOUSE_ROLE);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    if (BCrypt.checkpw(password, storedHash)) {
                        User user = new User();
                        user.setId(rs.getLong("id"));
                        user.setUsername(rs.getString("username"));
                        user.setPasswordHash(storedHash);
                        user.setFullName(rs.getString("full_name"));
                        user.setRole(rs.getString("role"));
                        user.setEnabled(rs.getBoolean("enabled"));
                        return user;
                    }
                }
            }
        }
        return null;
    }
}
