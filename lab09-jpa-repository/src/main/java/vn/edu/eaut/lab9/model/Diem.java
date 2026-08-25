package vn.edu.eaut.lab9.model;

import jakarta.persistence.*;

@Entity
@Table(name = "diem")
public class Diem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sinh_vien_id", nullable = false)
    private SinhVien sinhVien;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mon_hoc_id", nullable = false)
    private MonHoc monHoc;

    @Column(name = "diem_qua_trinh")
    private Double diemQuaTrinh;

    @Column(name = "diem_thi")
    private Double diemThi;

    @Column(name = "diem_tong_ket")
    private Double diemTongKet;

    @Column(name = "xep_loai", length = 20)
    private String xepLoai;

    public Diem() {
    }

    public Diem(SinhVien sinhVien, MonHoc monHoc, Double diemQuaTrinh, Double diemThi) {
        this.sinhVien = sinhVien;
        this.monHoc = monHoc;
        this.diemQuaTrinh = diemQuaTrinh;
        this.diemThi = diemThi;
        tinhDiem();
    }

    public void tinhDiem() {
        if (diemQuaTrinh != null && diemThi != null) {
            this.diemTongKet = Math.round((diemQuaTrinh * 0.3 + diemThi * 0.7) * 100.0) / 100.0;
            if (this.diemTongKet >= 9.0) {
                this.xepLoai = "Xuất sắc";
            } else if (this.diemTongKet >= 8.0) {
                this.xepLoai = "Giỏi";
            } else if (this.diemTongKet >= 6.5) {
                this.xepLoai = "Khá";
            } else if (this.diemTongKet >= 5.0) {
                this.xepLoai = "Trung bình";
            } else {
                this.xepLoai = "Yếu";
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public MonHoc getMonHoc() {
        return monHoc;
    }

    public void setMonHoc(MonHoc monHoc) {
        this.monHoc = monHoc;
    }

    public Double getDiemQuaTrinh() {
        return diemQuaTrinh;
    }

    public void setDiemQuaTrinh(Double diemQuaTrinh) {
        this.diemQuaTrinh = diemQuaTrinh;
        tinhDiem();
    }

    public Double getDiemThi() {
        return diemThi;
    }

    public void setDiemThi(Double diemThi) {
        this.diemThi = diemThi;
        tinhDiem();
    }

    public Double getDiemTongKet() {
        return diemTongKet;
    }

    public void setDiemTongKet(Double diemTongKet) {
        this.diemTongKet = diemTongKet;
    }

    public String getXepLoai() {
        return xepLoai;
    }

    public void setXepLoai(String xepLoai) {
        this.xepLoai = xepLoai;
    }
}
