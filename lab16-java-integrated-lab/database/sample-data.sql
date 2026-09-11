-- ====================================================================
-- LAB 16: Sample Data
-- Password '123456' hashed with BCrypt: $2a$10$nUoxpwPtksrSgwyQmgv8EeSx06Flgz3mehvplnCyoG9EudmqXOl2m
-- All users have password '123456'
-- ====================================================================

USE java_integrated_lab;

-- Users
INSERT INTO users (username, password_hash, full_name, role) VALUES
('customer01', '$2a$10$nUoxpwPtksrSgwyQmgv8EeSx06Flgz3mehvplnCyoG9EudmqXOl2m', 'Nguyen Van Khach', 'CUSTOMER'),
('warehouse01', '$2a$10$nUoxpwPtksrSgwyQmgv8EeSx06Flgz3mehvplnCyoG9EudmqXOl2m', 'Tran Van Kho', 'WAREHOUSE'),
('manager01', '$2a$10$nUoxpwPtksrSgwyQmgv8EeSx06Flgz3mehvplnCyoG9EudmqXOl2m', 'Le Van Quan Ly', 'MANAGER'),
('admin01', '$2a$10$nUoxpwPtksrSgwyQmgv8EeSx06Flgz3mehvplnCyoG9EudmqXOl2m', 'Quan tri he thong', 'ADMIN');

-- Products
INSERT INTO products (code, name, price, stock) VALUES
('SP001', 'Ban phim co', 850000.00, 20),
('SP002', 'Chuot khong day', 350000.00, 30),
('SP003', 'Tai nghe Bluetooth', 650000.00, 15),
('SP004', 'Webcam Full HD', 720000.00, 10),
('SP005', 'O cung SSD 1TB', 1850000.00, 8);
