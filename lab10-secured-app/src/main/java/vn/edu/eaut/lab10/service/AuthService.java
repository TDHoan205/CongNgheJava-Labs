package vn.edu.eaut.lab10.service;

import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.repository.UserRepository;

/**
 * Authentication service for Lab 10.
 * Handles login verification: user exists, active, password matches.
 */
public class AuthService {

    private final UserRepository userRepository;

    public AuthService() {
        this.userRepository = new UserRepository();
    }

    /**
     * Authenticates a user by email and password.
     *
     * @param email    the user's email
     * @param password the plain-text password
     * @return the authenticated User, or null if authentication fails
     */
    public User login(String email, String password) {
        if (email == null || email.isBlank()) {
            return null;
        }
        if (password == null || password.isBlank()) {
            return null;
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }

        if (!user.isActive()) {
            return null;
        }

        if (!password.equals(user.getPassword())) {
            return null;
        }

        return user;
    }

    /**
     * Changes password for a user.
     * Validates current password and confirmation.
     *
     * @param userId          the user ID
     * @param currentPassword the current password
     * @param newPassword     the new password
     * @param confirmPassword the password confirmation
     * @return error message, or null on success
     */
    public String changePassword(Integer userId, String currentPassword,
                                String newPassword, String confirmPassword) {
        if (currentPassword == null || currentPassword.isBlank()) {
            return "Mật khẩu hiện tại không được để trống.";
        }
        if (newPassword == null || newPassword.isBlank()) {
            return "Mật khẩu mới không được để trống.";
        }
        if (newPassword.length() < 6) {
            return "Mật khẩu mới phải có ít nhất 6 ký tự.";
        }
        if (!newPassword.equals(confirmPassword)) {
            return "Xác nhận mật khẩu mới không đúng.";
        }

        User user = userRepository.findById(userId);
        if (user == null) {
            return "Không tìm thấy tài khoản.";
        }

        if (!currentPassword.equals(user.getPassword())) {
            return "Mật khẩu hiện tại không đúng.";
        }

        user.setPassword(newPassword);
        userRepository.update(user);
        return null;
    }
}
