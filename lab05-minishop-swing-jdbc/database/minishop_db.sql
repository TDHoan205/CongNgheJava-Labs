DROP DATABASE IF EXISTS minishop_db;

CREATE DATABASE IF NOT EXISTS minishop_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE minishop_db;

-- 1. Bảng danh_muc
CREATE TABLE IF NOT EXISTS danh_muc (
    ma_dm INT AUTO_INCREMENT PRIMARY KEY,
    ten_dm VARCHAR(100) NOT NULL
);

-- 2. Bảng san_pham
CREATE TABLE IF NOT EXISTS san_pham (
    ma_sp INT AUTO_INCREMENT PRIMARY KEY,
    ten_sp VARCHAR(100) NOT NULL,
    don_gia DECIMAL(12,2) NOT NULL,
    so_luong INT NOT NULL DEFAULT 0,
    ma_dm INT NULL,
    FOREIGN KEY (ma_dm) REFERENCES danh_muc(ma_dm) ON DELETE SET NULL
);

-- 3. Bảng khach_hang
CREATE TABLE IF NOT EXISTS khach_hang (
    ma_kh INT AUTO_INCREMENT PRIMARY KEY,
    ten_kh VARCHAR(100) NOT NULL,
    sdt VARCHAR(10) NOT NULL,
    dia_chi VARCHAR(255)
);

-- 4. Bảng hoa_don
CREATE TABLE IF NOT EXISTS hoa_don (
    ma_hd INT AUTO_INCREMENT PRIMARY KEY,
    ngay_lap DATE NOT NULL,
    ma_kh INT NOT NULL,
    tong_tien DECIMAL(12,2) DEFAULT 0,
    FOREIGN KEY (ma_kh) REFERENCES khach_hang(ma_kh)
);

-- 5. Bảng chi_tiet_hoa_don
CREATE TABLE IF NOT EXISTS chi_tiet_hoa_don (
    ma_hd INT NOT NULL,
    ma_sp INT NOT NULL,
    so_luong INT NOT NULL,
    don_gia DECIMAL(12,2) NOT NULL,
    thanh_tien DECIMAL(12,2) NOT NULL,
    PRIMARY KEY (ma_hd, ma_sp),
    FOREIGN KEY (ma_hd) REFERENCES hoa_don(ma_hd),
    FOREIGN KEY (ma_sp) REFERENCES san_pham(ma_sp)
);

-- 6. Bảng tai_khoan
CREATE TABLE IF NOT EXISTS tai_khoan (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    ho_ten VARCHAR(100) NOT NULL,
    vai_tro VARCHAR(20) NOT NULL
);

-- Chèn dữ liệu mẫu
INSERT INTO danh_muc (ma_dm, ten_dm) VALUES
(1, 'Laptop'),
(2, 'Điện thoại'),
(3, 'Phụ kiện'),
(4, 'Màn hình'),
(5, 'Bàn phím'),
(6, 'Chuột');

INSERT INTO san_pham (ma_sp, ten_sp, don_gia, so_luong, ma_dm) VALUES
(1, 'Ban phim Logitech K120', 180000, 50, 5),
(2, 'Chuot khong day Rapoo', 220000, 4, 6),
(3, 'USB Kingston 32GB', 150000, 100, 3),
(4, 'Tai nghe Sony Basic', 350000, 30, 3),
(5, 'Laptop Dell Inspiron 15', 15000000, 10, 1),
(6, 'Man hinh LG 24 inch', 3200000, 3, 4);

INSERT INTO khach_hang (ma_kh, ten_kh, sdt, dia_chi) VALUES
(1, 'Nguyen Van An', '0912345678', 'Ha Noi'),
(2, 'Tran Thi Binh', '0987654321', 'Bac Ninh'),
(3, 'Le Van Cuong', '0901111222', 'Hai Duong');

INSERT INTO tai_khoan (username, password, ho_ten, vai_tro) VALUES
('admin', 'admin123', 'Quản Trị Viên', 'ADMIN'),
('nhanvien', 'nv123', 'Nhân Viên Bán Hàng', 'NHANVIEN'),
('ketoan', 'kt123', 'Kế Toán Viên', 'KETOAN');