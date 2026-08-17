package vn.edu.eaut.lab7.repository;

import vn.edu.eaut.lab7.model.Sach;
import java.util.*;
import java.util.stream.Collectors;

public class SachRepository {
    private static final List<Sach> data = new ArrayList<>();
    private static int autoId = 3;

    static {
        data.add(new Sach(1, "S001", "Lập trình Java", "Nguyễn Văn A", "NXB Giáo dục", 2024));
        data.add(new Sach(2, "S002", "Web Java EE", "Trần Văn B", "NXB Khoa học", 2025));
    }

    public List<Sach> findAll() { return data; }
    public Sach findById(int id) { return data.stream().filter(x -> x.getId() == id).findFirst().orElse(null); }
    public void add(Sach s) { s.setId(autoId++); data.add(s); }
    public void update(Sach s) {
        Sach old = findById(s.getId());
        if (old != null) {
            old.setMaSach(s.getMaSach()); old.setTenSach(s.getTenSach());
            old.setTacGia(s.getTacGia()); old.setNhaXuatBan(s.getNhaXuatBan());
            old.setNamXuatBan(s.getNamXuatBan());
        }
    }
    public void delete(int id) { data.removeIf(x -> x.getId() == id); }
    public List<Sach> search(String key) {
        if (key == null || key.trim().isEmpty()) return data;
        String k = key.toLowerCase();
        return data.stream().filter(x -> x.getTenSach().toLowerCase().contains(k)
                || x.getTacGia().toLowerCase().contains(k)).collect(Collectors.toList());
    }
}
