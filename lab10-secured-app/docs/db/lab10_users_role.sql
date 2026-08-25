-- =====================================================
-- LAB 10 - DATABASE SCRIPT FOR USER + ROLE (TASK 2.3)
-- Học phần: Công nghệ Java (IT3242)
-- Sinh viên: Trần Đức Hoàn - MSSV: 2030022
-- Trường: Đại học Công nghệ Đông Á
--
-- Scope: chỉ bảng `users` + role.
-- Không bao gồm: Login, Filter, User CRUD, module nghiệp vụ.
-- Sample accounts (ADMIN/STAFF/USER) thuộc Task 2.5.
--
-- Yêu cầu MySQL 8.x, InnoDB, utf8mb4.
-- Chạy từ database sạch: script DROP + CREATE lại DB.
-- =====================================================

DROP DATABASE IF EXISTS lab10_db;
CREATE DATABASE lab10_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE lab10_db;

-- -----------------------------------------------------
-- Bảng users
-- PK: id (IDENTITY)
-- UNIQUE: email
-- role: enum chính xác 3 giá trị của vn.edu.eaut.lab10.model.Role
-- active: TINYINT(1), mặc định 1
-- -----------------------------------------------------
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    email       VARCHAR(100) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    full_name   VARCHAR(100) NOT NULL,
    role        ENUM('ADMIN','STAFF','USER') NOT NULL,
    active      TINYINT(1)   NOT NULL DEFAULT 1,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
                              ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_users_email UNIQUE (email),
    CONSTRAINT ck_users_active CHECK (active IN (0,1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------
-- Index phụ để search theo email (Task 5.6 dùng).
-- Đã có UNIQUE(email) → không cần index thêm.
-- -----------------------------------------------------
