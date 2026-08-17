package vn.edu.eaut.lab7.repository;

import vn.edu.eaut.lab7.model.LopHoc;
import java.util.*;
import java.util.stream.Collectors;

public class LopHocRepository {
    private static final List<LopHoc> data = new ArrayList<>();
    private static int autoId = 3;

    static {
        data.add(new LopHoc(1, "DCCNTT15.10.1", "Công nghệ thông tin 15.10.1", "ThS. Nguyễn Minh", 35));
        data.add(new LopHoc(2, "DCCNTT15.10.2", "Công nghệ thông tin 15.10.2", "ThS. Trần Lan", 38));
    }

    public List<LopHoc> findAll() { return data; }
    public LopHoc findById(int id) { return data.stream().filter(x -> x.getId() == id).findFirst().orElse(null); }
    public void add(LopHoc l) { l.setId(autoId++); data.add(l); }
    public void update(LopHoc l) {
        LopHoc old = findById(l.getId());
        if (old != null) {
            old.setMaLop(l.getMaLop()); old.setTenLop(l.getTenLop());
            old.setCoVanHocTap(l.getCoVanHocTap()); old.setSoLuongSinhVien(l.getSoLuongSinhVien());
        }
    }
    public void delete(int id) { data.removeIf(x -> x.getId() == id); }
    public List<LopHoc> search(String key) {
        if (key == null || key.trim().isEmpty()) return data;
        String k = key.toLowerCase();
        return data.stream().filter(x -> x.getMaLop().toLowerCase().contains(k)
                || x.getTenLop().toLowerCase().contains(k)).collect(Collectors.toList());
    }
}
