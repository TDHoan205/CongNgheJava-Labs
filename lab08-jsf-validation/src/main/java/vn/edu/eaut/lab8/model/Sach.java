package vn.edu.eaut.lab8.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Sach {
    private int id;

    @NotBlank(message = "Mã sách không được để trống")
    private String maSach;

    @NotBlank(message = "Tên sách không được để trống")
    private String tenSach;

    @NotBlank(message = "Tác giả không được để trống")
    private String tacGia;

    @NotNull(message = "Năm xuất bản không được để trống")
    @Min(value = 1800, message = "Năm xuất bản phải từ 1800 trở lên")
    private Integer namXuatBan;

    @NotNull(message = "Giá sách không được để trống")
    @Min(value = 0, message = "Giá sách phải lớn hơn hoặc bằng 0")
    private Double gia;

    public Sach() {}

    public Sach(int id, String maSach, String tenSach, String tacGia, Integer namXuatBan, Double gia) {
        this.id = id;
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.namXuatBan = namXuatBan;
        this.gia = gia;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMaSach() { return maSach; }
    public void setMaSach(String maSach) { this.maSach = maSach; }

    public String getTenSach() { return tenSach; }
    public void setTenSach(String tenSach) { this.tenSach = tenSach; }

    public String getTacGia() { return tacGia; }
    public void setTacGia(String tacGia) { this.tacGia = tacGia; }

    public Integer getNamXuatBan() { return namXuatBan; }
    public void setNamXuatBan(Integer namXuatBan) { this.namXuatBan = namXuatBan; }

    public Double getGia() { return gia; }
    public void setGia(Double gia) { this.gia = gia; }
}
