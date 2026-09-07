-- ====================================================================
-- CƠ SỞ DỮ LIỆU LAB 15: DỰ ÁN TỔNG HỢP SPRING FRAMEWORK (MYSQL)
-- Sinh viên: Trần Đức Hoàn - MSSV: 2030022 - Lớp: DCCNTT14.10.1
-- ====================================================================

-- 1. Tạo Database
CREATE DATABASE IF NOT EXISTS lab15_jpa CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE lab15_jpa;

-- 2. Tạo Bảng students (Quản lý Sinh viên)
CREATE TABLE IF NOT EXISTS students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_code VARCHAR(50) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    class_name VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. Tạo Bảng courses (Quản lý Môn học)
CREATE TABLE IF NOT EXISTS courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_code VARCHAR(50) NOT NULL UNIQUE,
    course_name VARCHAR(150) NOT NULL,
    credits INT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. Tạo Bảng enrollments (Đăng ký học phần - Quan hệ N-N)
CREATE TABLE IF NOT EXISTS enrollments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    enroll_date DATE NOT NULL,
    CONSTRAINT fk_enrollment_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    CONSTRAINT fk_enrollment_course FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    CONSTRAINT uq_student_course UNIQUE (student_id, course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5. Dữ liệu mẫu khởi tạo (Seed Data)
INSERT INTO students (student_code, full_name, email, class_name) VALUES
('SV001', 'Nguyễn Văn An', 'an@eaut.edu.vn', 'DCCNTT13.10.1'),
('SV002', 'Trần Thị Bình', 'binh@eaut.edu.vn', 'DCCNTT13.10.2'),
('SV003', 'Lê Văn Cường', 'cuong@eaut.edu.vn', 'DCCNTT13.10.3'),
('SV2030022', 'Trần Đức Hoàn', 'hoan.td@eaut.edu.vn', 'DCCNTT14.10.1')
ON DUPLICATE KEY UPDATE full_name=VALUES(full_name);

INSERT INTO courses (course_code, course_name, credits) VALUES
('IT3242', 'Công nghệ Java', 3),
('IT3110', 'Cơ sở dữ liệu', 3),
('IT3200', 'Lập trình Web & Frameworks', 3),
('IT3150', 'Cấu trúc dữ liệu và giải thuật', 4),
('IT4100', 'Phát triển ứng dụng với Spring Framework', 3)
ON DUPLICATE KEY UPDATE course_name=VALUES(course_name);

INSERT INTO enrollments (student_id, course_id, enroll_date) VALUES
(4, 1, '2026-09-04'),
(4, 5, '2026-09-05'),
(1, 1, '2026-09-06'),
(2, 2, '2026-09-07')
ON DUPLICATE KEY UPDATE enroll_date=VALUES(enroll_date);
