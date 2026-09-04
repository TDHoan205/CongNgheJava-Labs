package vn.edu.eaut.lab14.entity;

import jakarta.persistence.*;

/**
 * AppUser - Tài khoản lưu trong CSDL (Bài 10).
 * Thay thế user trong bộ nhớ bằng user thật trong CSDL.
 * Mật khẩu được mã hóa bằng BCrypt.
 */
@Entity
@Table(name = "app_users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 50)
    private String role; // ADMIN hoặc USER

    @Column(nullable = false)
    private boolean enabled = true;

    public AppUser() {
    }

    public AppUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
