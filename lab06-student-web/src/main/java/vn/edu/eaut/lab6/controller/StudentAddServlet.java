package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab6.model.Student;
import vn.edu.eaut.lab6.store.StudentStore;

import java.io.IOException;

@WebServlet("/students/add")
public class StudentAddServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

        if (id == null || id.trim().isEmpty() || name == null || name.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập đầy đủ Mã sinh viên và Họ tên!");
            request.getRequestDispatcher("/student-form.jsp").forward(request, response);
            return;
        }

        if (StudentStore.findById(id.trim()) != null) {
            request.setAttribute("error", "Mã sinh viên " + id + " đã tồn tại!");
            request.getRequestDispatcher("/student-form.jsp").forward(request, response);
            return;
        }

        Student student = new Student(id.trim(), name.trim(), className != null ? className.trim() : "", email != null ? email.trim() : "");
        StudentStore.add(student);

        response.sendRedirect(request.getContextPath() + "/students");
    }
}
