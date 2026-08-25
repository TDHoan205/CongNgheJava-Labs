package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.lab10.config.Logger;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.model.UserSession;
import vn.edu.eaut.lab10.service.UserService;

import java.io.IOException;
import java.util.Optional;

/**
 * Profile management for logged-in users.
 * User can only view/edit their own profile.
 * URL: /user/profile
 */
@WebServlet(name = "ProfileServlet", urlPatterns = {"/user/profile"})
public class ProfileServlet extends HttpServlet {

    private final UserService userService;

    public ProfileServlet() {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        UserSession currentUser = (session != null)
                ? (UserSession) session.getAttribute("currentUser")
                : null;

        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        Optional<User> userOpt = userService.findById(currentUser.getUserId());
        if (userOpt.isEmpty()) {
            setFlash(req, "error", "Không tìm thấy tài khoản.");
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        req.setAttribute("user", userOpt.get());
        req.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        UserSession currentUser = (session != null)
                ? (UserSession) session.getAttribute("currentUser")
                : null;

        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        String fullName = req.getParameter("fullName");

        String error = userService.updateProfile(currentUser.getUserId(), fullName);
        if (error != null) {
            Logger.warn("Profile update FAILED: userId=" + currentUser.getUserId() + " - " + error);
            setFlash(req, "error", error);
            resp.sendRedirect(req.getContextPath() + "/user/profile");
            return;
        }

        currentUser.setFullName(fullName.trim());
        session.setAttribute("currentUser", currentUser);

        Logger.info("Profile updated: userId=" + currentUser.getUserId() + " | email=" + currentUser.getEmail());
        setFlash(req, "success", "Cập nhật thông tin thành công.");
        resp.sendRedirect(req.getContextPath() + "/user/profile");
    }

    private void setFlash(HttpServletRequest req, String type, String message) {
        req.getSession().setAttribute("flash_" + type, message);
    }
}
