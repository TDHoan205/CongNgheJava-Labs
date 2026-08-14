package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html><head><title>Hello Servlet</title>");
        response.getWriter().println("<style>");
        response.getWriter().println("body { font-family: 'Segoe UI', Arial, sans-serif; background-color: #f4f6f9; text-align: center; padding-top: 50px; }");
        response.getWriter().println(".card { background: white; max-width: 500px; margin: 0 auto; padding: 30px; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }");
        response.getWriter().println("h1 { color: #2c3e50; }");
        response.getWriter().println(".btn { display: inline-block; margin-top: 20px; padding: 10px 20px; background: #3498db; color: white; text-decoration: none; border-radius: 5px; }");
        response.getWriter().println("</style></head><body>");
        response.getWriter().println("<div class='card'>");
        response.getWriter().println("<h1>Hello, Servlet - Lab 6 Cong nghe Java</h1>");
        response.getWriter().println("<p>Servlet da khoi tao va chay thanh cong tren Web Container!</p>");
        response.getWriter().println("<a class='btn' href='" + request.getContextPath() + "/login.jsp'>Den trang dang nhap</a>");
        response.getWriter().println("</div>");
        response.getWriter().println("</body></html>");
    }
}
