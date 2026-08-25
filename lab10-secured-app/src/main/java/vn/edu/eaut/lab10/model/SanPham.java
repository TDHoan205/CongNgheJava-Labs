package vn.edu.eaut.lab10.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "san_pham")
public class SanPham {

    private Integer id;
    private String ma;
    private String ten;
    private String moTa;
    private BigDecimal gia;
    private Integer soLuong;

    public SanPham() {
    }

    public SanPham(String ma, String ten, String moTa, BigDecimal gia, Integer soLuong) {
        this.ma = ma;
        this.ten = ten;
        this.moTa = moTa;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(unique = true, length = 20, nullable = false)
    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    @Column(length = 100, nullable = false)
    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    @Column(name = "mo_ta", columnDefinition = "TEXT")
    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    @Column(precision = 12, scale = 2, nullable = false)
    public BigDecimal getGia() {
        return gia;
    }

    public void setGia(BigDecimal gia) {
        this.gia = gia;
    }

    @Column(name = "so_luong", nullable = false)
    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
}
