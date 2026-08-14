package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab6.model.Student;
import vn.edu.eaut.lab6.store.StudentStore;

import java.io.IOException;
import java.util.List;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String keyword = request.getParameter("keyword");

        List<Student> list;
        if (keyword != null && !keyword.trim().isEmpty()) {
            list = StudentStore.searchByName(keyword);
            request.setAttribute("keyword", keyword.trim());
        } else {
            list = StudentStore.findAll();
        }

        request.setAttribute("students", list);
        request.getRequestDispatcher("/student-list.jsp").forward(request, response);
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
            Student student = new Student(id.trim(), name != null ? name.trim() : "", 
                                          className != null ? className.trim() : "", 
                                          email != null ? email.trim() : "");
            StudentStore.add(student);
        }

        response.sendRedirect(request.getContextPath() + "/students");
    }
}
