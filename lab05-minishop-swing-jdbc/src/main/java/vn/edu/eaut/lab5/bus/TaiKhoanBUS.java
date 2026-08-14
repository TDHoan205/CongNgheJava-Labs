package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.TaiKhoanDAL;
import vn.edu.eaut.lab5.model.TaiKhoan;

public class TaiKhoanBUS {

    private final TaiKhoanDAL dal =
            new TaiKhoanDAL();

    public TaiKhoan dangNhap(
            String username,
            String password) throws Exception {

        if (username == null ||
                username.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Vui lòng nhập username."
            );
        }

        if (password == null ||
                password.isEmpty()) {

            throw new IllegalArgumentException(
                    "Vui lòng nhập mật khẩu."
            );
        }

        return dal.dangNhap(
                username.trim(),
                password
        );
    }
}