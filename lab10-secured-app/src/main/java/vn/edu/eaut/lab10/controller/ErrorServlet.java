package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Central error handler for 403, 404, 500.
 * Receives status code as query param "status" from web.xml.
 * Handles both GET and POST (needed when error occurs during POST processing).
 */
@WebServlet(name = "ErrorServlet", urlPatterns = {"/error"})
public class ErrorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        handleError(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        handleError(req, resp);
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int status = 500;
        String statusParam = req.getParameter("status");
        if (statusParam != null) {
            try {
                status = Integer.parseInt(statusParam);
            } catch (NumberFormatException ignored) {
            }
        }

        String jspPath;
        switch (status) {
            case 403:
                jspPath = "/error/403.jsp";
                break;
            case 404:
                jspPath = "/error/404.jsp";
                break;
            default:
                jspPath = "/error/500.jsp";
                break;
        }

        req.getRequestDispatcher(jspPath).forward(req, resp);
    }
}
