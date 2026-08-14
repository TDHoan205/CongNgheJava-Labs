package vn.edu.eaut.lab5.util;

import vn.edu.eaut.lab5.model.ChiTietHoaDon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class HoaDonExportUtil {

    public static File exportCSV(
            int maHd,
            LocalDate ngayLap,
            String tenKhachHang,
            String sdt,
            List<ChiTietHoaDon> danhSach,
            BigDecimal tongTien)
            throws IOException {

        String fileName = "HoaDon_" + maHd + ".csv";
        File file = new File(fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Ma hoa don,Ngay lap,Khach hang,So dien thoai");
            writer.newLine();

            writer.write(maHd + "," + ngayLap + "," + csv(tenKhachHang) + "," + sdt);
            writer.newLine();
            writer.newLine();

            writer.write("Ten san pham,So luong,Don gia,Thanh tien");
            writer.newLine();

            for (ChiTietHoaDon ct : danhSach) {
                writer.write(csv(ct.getTenSp()) + "," +
                        ct.getSoLuong() + "," +
                        ct.getDonGia() + "," +
                        ct.getThanhTien());
                writer.newLine();
            }

            writer.newLine();
            writer.write("TONG TIEN,,," + tongTien);
            writer.newLine();
        }

        return file;
    }

    public static File exportTXT(
            int maHd,
            LocalDate ngayLap,
            String tenKhachHang,
            String sdt,
            List<ChiTietHoaDon> danhSach,
            BigDecimal tongTien)
            throws IOException {

        String fileName = "HoaDon_" + maHd + ".txt";
        File file = new File(fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("==================================================");
            writer.newLine();
            writer.write("                 HOA DON BAN HANG                ");
            writer.newLine();
            writer.write("==================================================");
            writer.newLine();
            writer.write("Ma hoa don   : " + maHd);
            writer.newLine();
            writer.write("Ngay lap     : " + ngayLap);
            writer.newLine();
            writer.write("Khach hang   : " + tenKhachHang);
            writer.newLine();
            writer.write("So dien thoai: " + sdt);
            writer.newLine();
            writer.write("--------------------------------------------------");
            writer.newLine();
            writer.write(String.format("%-22s %-8s %-10s %-10s", "Ten SP", "SL", "Don gia", "Thanh tien"));
            writer.newLine();
            writer.write("--------------------------------------------------");
            writer.newLine();

            for (ChiTietHoaDon ct : danhSach) {
                writer.write(String.format("%-22s %-8d %-10s %-10s",
                        truncate(ct.getTenSp(), 22),
                        ct.getSoLuong(),
                        ct.getDonGia(),
                        ct.getThanhTien()));
                writer.newLine();
            }

            writer.write("--------------------------------------------------");
            writer.newLine();
            writer.write("TONG CONG TIEN: " + tongTien + " VND");
            writer.newLine();
            writer.write("==================================================");
            writer.newLine();
        }

        return file;
    }

    private static String csv(String text) {
        if (text == null) {
            return "";
        }
        return "\"" + text.replace("\"", "\"\"") + "\"";
    }

    private static String truncate(String text, int length) {
        if (text == null) return "";
        if (text.length() <= length) return text;
        return text.substring(0, length - 3) + "...";
    }
}