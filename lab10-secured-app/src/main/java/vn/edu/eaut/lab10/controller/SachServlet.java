package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.config.Logger;
import vn.edu.eaut.lab10.model.Sach;
import vn.edu.eaut.lab10.service.SachService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SachServlet", urlPatterns = {"/sach"})
public class SachServlet extends HttpServlet {

    private final SachService service;

    public SachServlet() {
        this.service = new SachService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("new".equals(action)) {
            req.getRequestDispatcher("/WEB-INF/views/sach/form.jsp").forward(req, resp);
            return;
        }
        if ("edit".equals(action)) {
            Integer id = parseInt(req.getParameter("id"));
            if (id != null) {
                Sach sach = service.findById(id);
                if (sach != null) {
                    req.setAttribute("sach", sach);
                    req.getRequestDispatcher("/WEB-INF/views/sach/form.jsp").forward(req, resp);
                    return;
                }
            }
            setFlash(req, "error", "Không tìm thấy sách.");
            resp.sendRedirect(req.getContextPath() + "/sach");
            return;
        }
        if ("delete".equals(action)) {
            Integer id = parseInt(req.getParameter("id"));
            if (id != null) {
                service.delete(id);
                Logger.info("Sach deleted: id=" + id);
                setFlash(req, "success", "Xóa sách thành công.");
            }
            resp.sendRedirect(req.getContextPath() + "/sach");
            return;
        }

        String keyword = req.getParameter("keyword");
        List<Sach> list = service.search(keyword);
        req.setAttribute("dsSach", list);
        req.setAttribute("keyword", keyword);
        flashToRequest(req);
        req.getRequestDispatcher("/WEB-INF/views/sach/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");
        String idStr = req.getParameter("id");
        String maSach = req.getParameter("maSach");
        String tenSach = req.getParameter("tenSach");
        String tacGia = req.getParameter("tacGia");
        String nhaXB = req.getParameter("nhaXuatBan");
        Integer namXB = parseInt(req.getParameter("namXuatBan"));

        boolean isCreate = (idStr == null || idStr.isEmpty());
        String error;
        if (isCreate) {
            error = service.create(maSach, tenSach, tacGia, nhaXB, namXB);
        } else {
            error = service.update(parseInt(idStr), maSach, tenSach, tacGia, nhaXB, namXB);
        }

        if (error != null) {
            Logger.warn("Sach " + (isCreate ? "create" : "update") + " FAILED: " + error);
            setFlash(req, "error", error);
            if (isCreate) {
                resp.sendRedirect(req.getContextPath() + "/sach?action=new");
            } else {
                resp.sendRedirect(req.getContextPath() + "/sach?action=edit&id=" + idStr);
            }
            return;
        }

        Logger.info("Sach " + (isCreate ? "created" : "updated") + ": maSach=" + maSach);
        setFlash(req, "success", isCreate ? "Thêm sách thành công." : "Cập nhật sách thành công.");
        resp.sendRedirect(req.getContextPath() + "/sach");
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
