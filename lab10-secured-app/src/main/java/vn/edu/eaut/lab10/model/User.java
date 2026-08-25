package vn.edu.eaut.lab10.model;

import jakarta.persistence.*;

/**
 * Account model for Lab 10. Fields follow Task 2.2 and docs/01_YEU_CAU.md.
 */
@Entity
@Table(name = "users")
public class User {

    private Integer id;
    private String email;
    private String password;
    private String fullName;
    private Role role;
    private boolean active = true;

    public User() {
    }

    public User(String email, String password, String fullName, Role role, boolean active) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.active = active;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "email", length = 100, nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", length = 255, nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "full_name", length = 100, nullable = false)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Column(name = "active", nullable = false)
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
