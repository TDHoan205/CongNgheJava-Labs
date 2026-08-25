package vn.edu.eaut.lab9.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab9.model.Diem;
import vn.edu.eaut.lab9.model.MonHoc;
import vn.edu.eaut.lab9.model.SinhVien;
import vn.edu.eaut.lab9.repository.DiemRepository;
import vn.edu.eaut.lab9.repository.MonHocRepository;
import vn.edu.eaut.lab9.repository.SinhVienRepository;

import java.io.IOException;

@WebServlet("/diem")
public class DiemController extends HttpServlet {

    private final DiemRepository diemRepo = new DiemRepository();
    private final SinhVienRepository svRepo = new SinhVienRepository();
    private final MonHocRepository monHocRepo = new MonHocRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                request.setAttribute("diem", new Diem());
                request.setAttribute("dsSinhVien", svRepo.findAll());
                request.setAttribute("dsMonHoc", monHocRepo.findAll());
                request.getRequestDispatcher("/views/diem/form.jsp").forward(request, response);
                break;
            case "edit":
                int editId = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("diem", diemRepo.findById(editId));
                request.setAttribute("dsSinhVien", svRepo.findAll());
                request.setAttribute("dsMonHoc", monHocRepo.findAll());
                request.getRequestDispatcher("/views/diem/form.jsp").forward(request, response);
                break;
            case "delete":
                int delId = Integer.parseInt(request.getParameter("id"));
                diemRepo.delete(delId);
                request.getSession().setAttribute("flashSuccess", "Đã xóa điểm môn học thành công!");
                response.sendRedirect(request.getContextPath() + "/diem");
                break;
            case "list":
            default:
                String keyword = request.getParameter("keyword");
                if (keyword == null || keyword.isBlank()) {
                    request.setAttribute("dsDiem", diemRepo.findAll());
                } else {
                    request.setAttribute("dsDiem", diemRepo.search(keyword));
                    request.setAttribute("keyword", keyword);
                }
                String flash = (String) request.getSession().getAttribute("flashSuccess");
                if (flash != null) {
                    request.setAttribute("successMessage", flash);
                    request.getSession().removeAttribute("flashSuccess");
                }
                request.getRequestDispatcher("/views/diem/list.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("id");
        int svId = Integer.parseInt(request.getParameter("sinhVienId"));
        int mhId = Integer.parseInt(request.getParameter("monHocId"));
        double dqt = Double.parseDouble(request.getParameter("diemQuaTrinh"));
        double dt = Double.parseDouble(request.getParameter("diemThi"));

        SinhVien sv = svRepo.findById(svId);
        MonHoc mh = monHocRepo.findById(mhId);

        Diem diem = new Diem(sv, mh, dqt, dt);
        try {
            if (idStr != null && !idStr.isBlank()) {
                diem.setId(Integer.parseInt(idStr));
                diemRepo.update(diem);
                request.getSession().setAttribute("flashSuccess", "Cập nhật điểm thành công!");
            } else {
                diemRepo.save(diem);
                request.getSession().setAttribute("flashSuccess", "Nhập điểm mới thành công!");
            }
            response.sendRedirect(request.getContextPath() + "/diem");
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Lỗi lưu điểm: " + ex.getMessage());
            request.setAttribute("diem", diem);
            request.setAttribute("dsSinhVien", svRepo.findAll());
            request.setAttribute("dsMonHoc", monHocRepo.findAll());
            request.getRequestDispatcher("/views/diem/form.jsp").forward(request, response);
        }
    }
}
