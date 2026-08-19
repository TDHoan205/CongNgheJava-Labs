package vn.edu.eaut.lab8.repository;

import vn.edu.eaut.lab8.model.Sach;
import java.util.ArrayList;
import java.util.List;

public class SachRepository {
    private static final List<Sach> data = new ArrayList<>();
    private static int autoId = 3;

    static {
        data.add(new Sach(1, "S001", "Lập trình Java Cơ bản", "Nguyễn Văn A", 2022, 150000.0));
        data.add(new Sach(2, "S002", "Jakarta EE Enterprise", "Trần Thị B", 2023, 220000.0));
    }

    public List<Sach> findAll() {
        return new ArrayList<>(data);
    }

    public Sach findById(int id) {
        return data.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public void add(Sach sach) {
        sach.setId(autoId++);
        data.add(sach);
    }

    public void update(Sach sach) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == sach.getId()) {
                data.set(i, sach);
                return;
            }
        }
    }

    public void delete(int id) {
        data.removeIf(s -> s.getId() == id);
    }
}
