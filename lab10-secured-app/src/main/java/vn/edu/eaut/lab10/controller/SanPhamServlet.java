package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.config.Logger;
import vn.edu.eaut.lab10.model.SanPham;
import vn.edu.eaut.lab10.service.SanPhamService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "SanPhamServlet", urlPatterns = {"/san-pham"})
public class SanPhamServlet extends HttpServlet {

    private final SanPhamService service;

    public SanPhamServlet() {
        this.service = new SanPhamService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("new".equals(action)) {
            req.getRequestDispatcher("/WEB-INF/views/sanpham/form.jsp").forward(req, resp);
            return;
        }
        if ("edit".equals(action)) {
            Integer id = parseInt(req.getParameter("id"));
            if (id != null) {
                SanPham sp = service.findById(id);
                if (sp != null) {
                    req.setAttribute("sanPham", sp);
                    req.getRequestDispatcher("/WEB-INF/views/sanpham/form.jsp").forward(req, resp);
                    return;
                }
            }
            setFlash(req, "error", "Không tìm thấy sản phẩm.");
            resp.sendRedirect(req.getContextPath() + "/san-pham");
            return;
        }
        if ("delete".equals(action)) {
            Integer id = parseInt(req.getParameter("id"));
            if (id != null) {
                service.delete(id);
                Logger.info("SanPham deleted: id=" + id);
                setFlash(req, "success", "Xóa sản phẩm thành công.");
            }
            resp.sendRedirect(req.getContextPath() + "/san-pham");
            return;
        }

        String keyword = req.getParameter("keyword");
        List<SanPham> list = service.search(keyword);
        req.setAttribute("dsSP", list);
        req.setAttribute("keyword", keyword);
        flashToRequest(req);
        req.getRequestDispatcher("/WEB-INF/views/sanpham/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");
        String idStr = req.getParameter("id");
        String ma = req.getParameter("ma");
        String ten = req.getParameter("ten");
        String moTa = req.getParameter("moTa");
        BigDecimal gia = parseDecimal(req.getParameter("gia"));
        Integer soLuong = parseInt(req.getParameter("soLuong"), 0);

        boolean isCreate = (idStr == null || idStr.isEmpty());
        String error;
        if (isCreate) {
            error = service.create(ma, ten, moTa, gia, soLuong);
        } else {
            error = service.update(parseInt(idStr), ma, ten, moTa, gia, soLuong);
        }

        if (error != null) {
            Logger.warn("SanPham " + (isCreate ? "create" : "update") + " FAILED: " + error);
            setFlash(req, "error", error);
            if (isCreate) {
                resp.sendRedirect(req.getContextPath() + "/san-pham?action=new");
            } else {
                resp.sendRedirect(req.getContextPath() + "/san-pham?action=edit&id=" + idStr);
            }
            return;
        }

        Logger.info("SanPham " + (isCreate ? "created" : "updated") + ": ma=" + ma);
        setFlash(req, "success", isCreate ? "Thêm sản phẩm thành công." : "Cập nhật sản phẩm thành công.");
        resp.sendRedirect(req.getContextPath() + "/san-pham");
    }

    private Integer parseInt(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return null; }
    }

    private Integer parseInt(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }

    private BigDecimal parseDecimal(String s) {
        try { return new BigDecimal(s); } catch (Exception e) { return null; }
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
