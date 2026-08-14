package vn.edu.eaut.lab5.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {

    // Địa chỉ MySQL Server
    private static final String URL =
            "jdbc:mysql://localhost:3306/minishop_db"
            + "?useUnicode=true"
            + "&characterEncoding=UTF-8"
            + "&serverTimezone=Asia/Ho_Chi_Minh";

    // Tài khoản MySQL
    private static final String USER = "root";

    // Mật khẩu MySQL
    // Nếu root không có mật khẩu thì để ""
    private static final String PASSWORD = "123456";

    /**
     * Tạo kết nối đến MySQL
     */
    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }

    /**
     * Kiểm tra kết nối database
     */
    public static boolean testConnection() {

        try (Connection connection = getConnection()) {

            System.out.println("=================================");
            System.out.println("KET NOI MYSQL THANH CONG!");
            System.out.println("Database: minishop_db");
            System.out.println("=================================");

            return true;

        } catch (SQLException e) {

            System.out.println("=================================");
            System.out.println("KET NOI MYSQL THAT BAI!");
            System.out.println("=================================");

            System.out.println("Ma loi: " + e.getErrorCode());
            System.out.println("Thong bao: " + e.getMessage());

            return false;
        }
    }
}