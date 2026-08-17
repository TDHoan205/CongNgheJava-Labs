package vn.edu.eaut.lab7.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if ("admin".equals(username) && "123456".equals(password)) {
            req.getSession(true).setAttribute("username", username);
            resp.sendRedirect(req.getContextPath() + "/admin");
        } else {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Sai tai khoan hoac mat khau");
        }
    }
}
