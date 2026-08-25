package vn.edu.eaut.lab10.model;

/**
 * Session identity object stored in HttpSession after login.
 * Contains only the minimal info needed for authorization checks.
 * Password is NEVER stored in session.
 */
public class UserSession {

    private final Integer userId;
    private final String email;
    private String fullName;
    private final Role role;

    public UserSession(Integer userId, String email, String fullName, Role role) {
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    public String getRoleName() {
        return role != null ? role.name() : null;
    }

    public boolean isAdmin() {
        return role != null && role == Role.ADMIN;
    }

    public boolean isStaff() {
        return role != null && role == Role.STAFF;
    }
}
