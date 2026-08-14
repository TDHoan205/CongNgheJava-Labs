package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.DanhMucDAL;
import vn.edu.eaut.lab5.model.DanhMuc;

import java.sql.SQLException;
import java.util.List;

public class DanhMucBUS {

    private final DanhMucDAL dal = new DanhMucDAL();

    public List<DanhMuc> findAll() throws SQLException {
        return dal.findAll();
    }

    public boolean save(DanhMuc dm) throws SQLException {

        if (dm.getTenDm() == null ||
                dm.getTenDm().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Tên danh mục không được để trống!"
            );
        }

        if (dm.getMaDm() == 0) {
            return dal.insert(dm);
        }

        return dal.update(dm);
    }

    public boolean delete(int maDm) throws SQLException {

        if (dal.existsProduct(maDm)) {

            throw new IllegalArgumentException(
                    "Không thể xóa danh mục vì đang có sản phẩm!"
            );
        }

        return dal.delete(maDm);
    }
}