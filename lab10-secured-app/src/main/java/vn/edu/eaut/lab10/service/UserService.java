package vn.edu.eaut.lab10.service;

import vn.edu.eaut.lab10.model.Role;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for User management.
 * Handles business logic for CRUD operations.
 */
public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(userRepository.findById(id));
    }

    public List<User> searchByEmail(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return userRepository.findAll();
        }
        return userRepository.searchByEmail(keyword.trim());
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByEmailExcludingId(String email, Integer excludeId) {
        return userRepository.existsByEmailExcludingId(email, excludeId);
    }

    public String createUser(String email, String password, String fullName, Role role, boolean active) {
        List<String> errors = validateUser(email, password, fullName, role, true);
        if (!errors.isEmpty()) {
            return String.join("; ", errors);
        }

        if (userRepository.existsByEmail(email)) {
            return "Email đã tồn tại trong hệ thống.";
        }

        User user = new User(email.trim(), password, fullName.trim(), role, active);
        userRepository.save(user);
        return null;
    }

    public String updateUser(Integer id, String email, String fullName, Role role, boolean active) {
        User existing = userRepository.findById(id);
        if (existing == null) {
            return "Không tìm thấy người dùng.";
        }

        List<String> errors = validateUser(email, null, fullName, role, false);
        if (!errors.isEmpty()) {
            return String.join("; ", errors);
        }

        if (userRepository.existsByEmailExcludingId(email.trim(), id)) {
            return "Email đã tồn tại trong hệ thống.";
        }

        existing.setEmail(email.trim());
        existing.setFullName(fullName.trim());
        existing.setRole(role);
        existing.setActive(active);
        userRepository.update(existing);
        return null;
    }

    public String toggleActive(Integer id) {
        User user = userRepository.findById(id);
        if (user == null) {
            return "Không tìm thấy người dùng.";
        }
        user.setActive(!user.isActive());
        userRepository.update(user);
        return null;
    }

    public String updateProfile(Integer id, String fullName) {
        User user = userRepository.findById(id);
        if (user == null) {
            return "Không tìm thấy tài khoản.";
        }

        if (fullName == null || fullName.trim().isEmpty()) {
            return "Họ tên không được để trống.";
        }
        if (fullName.trim().length() < 2) {
            return "Họ tên phải có ít nhất 2 ký tự.";
        }
        if (fullName.trim().length() > 100) {
            return "Họ tên không được quá 100 ký tự.";
        }

        user.setFullName(fullName.trim());
        userRepository.update(user);
        return null;
    }

    private List<String> validateUser(String email, String password, String fullName, Role role, boolean requirePassword) {
        List<String> errors = new ArrayList<>();

        if (email == null || email.trim().isEmpty()) {
            errors.add("Email không được để trống.");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errors.add("Email không hợp lệ.");
        }

        if (requirePassword) {
            if (password == null || password.isEmpty()) {
                errors.add("Mật khẩu không được để trống.");
            } else if (password.length() < 6) {
                errors.add("Mật khẩu phải có ít nhất 6 ký tự.");
            }
        }

        if (fullName == null || fullName.trim().isEmpty()) {
            errors.add("Họ tên không được để trống.");
        } else if (fullName.trim().length() < 2) {
            errors.add("Họ tên phải có ít nhất 2 ký tự.");
        } else if (fullName.trim().length() > 100) {
            errors.add("Họ tên không được quá 100 ký tự.");
        }

        if (role == null) {
            errors.add("Vai trò không được để trống.");
        }

        return errors;
    }
}
