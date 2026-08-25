package vn.edu.eaut.lab10.service;

import vn.edu.eaut.lab10.model.Sach;
import vn.edu.eaut.lab10.repository.SachRepository;

import java.util.ArrayList;
import java.util.List;

public class SachService {

    private final SachRepository repo;

    public SachService() {
        this.repo = new SachRepository();
    }

    public List<Sach> findAll() {
        return repo.findAll();
    }

    public List<Sach> search(String keyword) {
        return repo.search(keyword);
    }

    public Sach findById(Integer id) {
        return repo.findById(id);
    }

    public String create(String maSach, String tenSach, String tacGia, String nhaXuatBan, Integer namXuatBan) {
        List<String> errors = validate(maSach, tenSach, namXuatBan);
        if (!errors.isEmpty()) {
            return String.join("; ", errors);
        }
        if (repo.existsByMa(maSach, null)) {
            return "Mã sách đã tồn tại.";
        }
        Sach sach = new Sach(maSach.trim(), tenSach.trim(), tacGia, nhaXuatBan, namXuatBan);
        repo.save(sach);
        return null;
    }

    public String update(Integer id, String maSach, String tenSach, String tacGia, String nhaXuatBan, Integer namXuatBan) {
        Sach existing = repo.findById(id);
        if (existing == null) {
            return "Không tìm thấy sách.";
        }
        List<String> errors = validate(maSach, tenSach, namXuatBan);
        if (!errors.isEmpty()) {
            return String.join("; ", errors);
        }
        if (repo.existsByMa(maSach, id)) {
            return "Mã sách đã tồn tại.";
        }
        existing.setMaSach(maSach.trim());
        existing.setTenSach(tenSach.trim());
        existing.setTacGia(tacGia);
        existing.setNhaXuatBan(nhaXuatBan);
        existing.setNamXuatBan(namXuatBan);
        repo.update(existing);
        return null;
    }

    public void delete(Integer id) {
        repo.delete(id);
    }

    private List<String> validate(String maSach, String tenSach, Integer namXuatBan) {
        List<String> errors = new ArrayList<>();
        if (maSach == null || maSach.trim().isEmpty()) {
            errors.add("Mã sách không được để trống.");
        }
        if (tenSach == null || tenSach.trim().isEmpty()) {
            errors.add("Tên sách không được để trống.");
        }
        if (namXuatBan != null && namXuatBan <= 0) {
            errors.add("Năm xuất bản phải lớn hơn 0.");
        }
        return errors;
    }
}
