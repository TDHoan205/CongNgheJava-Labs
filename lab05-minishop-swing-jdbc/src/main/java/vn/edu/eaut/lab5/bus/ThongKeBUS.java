package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.ThongKeDAL;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public class ThongKeBUS {

    private final ThongKeDAL dal =
            new ThongKeDAL();

    public BigDecimal tinhDoanhThu(
            LocalDate tuNgay,
            LocalDate denNgay)
            throws SQLException {

        if (tuNgay == null ||
                denNgay == null) {

            throw new IllegalArgumentException(
                    "Ngày không được để trống."
            );
        }

        if (tuNgay.isAfter(denNgay)) {

            throw new IllegalArgumentException(
                    "Từ ngày không được lớn hơn đến ngày."
            );
        }

        return dal.tinhDoanhThu(
                tuNgay,
                denNgay
        );
    }

    public String hoaDonCaoNhat()
            throws SQLException {

        return dal.hoaDonCaoNhat();
    }

    public String sanPhamBanChayNhat()
            throws SQLException {

        return dal.sanPhamBanChayNhat();
    }

    public String timHoaDon(
            LocalDate tuNgay,
            LocalDate denNgay,
            int maKh)
            throws SQLException {

        if (tuNgay.isAfter(denNgay)) {

            throw new IllegalArgumentException(
                    "Khoảng ngày không hợp lệ."
            );
        }

        return dal.timHoaDon(
                tuNgay,
                denNgay,
                maKh
        );
    }
}