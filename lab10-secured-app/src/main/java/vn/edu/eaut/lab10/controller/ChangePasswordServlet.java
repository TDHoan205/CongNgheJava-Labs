package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.lab10.config.Logger;
import vn.edu.eaut.lab10.model.UserSession;
import vn.edu.eaut.lab10.service.AuthService;

import java.io.IOException;

/**
 * Change password for logged-in users.
 * URL: /user/change-password
 */
@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/user/change-password"})
public class ChangePasswordServlet extends HttpServlet {

    private final AuthService authService;

    public ChangePasswordServlet() {
        this.authService = new AuthService();
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

        req.getRequestDispatcher("/WEB-INF/views/user/change-password.jsp").forward(req, resp);
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

        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        String error = authService.changePassword(currentUser.getUserId(), currentPassword, newPassword, confirmPassword);
        if (error != null) {
            Logger.warn("Change password FAILED: userId=" + currentUser.getUserId() + " - " + error);
            setFlash(req, "error", error);
            resp.sendRedirect(req.getContextPath() + "/user/change-password");
            return;
        }

        Logger.info("Password changed: userId=" + currentUser.getUserId() + " | email=" + currentUser.getEmail());
        setFlash(req, "success", "Đổi mật khẩu thành công.");
        resp.sendRedirect(req.getContextPath() + "/user/change-password");
    }

    private void setFlash(HttpServletRequest req, String type, String message) {
        req.getSession().setAttribute("flash_" + type, message);
    }
}
