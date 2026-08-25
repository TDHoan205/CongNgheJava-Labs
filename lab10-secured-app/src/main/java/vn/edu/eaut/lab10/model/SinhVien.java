package vn.edu.eaut.lab10.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sinh_vien")
public class SinhVien {

    private Integer id;
    private String maSinhVien;
    private String hoTen;
    private String email;
    private String lop;

    public SinhVien() {
    }

    public SinhVien(String maSinhVien, String hoTen, String email, String lop) {
        this.maSinhVien = maSinhVien;
        this.hoTen = hoTen;
        this.email = email;
        this.lop = lop;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "ma_sinh_vien", unique = true, length = 20, nullable = false)
    public String getMaSinhVien() {
        return maSinhVien;
    }

    public void setMaSinhVien(String maSinhVien) {
        this.maSinhVien = maSinhVien;
    }

    @Column(name = "ho_ten", length = 100, nullable = false)
    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    @Column(name = "email", length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "lop", length = 50)
    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }
}
