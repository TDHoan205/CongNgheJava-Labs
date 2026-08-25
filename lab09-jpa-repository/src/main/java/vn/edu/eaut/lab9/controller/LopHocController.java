package vn.edu.eaut.lab9.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab9.model.LopHoc;
import vn.edu.eaut.lab9.model.SinhVien;
import vn.edu.eaut.lab9.repository.LopHocRepository;
import vn.edu.eaut.lab9.repository.SinhVienRepository;

import java.io.IOException;
import java.util.List;

@WebServlet("/lop-hoc")
public class LopHocController extends HttpServlet {

    private final LopHocRepository repository = new LopHocRepository();
    private final SinhVienRepository sinhVienRepository = new SinhVienRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                request.setAttribute("lopHoc", new LopHoc());
                request.getRequestDispatcher("/views/lophoc/form.jsp").forward(request, response);
                break;
            case "edit":
                int editId = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("lopHoc", repository.findById(editId));
                request.getRequestDispatcher("/views/lophoc/form.jsp").forward(request, response);
                break;
            case "delete":
                int delId = Integer.parseInt(request.getParameter("id"));
                repository.delete(delId);
                request.getSession().setAttribute("flashSuccess", "Xóa lớp học thành công!");
                response.sendRedirect(request.getContextPath() + "/lop-hoc");
                break;
            case "detail":
                int detailId = Integer.parseInt(request.getParameter("id"));
                LopHoc lop = repository.findById(detailId);
                List<SinhVien> dsSv = sinhVienRepository.findByLopId(detailId);
                request.setAttribute("lopHoc", lop);
                request.setAttribute("dsSinhVien", dsSv);
                request.getRequestDispatcher("/views/lophoc/detail.jsp").forward(request, response);
                break;
            case "list":
            default:
                String keyword = request.getParameter("keyword");
                if (keyword == null || keyword.isBlank()) {
                    request.setAttribute("dsLopHoc", repository.findAll());
                } else {
                    request.setAttribute("dsLopHoc", repository.search(keyword));
                    request.setAttribute("keyword", keyword);
                }
                String flash = (String) request.getSession().getAttribute("flashSuccess");
                if (flash != null) {
                    request.setAttribute("successMessage", flash);
                    request.getSession().removeAttribute("flashSuccess");
                }
                request.getRequestDispatcher("/views/lophoc/list.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("id");
        String maLop = request.getParameter("maLop");
        String tenLop = request.getParameter("tenLop");
        String khoaHoc = request.getParameter("khoaHoc");

        LopHoc lop = new LopHoc(maLop, tenLop, khoaHoc);
        try {
            if (idStr != null && !idStr.isBlank()) {
                lop.setId(Integer.parseInt(idStr));
                repository.update(lop);
                request.getSession().setAttribute("flashSuccess", "Cập nhật lớp học thành công!");
            } else {
                repository.save(lop);
                request.getSession().setAttribute("flashSuccess", "Thêm lớp học mới thành công!");
            }
            response.sendRedirect(request.getContextPath() + "/lop-hoc");
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Lỗi lưu lớp học: " + ex.getMessage());
            request.setAttribute("lopHoc", lop);
            request.getRequestDispatcher("/views/lophoc/form.jsp").forward(request, response);
        }
    }
}
