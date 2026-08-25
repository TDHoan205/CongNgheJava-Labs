package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.config.Logger;
import vn.edu.eaut.lab10.model.SinhVien;
import vn.edu.eaut.lab10.service.SinhVienService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SinhVienServlet", urlPatterns = {"/sinh-vien"})
public class SinhVienServlet extends HttpServlet {

    private final SinhVienService service;

    public SinhVienServlet() {
        this.service = new SinhVienService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("new".equals(action)) {
            req.getRequestDispatcher("/WEB-INF/views/sinhvien/form.jsp").forward(req, resp);
            return;
        }
        if ("edit".equals(action)) {
            Integer id = parseInt(req.getParameter("id"));
            if (id != null) {
                SinhVien sv = service.findById(id);
                if (sv != null) {
                    req.setAttribute("sinhVien", sv);
                    req.getRequestDispatcher("/WEB-INF/views/sinhvien/form.jsp").forward(req, resp);
                    return;
                }
            }
            setFlash(req, "error", "Không tìm thấy sinh viên.");
            resp.sendRedirect(req.getContextPath() + "/sinh-vien");
            return;
        }
        if ("delete".equals(action)) {
            Integer id = parseInt(req.getParameter("id"));
            if (id != null) {
                service.delete(id);
                Logger.info("SinhVien deleted: id=" + id);
                setFlash(req, "success", "Xóa sinh viên thành công.");
            }
            resp.sendRedirect(req.getContextPath() + "/sinh-vien");
            return;
        }

        String keyword = req.getParameter("keyword");
        List<SinhVien> list = service.search(keyword);
        req.setAttribute("dsSV", list);
        req.setAttribute("keyword", keyword);
        flashToRequest(req);
        req.getRequestDispatcher("/WEB-INF/views/sinhvien/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");
        String idStr = req.getParameter("id");
        String maSV = req.getParameter("maSinhVien");
        String hoTen = req.getParameter("hoTen");
        String email = req.getParameter("email");
        String lop = req.getParameter("lop");

        String error;
        boolean isCreate = (idStr == null || idStr.isEmpty());
        if (isCreate) {
            error = service.create(maSV, hoTen, email, lop);
        } else {
            error = service.update(parseInt(idStr), maSV, hoTen, email, lop);
        }

        if (error != null) {
            Logger.warn("SinhVien " + (isCreate ? "create" : "update") + " FAILED: " + error);
            setFlash(req, "error", error);
            if (isCreate) {
                resp.sendRedirect(req.getContextPath() + "/sinh-vien?action=new");
            } else {
                resp.sendRedirect(req.getContextPath() + "/sinh-vien?action=edit&id=" + idStr);
            }
            return;
        }

        Logger.info("SinhVien " + (isCreate ? "created" : "updated") + ": maSV=" + maSV);
        setFlash(req, "success", isCreate ? "Thêm sinh viên thành công." : "Cập nhật sinh viên thành công.");
        resp.sendRedirect(req.getContextPath() + "/sinh-vien");
    }

    private Integer parseInt(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return null; }
    }

    private void setFlash(HttpServletRequest req, String type, String msg) {
        req.getSession().setAttribute("flash_" + type, msg);
    }

    private void flashToRequest(HttpServletRequest req) {
        var session = req.getSession(false);
        if (session != null) {
            String success = (String) session.getAttribute("flash_success");
            String error = (String) session.getAttribute("flash_error");
            if (success != null) {
                req.setAttribute("flash_success", success);
                session.removeAttribute("flash_success");
            }
            if (error != null) {
                req.setAttribute("flash_error", error);
                session.removeAttribute("flash_error");
            }
        }
    }
}
