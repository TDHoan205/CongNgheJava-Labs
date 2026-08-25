package vn.edu.eaut.lab10.service;

import vn.edu.eaut.lab10.model.SinhVien;
import vn.edu.eaut.lab10.repository.SinhVienRepository;

import java.util.ArrayList;
import java.util.List;

public class SinhVienService {

    private final SinhVienRepository repo;

    public SinhVienService() {
        this.repo = new SinhVienRepository();
    }

    public List<SinhVien> findAll() {
        return repo.findAll();
    }

    public List<SinhVien> search(String keyword) {
        return repo.search(keyword);
    }

    public SinhVien findById(Integer id) {
        return repo.findById(id);
    }

    public String create(String maSV, String hoTen, String email, String lop) {
        List<String> errors = validate(maSV, hoTen);
        if (!errors.isEmpty()) {
            return String.join("; ", errors);
        }
        if (repo.existsByMa(maSV, null)) {
            return "Mã sinh viên đã tồn tại.";
        }
        SinhVien sv = new SinhVien(maSV.trim(), hoTen.trim(), email, lop);
        repo.save(sv);
        return null;
    }

    public String update(Integer id, String maSV, String hoTen, String email, String lop) {
        SinhVien existing = repo.findById(id);
        if (existing == null) {
            return "Không tìm thấy sinh viên.";
        }
        List<String> errors = validate(maSV, hoTen);
        if (!errors.isEmpty()) {
            return String.join("; ", errors);
        }
        if (repo.existsByMa(maSV, id)) {
            return "Mã sinh viên đã tồn tại.";
        }
        existing.setMaSinhVien(maSV.trim());
        existing.setHoTen(hoTen.trim());
        existing.setEmail(email);
        existing.setLop(lop);
        repo.update(existing);
        return null;
    }

    public void delete(Integer id) {
        repo.delete(id);
    }

    private List<String> validate(String maSV, String hoTen) {
        List<String> errors = new ArrayList<>();
        if (maSV == null || maSV.trim().isEmpty()) {
            errors.add("Mã sinh viên không được để trống.");
        }
        if (hoTen == null || hoTen.trim().isEmpty()) {
            errors.add("Họ tên không được để trống.");
        }
        return errors;
    }
}
