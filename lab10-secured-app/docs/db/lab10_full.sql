-- =====================================================
-- LAB 10 - DATABASE SCRIPT - PHASE 1
-- Sinh viên: Trần Đức Hoàn - MSSV: 2030022
-- Trường: Đại học Công nghệ Đông Á
--
-- Scope: users + sinh_vien + san_pham + sach
-- Không bao gồm: Login, Filter, User CRUD (Phase 2+)
-- =====================================================

-- IMPORTANT: enforce UTF-8 for both client connection and result set
-- Run with: mysql --default-character-set=utf8mb4 -u root -p < lab10_full.sql
SET NAMES utf8mb4;

DROP DATABASE IF EXISTS lab10_db;
CREATE DATABASE lab10_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE lab10_db;

-- -----------------------------------------------------
-- Bảng users
-- -----------------------------------------------------
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    email       VARCHAR(100) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    full_name   VARCHAR(100) NOT NULL,
    role        ENUM('ADMIN','STAFF','USER') NOT NULL DEFAULT 'USER',
    active      TINYINT(1)   NOT NULL DEFAULT 1,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_users_email UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------
-- Bảng sinh_vien
-- -----------------------------------------------------
DROP TABLE IF EXISTS sinh_vien;
CREATE TABLE sinh_vien (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    ma_sinh_vien    VARCHAR(20) NOT NULL,
    ho_ten          VARCHAR(100) NOT NULL,
    email           VARCHAR(100),
    lop             VARCHAR(50),
    CONSTRAINT uk_sinhvien_ma UNIQUE (ma_sinh_vien)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------
-- Bảng san_pham
-- -----------------------------------------------------
DROP TABLE IF EXISTS san_pham;
CREATE TABLE san_pham (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    ma          VARCHAR(20) NOT NULL,
    ten         VARCHAR(100) NOT NULL,
    mo_ta       TEXT,
    gia         DECIMAL(12,2) NOT NULL DEFAULT 0,
    so_luong    INT NOT NULL DEFAULT 0,
    CONSTRAINT uk_sanpham_ma UNIQUE (ma)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------
-- Bảng sach
-- -----------------------------------------------------
DROP TABLE IF EXISTS sach;
CREATE TABLE sach (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    ma_sach         VARCHAR(20) NOT NULL,
    ten_sach        VARCHAR(200) NOT NULL,
    tac_gia         VARCHAR(100),
    nha_xuat_ban    VARCHAR(100),
    nam_xuat_ban    INT,
    CONSTRAINT uk_sach_ma UNIQUE (ma_sach)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------
-- Sample accounts
-- -----------------------------------------------------
INSERT INTO users (email, password, full_name, role, active) VALUES
('admin@test.com', 'admin123', 'Quản trị viên', 'ADMIN', 1),
('staff@test.com', 'staff123', 'Nhân viên', 'STAFF', 1),
('user@test.com', 'user123', 'Người dùng', 'USER', 1),
('inactive@test.com', 'inactive123', 'Tài khoản bị khóa', 'USER', 0);

-- -----------------------------------------------------
-- Sample data: SinhVien
-- -----------------------------------------------------
INSERT INTO sinh_vien (ma_sinh_vien, ho_ten, email, lop) VALUES
('20240001', 'Nguyễn Văn An', 'an@gmail.com', 'DCCNTT15.10.1'),
('20240002', 'Trần Thị Bình', 'binh@gmail.com', 'DCCNTT15.10.2'),
('20240003', 'Lê Văn Cường', 'cuong@gmail.com', 'DCCNTT15.10.1'),
('20240004', 'Phạm Thị Dung', 'dung@gmail.com', 'DCCNTT15.10.2'),
('20240005', 'Hoàng Văn Em', 'em@gmail.com', 'DCCNTT15.10.1'),
('20240006', 'Vũ Thị Giang', 'giang@gmail.com', 'DCCNTT15.10.3'),
('20240007', 'Đỗ Văn Hoa', 'hoa@gmail.com', 'DCCNTT15.10.3');

-- -----------------------------------------------------
-- Sample data: SanPham
-- -----------------------------------------------------
INSERT INTO san_pham (ma, ten, mo_ta, gia, so_luong) VALUES
('SP001', 'Laptop Dell XPS 15', 'Laptop cao cấp, i7, 16GB RAM, 512GB SSD', 28990000, 15),
('SP002', 'iPhone 15 Pro', 'Điện thoại Apple A17 Pro, 256GB', 32990000, 20),
('SP003', 'Samsung Galaxy S24', 'Điện thoại Samsung Snapdragon 8 Gen 3', 24990000, 25),
('SP004', 'Tai nghe Sony WH-1000XM5', 'Tai nghe chống ồn cao cấp', 7990000, 30),
('SP005', 'Bàn phím Keychron K8', 'Bàn phím cơ wireless, layout TKL', 2890000, 50),
('SP006', 'Chuột Logitech MX Master 3', 'Chuột không dây cao cấp', 3490000, 40),
('SP007', 'Monitor LG 27UK850', 'Màn hình 27 inch 4K IPS', 14990000, 12),
('SP008', 'USB Kingston 64GB', 'USB 3.2, tốc độ cao', 290000, 100);

-- -----------------------------------------------------
-- Sample data: Sach
-- -----------------------------------------------------
INSERT INTO sach (ma_sach, ten_sach, tac_gia, nha_xuat_ban, nam_xuat_ban) VALUES
('BK001', 'Lập trình Java cơ bản', 'Nguyễn Thanh Tuấn', 'Nhà xuất bản Giáo dục', 2023),
('BK002', 'Cấu trúc dữ liệu và giải thuật', 'Trần Minh Hoàng', 'Nhà xuất bản Khoa học', 2022),
('BK003', 'Lập trình hướng đối tượng với Java', 'Phạm Văn Hùng', 'Nhà xuất bản Đại học Quốc gia', 2024),
('BK004', 'SQL cơ bản và nâng cao', 'Lê Thị Lan', 'Nhà xuất bản Thống kê', 2023),
('BK005', 'Clean Code - Thực hành', 'Robert C. Martin', 'Prentice Hall', 2021),
('BK006', 'Design Patterns', 'Gang of Four', 'Addison-Wesley', 2020),
('BK007', 'Spring Boot in Action', 'Craig Walls', 'Manning Publications', 2022),
('BK008', 'Microservices Architecture', 'Sam Newman', 'O''Reilly Media', 2023);

-- -----------------------------------------------------
-- Verify data
-- -----------------------------------------------------
SELECT 'USERS' as tbl, COUNT(*) as cnt FROM users
UNION ALL
SELECT 'SINH_VIEN', COUNT(*) FROM sinh_vien
UNION ALL
SELECT 'SAN_PHAM', COUNT(*) FROM san_pham
UNION ALL
SELECT 'SACH', COUNT(*) FROM sach;
