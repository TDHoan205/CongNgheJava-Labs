package vn.edu.eaut.lab7.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.eaut.lab7.model.SanPham;
import vn.edu.eaut.lab7.repository.SanPhamRepository;
import java.io.IOException;

@WebServlet("/san-pham")
public class SanPhamController extends HttpServlet {
    private final SanPhamRepository repo = new SanPhamRepository();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("new".equals(action)) { req.getRequestDispatcher("/views/sanpham/form.jsp").forward(req, resp); return; }
        if ("edit".equals(action)) {
            req.setAttribute("sanPham", repo.findById(Integer.parseInt(req.getParameter("id"))));
            req.getRequestDispatcher("/views/sanpham/form.jsp").forward(req, resp); return;
        }
        if ("delete".equals(action)) {
            repo.delete(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/san-pham"); return;
        }
        req.setAttribute("keyword", req.getParameter("keyword"));
        req.setAttribute("dsSanPham", repo.search(req.getParameter("keyword")));
        req.getRequestDispatcher("/views/sanpham/list.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        int id = parseInt(req.getParameter("id"), 0);
        double gia = parseDouble(req.getParameter("gia"), -1);
        int soLuong = parseInt(req.getParameter("soLuong"), -1);
        if (gia <= 0 || soLuong < 0 || req.getParameter("ma").isBlank() || req.getParameter("ten").isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/san-pham?action=new&error=Gia phai > 0 va so luong >= 0"); return;
        }
        SanPham sp = new SanPham(id, req.getParameter("ma"), req.getParameter("ten"),
                req.getParameter("moTa"), gia, soLuong);
        if (id == 0) repo.add(sp); else repo.update(sp);
        resp.sendRedirect(req.getContextPath() + "/san-pham");
    }

    private int parseInt(String s, int def) { try { return Integer.parseInt(s); } catch (Exception e) { return def; } }
    private double parseDouble(String s, double def) { try { return Double.parseDouble(s); } catch (Exception e) { return def; } }
}
