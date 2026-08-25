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
import vn.edu.eaut.lab10.service.AuthService;

import java.io.IOException;

@WebServlet(name = "AuthController", urlPatterns = {"/auth"})
public class AuthController extends HttpServlet {

    private final AuthService authService;

    public AuthController() {
        this.authService = new AuthService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = authService.login(email, password);

        if (user == null) {
            Logger.warn("Login failed for email: " + email);
            HttpSession session = req.getSession();
            session.setAttribute("errorMessage", "Email hoặc mật khẩu không đúng.");
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        UserSession userSession = new UserSession(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getRole()
        );

        HttpSession session = req.getSession();
        session.setAttribute("currentUser", userSession);
        session.setAttribute("userRole", user.getRole().name());

        Logger.info("Login success: " + email + " | Role: " + user.getRole().name());

        resp.sendRedirect(req.getContextPath() + "/sinh-vien");
    }
}
