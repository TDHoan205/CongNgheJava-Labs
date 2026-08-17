package vn.edu.eaut.lab7.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.eaut.lab7.model.Sach;
import vn.edu.eaut.lab7.repository.SachRepository;
import java.io.IOException;

@WebServlet("/sach")
public class SachController extends HttpServlet {
    private final SachRepository repo = new SachRepository();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if ("new".equals(action)) { req.getRequestDispatcher("/views/sach/form.jsp").forward(req, resp); return; }
        if ("edit".equals(action)) {
            req.setAttribute("sach", repo.findById(Integer.parseInt(req.getParameter("id"))));
            req.getRequestDispatcher("/views/sach/form.jsp").forward(req, resp); return;
        }
        if ("delete".equals(action)) {
            repo.delete(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/sach"); return;
        }
        req.setAttribute("keyword", req.getParameter("keyword"));
        req.setAttribute("dsSach", repo.search(req.getParameter("keyword")));
        req.getRequestDispatcher("/views/sach/list.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        int id = parseInt(req.getParameter("id"), 0);
        int nam = parseInt(req.getParameter("namXuatBan"), 0);
        if (req.getParameter("maSach").isBlank() || req.getParameter("tenSach").isBlank() || nam <= 0) {
            resp.sendRedirect(req.getContextPath() + "/sach?action=new&error=Du lieu khong hop le"); return;
        }
        Sach s = new Sach(id, req.getParameter("maSach"), req.getParameter("tenSach"),
                req.getParameter("tacGia"), req.getParameter("nhaXuatBan"), nam);
        if (id == 0) repo.add(s); else repo.update(s);
        resp.sendRedirect(req.getContextPath() + "/sach");
    }

    private int parseInt(String s, int def) { try { return Integer.parseInt(s); } catch (Exception e) { return def; } }
}
