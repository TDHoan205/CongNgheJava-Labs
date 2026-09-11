-- ====================================================================
-- Test script for Lab 16 TC01-TC12
-- Tests order status transitions and history
-- ====================================================================

USE java_integrated_lab;

-- Reset data
SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE order_status_history;
TRUNCATE TABLE order_items;
TRUNCATE TABLE orders;
TRUNCATE TABLE products;
TRUNCATE TABLE users;
SET FOREIGN_KEY_CHECKS=1;

-- Re-seed
INSERT INTO users (username, password_hash, full_name, role) VALUES
('customer01', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjlV9.7E8GqKzS5sF0RT4o5xQ8uC.', 'Nguyen Van Khach', 'CUSTOMER'),
('warehouse01', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjlV9.7E8GqKzS5sF0RT4o5xQ8uC.', 'Tran Van Kho', 'WAREHOUSE'),
('manager01', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjlV9.7E8GqKzS5sF0RT4o5xQ8uC.', 'Le Van Quan Ly', 'MANAGER'),
('admin01', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjlV9.7E8GqKzS5sF0RT4o5xQ8uC.', 'Quan tri he thong', 'ADMIN');

INSERT INTO products (code, name, price, stock) VALUES
('SP001', 'Ban phim co', 850000.00, 20),
('SP002', 'Chuot khong day', 350000.00, 30),
('SP003', 'Tai nghe Bluetooth', 650000.00, 15),
('SP004', 'Webcam Full HD', 720000.00, 10),
('SP005', 'O cung SSD 1TB', 1850000.00, 8);

SELECT 'Reset done' AS status;
SELECT 'Users' AS info, id, username, role FROM users;
SELECT 'Products' AS info, id, code, name, stock FROM products;
