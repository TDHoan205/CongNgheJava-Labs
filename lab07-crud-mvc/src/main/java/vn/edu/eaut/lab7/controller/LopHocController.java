package vn.edu.eaut.lab7.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.eaut.lab7.model.LopHoc;
import vn.edu.eaut.lab7.repository.LopHocRepository;
import java.io.IOException;

@WebServlet("/lop-hoc")
public class LopHocController extends HttpServlet {
    private final LopHocRepository repo = new LopHocRepository();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("new".equals(action)) { req.getRequestDispatcher("/views/lophoc/form.jsp").forward(req, resp); return; }
        if ("edit".equals(action)) {
            req.setAttribute("lop", repo.findById(Integer.parseInt(req.getParameter("id"))));
            req.getRequestDispatcher("/views/lophoc/form.jsp").forward(req, resp); return;
        }
        if ("delete".equals(action)) {
            repo.delete(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/lop-hoc"); return;
        }
        req.setAttribute("keyword", req.getParameter("keyword"));
        req.setAttribute("dsLop", repo.search(req.getParameter("keyword")));
        req.getRequestDispatcher("/views/lophoc/list.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        int id = parse(req.getParameter("id"), 0);
        int sl = parse(req.getParameter("soLuongSinhVien"), -1);
        if (sl < 0 || req.getParameter("maLop").isBlank() || req.getParameter("tenLop").isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/lop-hoc?action=new&error=Du lieu khong hop le"); return;
        }
        LopHoc l = new LopHoc(id, req.getParameter("maLop"), req.getParameter("tenLop"),
                req.getParameter("coVanHocTap"), sl);
        if (id == 0) repo.add(l); else repo.update(l);
        resp.sendRedirect(req.getContextPath() + "/lop-hoc");
    }

    private int parse(String s, int d) { try { return Integer.parseInt(s); } catch (Exception e) { return d; } }
}
