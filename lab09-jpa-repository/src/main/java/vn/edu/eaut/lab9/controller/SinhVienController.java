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
import vn.edu.eaut.lab9.service.SinhVienService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/sinh-vien")
public class SinhVienController extends HttpServlet {

    private final SinhVienRepository repository = new SinhVienRepository();
    private final LopHocRepository lopHocRepository = new LopHocRepository();
    private final SinhVienService service = new SinhVienService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteSinhVien(request, response);
                    break;
                case "list":
                default:
                    listSinhVien(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Đã xảy ra lỗi: " + ex.getMessage());
            listSinhVien(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("id");
        String maSinhVien = request.getParameter("maSinhVien");
        String hoTen = request.getParameter("hoTen");
        String email = request.getParameter("email");
        String ngaySinhStr = request.getParameter("ngaySinh");
        String lopIdStr = request.getParameter("lopId");

        SinhVien sv = new SinhVien();
        if (idStr != null && !idStr.isBlank()) {
            sv.setId(Integer.parseInt(idStr));
        }
        sv.setMaSinhVien(maSinhVien != null ? maSinhVien.trim() : "");
        sv.setHoTen(hoTen != null ? hoTen.trim() : "");
        sv.setEmail(email != null ? email.trim() : "");

        if (ngaySinhStr != null && !ngaySinhStr.isBlank()) {
            try {
                sv.setNgaySinh(LocalDate.parse(ngaySinhStr));
            } catch (Exception e) {
                // Ignore parse error, handle in validation
            }
        }

        Integer lopId = null;
        if (lopIdStr != null && !lopIdStr.isBlank()) {
            try {
                lopId = Integer.parseInt(lopIdStr);
            } catch (NumberFormatException ignored) {}
        }

        try {
            if (sv.getId() == null) {
                // Bài 11: Transaction thêm sinh viên & khởi tạo bảng điểm mặc định
                service.saveWithDefaultGradesTransaction(sv, lopId);
                request.getSession().setAttribute("flashSuccess", "Thêm mới sinh viên '" + sv.getHoTen() + "' và tự động tạo bảng điểm thành công!");
            } else {
                service.updateSinhVien(sv, lopId);
                request.getSession().setAttribute("flashSuccess", "Cập nhật sinh viên '" + sv.getHoTen() + "' thành công!");
            }
            response.sendRedirect(request.getContextPath() + "/sinh-vien");
        } catch (Exception ex) {
            request.setAttribute("errorMessage", ex.getMessage());
            request.setAttribute("sinhVien", sv);
            request.setAttribute("dsLopHoc", lopHocRepository.findAll());
            request.getRequestDispatcher("/views/sinhvien/form.jsp").forward(request, response);
        }
    }

    private void listSinhVien(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }

        int page = 1;
        int pageSize = 5; // Phân trang 5 dòng/trang (Bài 9)
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.isBlank()) {
            try {
                page = Math.max(1, Integer.parseInt(pageStr));
            } catch (NumberFormatException ignored) {}
        }

        List<SinhVien> dsSinhVien;
        long totalElements;

        if (keyword.isBlank()) {
            dsSinhVien = repository.findPaginated(page, pageSize);
            totalElements = repository.count();
        } else {
            dsSinhVien = repository.searchPaginated(keyword, page, pageSize);
            totalElements = repository.countSearch(keyword);
        }

        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        if (totalPages < 1) totalPages = 1;

        request.setAttribute("dsSinhVien", dsSinhVien);
        request.setAttribute("keyword", keyword);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalElements", totalElements);
        request.setAttribute("dsLopHoc", lopHocRepository.findAll());

        // Check for session flash message
        String flashSuccess = (String) request.getSession().getAttribute("flashSuccess");
        if (flashSuccess != null) {
            request.setAttribute("successMessage", flashSuccess);
            request.getSession().removeAttribute("flashSuccess");
        }

        request.getRequestDispatcher("/views/sinhvien/list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("sinhVien", new SinhVien());
        request.setAttribute("dsLopHoc", lopHocRepository.findAll());
        request.getRequestDispatcher("/views/sinhvien/form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        SinhVien sv = repository.findById(id);
        request.setAttribute("sinhVien", sv);
        request.setAttribute("dsLopHoc", lopHocRepository.findAll());
        request.getRequestDispatcher("/views/sinhvien/form.jsp").forward(request, response);
    }

    private void deleteSinhVien(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        SinhVien sv = repository.findById(id);
        if (sv != null) {
            repository.delete(id);
            request.getSession().setAttribute("flashSuccess", "Đã xóa sinh viên: " + sv.getHoTen());
        }
        response.sendRedirect(request.getContextPath() + "/sinh-vien");
    }
}
