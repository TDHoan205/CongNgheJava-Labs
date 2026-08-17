package vn.edu.eaut.lab7.repository;

import vn.edu.eaut.lab7.model.DiemSinhVien;
import java.util.*;

public class DiemRepository {
    private static final List<DiemSinhVien> data = new ArrayList<>();
    private static int autoId = 3;

    static {
        data.add(new DiemSinhVien(1, 1, "20240001", "Nguyễn Văn An", 9, 8, 9));
        data.add(new DiemSinhVien(2, 2, "20240002", "Trần Thị Bình", 8, 7, 7));
    }

    public List<DiemSinhVien> findAll() { return data; }
    public DiemSinhVien findById(int id) { return data.stream().filter(x -> x.getId() == id).findFirst().orElse(null); }
    public void add(DiemSinhVien d) { d.setId(autoId++); data.add(d); }
    public void update(DiemSinhVien d) {
        DiemSinhVien old = findById(d.getId());
        if (old != null) {
            old.setSinhVienId(d.getSinhVienId()); old.setMaSinhVien(d.getMaSinhVien());
            old.setHoTen(d.getHoTen()); old.setChuyenCan(d.getChuyenCan());
            old.setGiuaKy(d.getGiuaKy()); old.setCuoiKy(d.getCuoiKy());
        }
    }
    public void delete(int id) { data.removeIf(x -> x.getId() == id); }
}
