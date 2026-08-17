package vn.edu.eaut.lab7.model;

public class DiemSinhVien {
    private int id;
    private int sinhVienId;
    private String maSinhVien;
    private String hoTen;
    private double chuyenCan;
    private double giuaKy;
    private double cuoiKy;

    public DiemSinhVien() {}

    public DiemSinhVien(int id, int sinhVienId, String maSinhVien, String hoTen,
                        double chuyenCan, double giuaKy, double cuoiKy) {
        this.id = id;
        this.sinhVienId = sinhVienId;
        this.maSinhVien = maSinhVien;
        this.hoTen = hoTen;
        this.chuyenCan = chuyenCan;
        this.giuaKy = giuaKy;
        this.cuoiKy = cuoiKy;
    }

    public double getTongKet() {
        return chuyenCan * 0.1 + giuaKy * 0.3 + cuoiKy * 0.6;
    }

    public String getXepLoai() {
        double d = getTongKet();
        if (d >= 8.5) return "A";
        if (d >= 7.0) return "B";
        if (d >= 5.5) return "C";
        if (d >= 4.0) return "D";
        return "F";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getSinhVienId() { return sinhVienId; }
    public void setSinhVienId(int sinhVienId) { this.sinhVienId = sinhVienId; }
    public String getMaSinhVien() { return maSinhVien; }
    public void setMaSinhVien(String maSinhVien) { this.maSinhVien = maSinhVien; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public double getChuyenCan() { return chuyenCan; }
    public void setChuyenCan(double chuyenCan) { this.chuyenCan = chuyenCan; }
    public double getGiuaKy() { return giuaKy; }
    public void setGiuaKy(double giuaKy) { this.giuaKy = giuaKy; }
    public double getCuoiKy() { return cuoiKy; }
    public void setCuoiKy(double cuoiKy) { this.cuoiKy = cuoiKy; }
}
