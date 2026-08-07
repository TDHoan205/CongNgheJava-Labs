package vn.edu.eaut.lab3;

public class Student {

    private String maSV;
    private String hoTen;
    private double diem;

    public Student() {
    }

    public Student(String maSV, String hoTen, double diem) {

        this.maSV = maSV;
        this.hoTen = hoTen;
        this.diem = diem;

    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public double getDiem() {
        return diem;
    }

    public void setDiem(double diem) {
        this.diem = diem;
    }

}