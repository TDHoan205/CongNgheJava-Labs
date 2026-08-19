package vn.edu.eaut.lab8.repository;

import vn.edu.eaut.lab8.model.SinhVien;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SinhVienRepository {
    private static final List<SinhVien> data = new ArrayList<>();
    private static int autoId = 3;

    static {
        data.add(new SinhVien(1, "20240001", "Nguyễn Văn An", "an@gmail.com", "DCCNTT15.10.1"));
        data.add(new SinhVien(2, "20240002", "Trần Thị Bình", "binh@gmail.com", "DCCNTT15.10.2"));
    }

    public List<SinhVien> findAll() {
        return new ArrayList<>(data);
    }

    public SinhVien findById(int id) {
        return data.stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void add(SinhVien sv) {
        sv.setId(autoId++);
        data.add(sv);
    }

    public void update(SinhVien sv) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == sv.getId()) {
                data.set(i, sv);
                return;
            }
        }
    }

    public void delete(int id) {
        data.removeIf(x -> x.getId() == id);
    }

    public List<SinhVien> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        String kw = keyword.toLowerCase().trim();
        return data.stream()
                .filter(sv -> (sv.getHoTen() != null && sv.getHoTen().toLowerCase().contains(kw)) ||
                              (sv.getLop() != null && sv.getLop().toLowerCase().contains(kw)) ||
                              (sv.getMaSinhVien() != null && sv.getMaSinhVien().toLowerCase().contains(kw)))
                .collect(Collectors.toList());
    }
}
