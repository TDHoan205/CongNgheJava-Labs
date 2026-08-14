package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab6.store.StudentStore;

import java.io.IOException;
import java.util.Map;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int totalStudents = StudentStore.getTotalCount();
        Map<String, Long> countByClass = StudentStore.getCountByClass();

        request.setAttribute("totalStudents", totalStudents);
        request.setAttribute("countByClass", countByClass);

        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }
}
