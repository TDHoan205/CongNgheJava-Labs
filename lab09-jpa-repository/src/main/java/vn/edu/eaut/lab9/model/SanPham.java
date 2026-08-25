package vn.edu.eaut.lab9.model;

import jakarta.persistence.*;

@Entity
@Table(name = "san_pham")
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_sp", nullable = false, unique = true, length = 20)
    private String maSp;

    @Column(name = "ten_sp", nullable = false, length = 150)
    private String tenSp;

    @Column(name = "loai_sp", length = 50)
    private String loaiSp;

    @Column(name = "gia")
    private Double gia = 0.0;

    @Column(name = "so_luong")
    private Integer soLuong = 0;

    public SanPham() {
    }

    public SanPham(String maSp, String tenSp, String loaiSp, Double gia, Integer soLuong) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.loaiSp = loaiSp;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getLoaiSp() {
        return loaiSp;
    }

    public void setLoaiSp(String loaiSp) {
        this.loaiSp = loaiSp;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
}
