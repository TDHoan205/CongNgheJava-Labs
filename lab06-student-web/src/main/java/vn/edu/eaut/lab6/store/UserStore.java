package vn.edu.eaut.lab6.store;

import vn.edu.eaut.lab6.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserStore {
    private static final Map<String, User> users = new HashMap<>();

    static {
        // Sample accounts: admin (ADMIN role) & user (USER role)
        users.put("admin", new User("admin", "123456", "Quản Trị Viên (Admin)", "ADMIN"));
        users.put("user", new User("user", "123456", "Sinh Viên Tra Cứu (User)", "USER"));
    }

    public static User authenticate(String username, String password) {
        if (username == null || password == null) return null;
        User user = users.get(username.trim().toLowerCase());
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public static User findByUsername(String username) {
        if (username == null) return null;
        return users.get(username.trim().toLowerCase());
    }
}
