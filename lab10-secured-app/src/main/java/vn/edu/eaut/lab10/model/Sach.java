package vn.edu.eaut.lab10.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sach")
public class Sach {

    private Integer id;
    private String maSach;
    private String tenSach;
    private String tacGia;
    private String nhaXuatBan;
    private Integer namXuatBan;

    public Sach() {
    }

    public Sach(String maSach, String tenSach, String tacGia, String nhaXuatBan, Integer namXuatBan) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.nhaXuatBan = nhaXuatBan;
        this.namXuatBan = namXuatBan;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "ma_sach", unique = true, length = 20, nullable = false)
    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    @Column(name = "ten_sach", length = 200, nullable = false)
    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    @Column(name = "tac_gia", length = 100)
    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    @Column(name = "nha_xuat_ban", length = 100)
    public String getNhaXuatBan() {
        return nhaXuatBan;
    }

    public void setNhaXuatBan(String nhaXuatBan) {
        this.nhaXuatBan = nhaXuatBan;
    }

    @Column(name = "nam_xuat_ban")
    public Integer getNamXuatBan() {
        return namXuatBan;
    }

    public void setNamXuatBan(Integer namXuatBan) {
        this.namXuatBan = namXuatBan;
    }
}
