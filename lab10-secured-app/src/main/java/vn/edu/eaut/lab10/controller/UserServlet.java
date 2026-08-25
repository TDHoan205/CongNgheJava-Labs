package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.lab10.config.Logger;
import vn.edu.eaut.lab10.model.Role;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.model.UserSession;
import vn.edu.eaut.lab10.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Servlet for User Management (Phase 5).
 * Protected by /admin/* — ADMIN only.
 *
 * URLs:
 * - GET  /admin/users         → list
 * - GET  /admin/users/new     → show create form
 * - POST /admin/users/create  → create user
 * - GET  /admin/users/edit?id → show edit form
 * - POST /admin/users/update  → update user
 * - POST /admin/users/toggle?id → toggle active/inactive
 * - GET  /admin/users/search?keyword → search by email
 */
@WebServlet(name = "UserServlet", urlPatterns = {
        "/admin/users",
        "/admin/users/new",
        "/admin/users/create",
        "/admin/users/edit",
        "/admin/users/update",
        "/admin/users/toggle"
})
public class UserServlet extends HttpServlet {

    private final UserService userService;

    public UserServlet() {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("")) {
            listUsers(req, resp);
        } else if (pathInfo.equals("/new")) {
            showCreateForm(req, resp);
        } else if (pathInfo.equals("/edit")) {
            showEditForm(req, resp);
        } else {
            listUsers(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();

        if (pathInfo.equals("/create")) {
            createUser(req, resp);
        } else if (pathInfo.equals("/update")) {
            updateUser(req, resp);
        } else if (pathInfo.equals("/toggle")) {
            toggleActive(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        }
    }

    private void listUsers(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        List<User> users = userService.searchByEmail(keyword);
        req.setAttribute("users", users);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/WEB-INF/views/admin/users/list.jsp").forward(req, resp);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("action", "create");
        req.getRequestDispatcher("/WEB-INF/views/admin/users/form.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            Optional<User> userOpt = userService.findById(id);
            if (userOpt.isEmpty()) {
                setFlashMessage(req, "error", "Không tìm thấy người dùng.");
                resp.sendRedirect(req.getContextPath() + "/admin/users");
                return;
            }
            req.setAttribute("user", userOpt.get());
            req.setAttribute("action", "update");
            req.getRequestDispatcher("/WEB-INF/views/admin/users/form.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        }
    }

    private void createUser(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String fullName = req.getParameter("fullName");
        String roleStr = req.getParameter("role");
        boolean active = "true".equals(req.getParameter("active"));

        Role role = parseRole(roleStr);

        String error = userService.createUser(email, password, fullName, role, active);
        if (error != null) {
            Logger.warn("User create FAILED: " + email + " - " + error);
            setFlashMessage(req, "error", error);
            try {
                forwardWithData(req, resp, email, password, fullName, roleStr, active, "create");
            } catch (ServletException e) {
                resp.sendRedirect(req.getContextPath() + "/admin/users/new");
            }
        } else {
            Logger.info("User created: " + email + " | Role: " + role + " | By: " + getCurrentUserEmail(req));
            setFlashMessage(req, "success", "Tạo tài khoản thành công.");
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        }
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            String email = req.getParameter("email");
            String fullName = req.getParameter("fullName");
            String roleStr = req.getParameter("role");
            boolean active = "true".equals(req.getParameter("active"));

            Role role = parseRole(roleStr);

            String error = userService.updateUser(id, email, fullName, role, active);
            if (error != null) {
                Logger.warn("User update FAILED: id=" + id + " - " + error);
                setFlashMessage(req, "error", error);
                resp.sendRedirect(req.getContextPath() + "/admin/users/edit?id=" + id);
            } else {
                Logger.info("User updated: id=" + id + " | email=" + email + " | Role: " + role + " | Active: " + active + " | By: " + getCurrentUserEmail(req));
                setFlashMessage(req, "success", "Cập nhật tài khoản thành công.");
                resp.sendRedirect(req.getContextPath() + "/admin/users");
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        }
    }

    private String getCurrentUserEmail(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            UserSession currentUser = (UserSession) session.getAttribute("currentUser");
            if (currentUser != null) {
                return currentUser.getEmail();
            }
        }
        return "system";
    }

    private void toggleActive(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            String error = userService.toggleActive(id);
            User target = userService.findById(id).orElse(null);
            if (error != null) {
                Logger.warn("User toggle FAILED: id=" + id + " - " + error);
                setFlashMessage(req, "error", error);
            } else {
                String newStatus = (target != null && target.isActive()) ? "ACTIVE" : "INACTIVE";
                Logger.info("User toggled: id=" + id + " → " + newStatus + " | By: " + getCurrentUserEmail(req));
                setFlashMessage(req, "success", "Cập nhật trạng thái thành công.");
            }
        } catch (NumberFormatException ignored) {
        }
        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }

    private Role parseRole(String roleStr) {
        if (roleStr == null || roleStr.trim().isEmpty()) {
            return null;
        }
        try {
            return Role.valueOf(roleStr.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private void forwardWithData(HttpServletRequest req, HttpServletResponse resp,
                                  String email, String password, String fullName,
                                  String role, boolean active, String action)
            throws ServletException, IOException {
        req.setAttribute("email", email);
        req.setAttribute("password", password);
        req.setAttribute("fullName", fullName);
        req.setAttribute("role", role);
        req.setAttribute("active", active);
        req.setAttribute("action", action);
        req.getRequestDispatcher("/WEB-INF/views/admin/users/form.jsp").forward(req, resp);
    }

    private void setFlashMessage(HttpServletRequest req, String type, String message) {
        req.getSession().setAttribute("flash_" + type, message);
    }
}
