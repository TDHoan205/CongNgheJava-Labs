package vn.edu.eaut.lab7.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.eaut.lab7.model.SinhVien;
import vn.edu.eaut.lab7.repository.SinhVienRepository;
import java.io.IOException;
import java.util.List;

@WebServlet("/sinh-vien")
public class SinhVienController extends HttpServlet {
    private final SinhVienRepository repo = new SinhVienRepository();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if ("new".equals(action)) { req.getRequestDispatcher("/views/sinhvien/form.jsp").forward(req, resp); return; }
        if ("edit".equals(action)) {
            req.setAttribute("sv", repo.findById(Integer.parseInt(req.getParameter("id"))));
            req.getRequestDispatcher("/views/sinhvien/form.jsp").forward(req, resp); return;
        }
        if ("detail".equals(action)) {
            req.setAttribute("sv", repo.findById(Integer.parseInt(req.getParameter("id"))));
            req.getRequestDispatcher("/views/sinhvien/detail.jsp").forward(req, resp); return;
        }
        if ("delete".equals(action)) {
            repo.delete(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/sinh-vien"); return;
        }
        String keyword = req.getParameter("keyword");
        req.setAttribute("keyword", keyword);

        // Phân trang (Bài 11) - 5 dòng/trang
        List<SinhVien> all = repo.search(keyword);
        int pageSize = 5;
        int totalPages = (int) Math.ceil((double) all.size() / pageSize);
        if (totalPages == 0) totalPages = 1;
        int page = 1;
        try { page = Integer.parseInt(req.getParameter("page")); } catch (Exception ignored) {}
        if (page < 1) page = 1;
        if (page > totalPages) page = totalPages;
        int from = (page - 1) * pageSize;
        int to = Math.min(from + pageSize, all.size());

        req.setAttribute("dsSinhVien", all.subList(from, to));
        req.setAttribute("page", page);
        req.setAttribute("totalPages", totalPages);
        req.getRequestDispatcher("/views/sinhvien/list.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        String ma = req.getParameter("maSinhVien"), ten = req.getParameter("hoTen");
        String email = req.getParameter("email"), lop = req.getParameter("lop");
        if (ma == null || ma.isBlank() || ten == null || ten.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/sinh-vien?action=new&error=Vui lòng nhập mã và họ tên");
            return;
        }
        SinhVien sv = new SinhVien(id == null || id.isBlank() ? 0 : Integer.parseInt(id), ma, ten, email, lop);
        if (sv.getId() == 0) repo.add(sv); else repo.update(sv);
        resp.sendRedirect(req.getContextPath() + "/sinh-vien");
    }
}
