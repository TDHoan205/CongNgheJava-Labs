package vn.edu.eaut.lab9.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab9.model.SanPham;
import vn.edu.eaut.lab9.repository.SanPhamRepository;

import java.io.IOException;

@WebServlet("/san-pham")
public class SanPhamController extends HttpServlet {

    private final SanPhamRepository repository = new SanPhamRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                request.setAttribute("sanPham", new SanPham());
                request.getRequestDispatcher("/views/sanpham/form.jsp").forward(request, response);
                break;
            case "edit":
                int editId = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("sanPham", repository.findById(editId));
                request.getRequestDispatcher("/views/sanpham/form.jsp").forward(request, response);
                break;
            case "delete":
                int delId = Integer.parseInt(request.getParameter("id"));
                repository.delete(delId);
                request.getSession().setAttribute("flashSuccess", "Đã xóa sản phẩm thành công!");
                response.sendRedirect(request.getContextPath() + "/san-pham");
                break;
            case "list":
            default:
                String keyword = request.getParameter("keyword");
                if (keyword == null || keyword.isBlank()) {
                    request.setAttribute("dsSanPham", repository.findAll());
                } else {
                    request.setAttribute("dsSanPham", repository.search(keyword));
                    request.setAttribute("keyword", keyword);
                }
                String flash = (String) request.getSession().getAttribute("flashSuccess");
                if (flash != null) {
                    request.setAttribute("successMessage", flash);
                    request.getSession().removeAttribute("flashSuccess");
                }
                request.getRequestDispatcher("/views/sanpham/list.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("id");
        String maSp = request.getParameter("maSp");
        String tenSp = request.getParameter("tenSp");
        String loaiSp = request.getParameter("loaiSp");
        double gia = Double.parseDouble(request.getParameter("gia"));
        int soLuong = Integer.parseInt(request.getParameter("soLuong"));

        SanPham sp = new SanPham(maSp, tenSp, loaiSp, gia, soLuong);
        try {
            if (idStr != null && !idStr.isBlank()) {
                sp.setId(Integer.parseInt(idStr));
                repository.update(sp);
                request.getSession().setAttribute("flashSuccess", "Cập nhật sản phẩm thành công!");
            } else {
                repository.save(sp);
                request.getSession().setAttribute("flashSuccess", "Thêm sản phẩm mới thành công!");
            }
            response.sendRedirect(request.getContextPath() + "/san-pham");
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Lỗi lưu sản phẩm: " + ex.getMessage());
            request.setAttribute("sanPham", sp);
            request.getRequestDispatcher("/views/sanpham/form.jsp").forward(request, response);
        }
    }
}
