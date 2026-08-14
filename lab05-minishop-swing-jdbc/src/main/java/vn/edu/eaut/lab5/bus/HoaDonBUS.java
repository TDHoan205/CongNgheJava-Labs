package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.HoaDonDAL;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.HoaDon;

import java.sql.SQLException;
import java.util.List;

public class HoaDonBUS {

    private final HoaDonDAL dal = new HoaDonDAL();

    public List<HoaDon> findAllInvoices() throws SQLException {
        return dal.findAllInvoices();
    }

    public List<ChiTietHoaDon> getChiTietHoaDon(int maHd) throws SQLException {
        return dal.getChiTietHoaDon(maHd);
    }

    public int save(
            int maKh,
            List<ChiTietHoaDon> chiTietList)
            throws SQLException {

        if (maKh <= 0) {
            throw new IllegalArgumentException("Vui lòng chọn khách hàng.");
        }

        if (chiTietList == null || chiTietList.isEmpty()) {
            throw new IllegalArgumentException("Hóa đơn chưa có sản phẩm.");
        }

        for (ChiTietHoaDon ct : chiTietList) {
            if (ct.getSoLuong() <= 0) {
                throw new IllegalArgumentException("Số lượng phải lớn hơn 0.");
            }
            if (ct.getDonGia() == null) {
                throw new IllegalArgumentException("Đơn giá không hợp lệ.");
            }
        }

        return dal.insertHoaDon(maKh, chiTietList);
    }
}