package vn.edu.eaut.lab9.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab9.model.Sach;
import vn.edu.eaut.lab9.repository.SachRepository;

import java.io.IOException;

@WebServlet("/sach")
public class SachController extends HttpServlet {

    private final SachRepository repository = new SachRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                request.setAttribute("sach", new Sach());
                request.getRequestDispatcher("/views/sach/form.jsp").forward(request, response);
                break;
            case "edit":
                int editId = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("sach", repository.findById(editId));
                request.getRequestDispatcher("/views/sach/form.jsp").forward(request, response);
                break;
            case "delete":
                int delId = Integer.parseInt(request.getParameter("id"));
                repository.delete(delId);
                request.getSession().setAttribute("flashSuccess", "Đã xóa sách thành công!");
                response.sendRedirect(request.getContextPath() + "/sach");
                break;
            case "list":
            default:
                String keyword = request.getParameter("keyword");
                if (keyword == null || keyword.isBlank()) {
                    request.setAttribute("dsSach", repository.findAll());
                } else {
                    request.setAttribute("dsSach", repository.search(keyword));
                    request.setAttribute("keyword", keyword);
                }
                String flash = (String) request.getSession().getAttribute("flashSuccess");
                if (flash != null) {
                    request.setAttribute("successMessage", flash);
                    request.getSession().removeAttribute("flashSuccess");
                }
                request.getRequestDispatcher("/views/sach/list.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("id");
        String maSach = request.getParameter("maSach");
        String tenSach = request.getParameter("tenSach");
        String tacGia = request.getParameter("tacGia");
        double gia = Double.parseDouble(request.getParameter("gia"));
        int soLuong = Integer.parseInt(request.getParameter("soLuong"));

        Sach sach = new Sach(maSach, tenSach, tacGia, gia, soLuong);
        try {
            if (idStr != null && !idStr.isBlank()) {
                sach.setId(Integer.parseInt(idStr));
                repository.update(sach);
                request.getSession().setAttribute("flashSuccess", "Cập nhật thông tin sách thành công!");
            } else {
                repository.save(sach);
                request.getSession().setAttribute("flashSuccess", "Thêm sách mới thành công!");
            }
            response.sendRedirect(request.getContextPath() + "/sach");
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Lỗi lưu thông tin sách: " + ex.getMessage());
            request.setAttribute("sach", sach);
            request.getRequestDispatcher("/views/sach/form.jsp").forward(request, response);
        }
    }
}
