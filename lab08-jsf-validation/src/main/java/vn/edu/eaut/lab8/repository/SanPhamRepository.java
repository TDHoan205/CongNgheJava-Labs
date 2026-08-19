package vn.edu.eaut.lab8.repository;

import vn.edu.eaut.lab8.model.SanPham;
import java.util.ArrayList;
import java.util.List;

public class SanPhamRepository {
    private static final List<SanPham> data = new ArrayList<>();
    private static int autoId = 3;

    static {
        data.add(new SanPham(1, "SP001", "Laptop Dell XPS 15", 35000000.0, 10));
        data.add(new SanPham(2, "SP002", "Bàn phím cơ Logi", 2500000.0, 25));
    }

    public List<SanPham> findAll() {
        return new ArrayList<>(data);
    }

    public SanPham findById(int id) {
        return data.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public void add(SanPham sp) {
        sp.setId(autoId++);
        data.add(sp);
    }

    public void update(SanPham sp) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == sp.getId()) {
                data.set(i, sp);
                return;
            }
        }
    }

    public void delete(int id) {
        data.removeIf(p -> p.getId() == id);
    }
}
