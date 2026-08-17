package vn.edu.eaut.lab7.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.eaut.lab7.model.DiemSinhVien;
import vn.edu.eaut.lab7.repository.DiemRepository;
import java.io.IOException;

@WebServlet("/diem")
public class DiemController extends HttpServlet {
    private final DiemRepository repo = new DiemRepository();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("new".equals(action)) { req.getRequestDispatcher("/views/diem/form.jsp").forward(req, resp); return; }
        if ("edit".equals(action)) {
            req.setAttribute("diem", repo.findById(Integer.parseInt(req.getParameter("id"))));
            req.getRequestDispatcher("/views/diem/form.jsp").forward(req, resp); return;
        }
        if ("delete".equals(action)) {
            repo.delete(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/diem"); return;
        }
        req.setAttribute("dsDiem", repo.findAll());
        req.getRequestDispatcher("/views/diem/list.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = parse(req.getParameter("id"), 0);
        int svId = parse(req.getParameter("sinhVienId"), 0);
        double cc = parseDouble(req.getParameter("chuyenCan"), -1);
        double gk = parseDouble(req.getParameter("giuaKy"), -1);
        double ck = parseDouble(req.getParameter("cuoiKy"), -1);
        if (svId <= 0 || cc < 0 || cc > 10 || gk < 0 || gk > 10 || ck < 0 || ck > 10) {
            resp.sendRedirect(req.getContextPath() + "/diem?action=new&error=Diem phai tu 0 den 10"); return;
        }
        DiemSinhVien d = new DiemSinhVien(id, svId, req.getParameter("maSinhVien"),
                req.getParameter("hoTen"), cc, gk, ck);
        if (id == 0) repo.add(d); else repo.update(d);
        resp.sendRedirect(req.getContextPath() + "/diem");
    }

    private int parse(String s, int d) { try { return Integer.parseInt(s); } catch (Exception e) { return d; } }
    private double parseDouble(String s, double d) { try { return Double.parseDouble(s); } catch (Exception e) { return d; } }
}
