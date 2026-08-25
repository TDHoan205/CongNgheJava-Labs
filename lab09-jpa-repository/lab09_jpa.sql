-- ========================================================
-- DATABASE CREATION SCRIPT FOR LAB 9 - JAKARTA PERSISTENCE
-- Học phần: Công nghệ Java (IT3242)
-- Sinh viên: Trần Đức Hoàn - MSSV: 2030022
-- Trường: Đại học Công nghệ Đông Á
-- ========================================================

CREATE DATABASE IF NOT EXISTS lab09_jpa CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE lab09_jpa;

-- 1. Bảng LopHoc (Bài 6)
DROP TABLE IF EXISTS diem;
DROP TABLE IF EXISTS sinh_vien;
DROP TABLE IF EXISTS lop_hoc;
DROP TABLE IF EXISTS mon_hoc;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS sach;
DROP TABLE IF EXISTS san_pham;

CREATE TABLE lop_hoc (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_lop VARCHAR(20) NOT NULL UNIQUE,
    ten_lop VARCHAR(100) NOT NULL,
    khoa_hoc VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. Bảng SinhVien (Bài 2, Bài 6)
CREATE TABLE sinh_vien (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_sinh_vien VARCHAR(20) NOT NULL UNIQUE,
    ho_ten VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    ngay_sinh DATE,
    lop_id INT,
    FOREIGN KEY (lop_id) REFERENCES lop_hoc(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. Bảng MonHoc (Bài 7)
CREATE TABLE mon_hoc (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_mon VARCHAR(20) NOT NULL UNIQUE,
    ten_mon VARCHAR(100) NOT NULL,
    so_tin_chi INT DEFAULT 3
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. Bảng Diem (Bài 7)
CREATE TABLE diem (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sinh_vien_id INT NOT NULL,
    mon_hoc_id INT NOT NULL,
    diem_qua_trinh DOUBLE,
    diem_thi DOUBLE,
    diem_tong_ket DOUBLE,
    xep_loai VARCHAR(20),
    FOREIGN KEY (sinh_vien_id) REFERENCES sinh_vien(id) ON DELETE CASCADE,
    FOREIGN KEY (mon_hoc_id) REFERENCES mon_hoc(id) ON DELETE CASCADE,
    UNIQUE KEY uk_sv_mon (sinh_vien_id, mon_hoc_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5. Bảng Roles (Bài 8 / Lab 10 Prep)
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 6. Bảng Users (Bài 8 / Lab 10 Prep)
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    full_name VARCHAR(100),
    enabled TINYINT(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 7. Bảng User_Roles (N-N)
CREATE TABLE user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 8. Bảng Sach (Bài 13)
CREATE TABLE sach (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_sach VARCHAR(20) NOT NULL UNIQUE,
    ten_sach VARCHAR(150) NOT NULL,
    tac_gia VARCHAR(100),
    gia DOUBLE DEFAULT 0,
    so_luong INT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 9. Bảng SanPham (Bài 13)
CREATE TABLE san_pham (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_sp VARCHAR(20) NOT NULL UNIQUE,
    ten_sp VARCHAR(150) NOT NULL,
    loai_sp VARCHAR(50),
    gia DOUBLE DEFAULT 0,
    so_luong INT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================================
-- SEED DATA INITIALIZATION
-- ========================================================

-- Seed Lớp học
INSERT INTO lop_hoc (ma_lop, ten_lop, khoa_hoc) VALUES
('CNTT14-01', 'Công nghệ thông tin 1', 'K14'),
('CNTT14-02', 'Công nghệ thông tin 2', 'K14'),
('KHMT14-01', 'Khoa học máy tính 1', 'K14');

-- Seed Sinh viên
INSERT INTO sinh_vien (ma_sinh_vien, ho_ten, email, ngay_sinh, lop_id) VALUES
('SV2030022', 'Trần Đức Hoàn', 'hoan.td@eaut.edu.vn', '2002-05-15', 1),
('SV2030001', 'Nguyễn Văn Anh', 'anh.nv@eaut.edu.vn', '2002-01-10', 1),
('SV2030002', 'Lê Thị Bình', 'binh.lt@eaut.edu.vn', '2002-03-22', 1),
('SV2030003', 'Phạm Minh Cường', 'cuong.pm@eaut.edu.vn', '2002-07-08', 2),
('SV2030004', 'Hoàng Thu Duyên', 'duyen.ht@eaut.edu.vn', '2002-11-30', 2),
('SV2030005', 'Đỗ Thanh Giang', 'giang.dt@eaut.edu.vn', '2002-09-19', 3);

-- Seed Môn học
INSERT INTO mon_hoc (ma_mon, ten_mon, so_tin_chi) VALUES
('IT3242', 'Công nghệ Java', 3),
('IT3110', 'Cơ sở dữ liệu', 3),
('IT3200', 'Lập trình Web', 3),
('IT3150', 'Cấu trúc dữ liệu và giải thuật', 4);

-- Seed Điểm
INSERT INTO diem (sinh_vien_id, mon_hoc_id, diem_qua_trinh, diem_thi, diem_tong_ket, xep_loai) VALUES
(1, 1, 9.0, 9.5, 9.35, 'Xuất sắc'),
(1, 2, 8.5, 9.0, 8.85, 'Giỏi'),
(2, 1, 7.0, 8.0, 7.7, 'Khá'),
(3, 1, 8.0, 8.5, 8.35, 'Giỏi');

-- Seed Roles & Users cho Lab 10
INSERT INTO roles (name, description) VALUES
('ROLE_ADMIN', 'Quản trị viên hệ thống'),
('ROLE_USER', 'Người dùng thông thường');

INSERT INTO users (username, password, email, full_name, enabled) VALUES
('admin', 'admin123', 'admin@eaut.edu.vn', 'Quản trị viên System', 1),
('hoan2030022', 'user123', 'hoan.td@eaut.edu.vn', 'Trần Đức Hoàn', 1);

INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1),
(1, 2),
(2, 2);

-- Seed Sách
INSERT INTO sach (ma_sach, ten_sach, tac_gia, gia, so_luong) VALUES
('S001', 'Lập Trình Java Căn Bản', 'Nguyễn Văn Hùng', 120000, 50),
('S002', 'Jakarta EE Web Development', 'Trần Đức Hoàn', 185000, 30),
('S003', 'Thiết Kế CSDL Với MySQL', 'Lê Anh Tuấn', 95000, 45);

-- Seed Sản phẩm
INSERT INTO san_pham (ma_sp, ten_sp, loai_sp, gia, so_luong) VALUES
('SP001', 'Laptop Dell XPS 15', 'Điện tử', 35000000, 10),
('SP002', 'Bàn phím cơ Keychron K2', 'Phụ kiện', 1850000, 25),
('SP003', 'Chuột không dây Logitech MX Master 3S', 'Phụ kiện', 2200000, 15);
