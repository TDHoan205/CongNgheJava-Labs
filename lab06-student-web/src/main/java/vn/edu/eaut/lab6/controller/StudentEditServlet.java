package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab6.model.Student;
import vn.edu.eaut.lab6.store.StudentStore;

import java.io.IOException;

@WebServlet("/students/edit")
public class StudentEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null || id.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/students");
            return;
        }

        Student student = StudentStore.findById(id.trim());
        if (student == null) {
            response.sendRedirect(request.getContextPath() + "/students");
            return;
        }

        request.setAttribute("student", student);
        request.setAttribute("isEdit", true);
        request.getRequestDispatcher("/student-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String className = request.getParameter("className");
        String email = request.getParameter("email");

        if (id != null && !id.trim().isEmpty()) {
            Student existing = StudentStore.findById(id.trim());
            if (existing != null) {
                existing.setName(name != null ? name.trim() : "");
                existing.setClassName(className != null ? className.trim() : "");
                existing.setEmail(email != null ? email.trim() : "");
                StudentStore.update(existing);
            }
        }

        response.sendRedirect(request.getContextPath() + "/students");
    }
}
