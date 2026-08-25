package vn.edu.eaut.lab10.service;

import vn.edu.eaut.lab10.model.SanPham;
import vn.edu.eaut.lab10.repository.SanPhamRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SanPhamService {

    private final SanPhamRepository repo;

    public SanPhamService() {
        this.repo = new SanPhamRepository();
    }

    public List<SanPham> findAll() {
        return repo.findAll();
    }

    public List<SanPham> search(String keyword) {
        return repo.search(keyword);
    }

    public SanPham findById(Integer id) {
        return repo.findById(id);
    }

    public String create(String ma, String ten, String moTa, BigDecimal gia, Integer soLuong) {
        List<String> errors = validate(ma, ten, gia, soLuong);
        if (!errors.isEmpty()) {
            return String.join("; ", errors);
        }
        if (repo.existsByMa(ma, null)) {
            return "Mã sản phẩm đã tồn tại.";
        }
        SanPham sp = new SanPham(ma.trim(), ten.trim(), moTa, gia, soLuong);
        repo.save(sp);
        return null;
    }

    public String update(Integer id, String ma, String ten, String moTa, BigDecimal gia, Integer soLuong) {
        SanPham existing = repo.findById(id);
        if (existing == null) {
            return "Không tìm thấy sản phẩm.";
        }
        List<String> errors = validate(ma, ten, gia, soLuong);
        if (!errors.isEmpty()) {
            return String.join("; ", errors);
        }
        if (repo.existsByMa(ma, id)) {
            return "Mã sản phẩm đã tồn tại.";
        }
        existing.setMa(ma.trim());
        existing.setTen(ten.trim());
        existing.setMoTa(moTa);
        existing.setGia(gia);
        existing.setSoLuong(soLuong);
        repo.update(existing);
        return null;
    }

    public void delete(Integer id) {
        repo.delete(id);
    }

    private List<String> validate(String ma, String ten, BigDecimal gia, Integer soLuong) {
        List<String> errors = new ArrayList<>();
        if (ma == null || ma.trim().isEmpty()) {
            errors.add("Mã sản phẩm không được để trống.");
        }
        if (ten == null || ten.trim().isEmpty()) {
            errors.add("Tên sản phẩm không được để trống.");
        }
        if (gia == null || gia.compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("Giá phải lớn hơn 0.");
        }
        if (soLuong == null || soLuong < 0) {
            errors.add("Số lượng không được âm.");
        }
        return errors;
    }
}
