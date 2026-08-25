package vn.edu.eaut.lab9.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lop_hoc")
public class LopHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_lop", nullable = false, unique = true, length = 20)
    private String maLop;

    @Column(name = "ten_lop", nullable = false, length = 100)
    private String tenLop;

    @Column(name = "khoa_hoc", length = 50)
    private String khoaHoc;

    @OneToMany(mappedBy = "lopHoc", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SinhVien> dsSinhVien = new ArrayList<>();

    public LopHoc() {
    }

    public LopHoc(String maLop, String tenLop, String khoaHoc) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.khoaHoc = khoaHoc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public String getKhoaHoc() {
        return khoaHoc;
    }

    public void setKhoaHoc(String khoaHoc) {
        this.khoaHoc = khoaHoc;
    }

    public List<SinhVien> getDsSinhVien() {
        return dsSinhVien;
    }

    public void setDsSinhVien(List<SinhVien> dsSinhVien) {
        this.dsSinhVien = dsSinhVien;
    }
}
