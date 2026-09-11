# Lab 16 - Multi-Platform Order Processing System

## Cấu trúc project

```
java-integrated-lab/
├── database/
│   ├── schema.sql          # MySQL schema (5 tables)
│   ├── sample-data.sql     # Seed: 4 users + 5 products
│   ├── reset-test-data.sql # Reset script
│   └── fix-hashes.sql     # BCrypt hash fix
├── customer-portal-jakarta/    # Jakarta EE (port 8081, context /customer-portal)
├── warehouse-desktop-swing/     # Java Swing Desktop
├── management-portal-spring/     # Spring Boot (port 8082)
├── run-customer.bat
├── run-management.bat
└── README.md
```

## Cơ sở dữ liệu

**MySQL**: `java_integrated_lab` (utf8mb4)

**Tables**: users, products, orders, order_items, order_status_history

**Tài khoản test** (password: `123456`):

| Username | Role | Ứng dụng |
|----------|------|-----------|
| customer01 | CUSTOMER | Customer Portal |
| warehouse01 | WAREHOUSE | Warehouse Desktop |
| manager01 | MANAGER | Management Portal |
| admin01 | ADMIN | Management Portal |

## Cách chạy

### 1. Khởi động MySQL và tạo DB
```bash
mysql -u root -p123456 < database/schema.sql
mysql -u root -p123456 < database/sample-data.sql
mysql -u root -p123456 < database/fix-hashes.sql
```

### 2. Customer Portal (Jakarta EE)
```bash
cd customer-portal-jakarta
mvn jetty:run
# Truy cập: http://localhost:8081/customer-portal
```

### 3. Warehouse Desktop (Java Swing)
```bash
cd warehouse-desktop-swing
java -jar target/warehouse-desktop-swing-1.0-SNAPSHOT.jar
```

### 4. Management Portal (Spring Boot)
```bash
cd management-portal-spring
mvn spring-boot:run
# Truy cập: http://localhost:8082/login
```

## Luồng nghiệp vụ

```
CUSTOMER (Jakarta EE)
  -> Tạo đơn PENDING
       -> Hiển thị trên Warehouse Desktop

WAREHOUSE (Swing Desktop)
  -> Tiếp nhận: PENDING -> PROCESSING
       -> Trừ tồn kho
  -> Đóng gói: PROCESSING -> READY
       -> Hiển thị trên Management Portal

MANAGER (Spring Boot)
  -> Phê duyệt: READY -> SHIPPING
       -> Hiển thị trên Customer Portal

CUSTOMER (Jakarta EE)
  -> Xác nhận: SHIPPING -> COMPLETED
```

## 10 điều kiện đạt Lab 16

| # | Mô tả | Kết quả |
|---|--------|---------|
| TC01 | Khách tạo đơn trên Jakarta EE | **PASS** |
| TC02 | Đơn xuất hiện trên Swing | **PASS** |
| TC03 | Kho tiếp nhận + trừ tồn kho | **PASS** |
| TC04 | Đóng gói: PROCESSING -> READY | **PASS** |
| TC05 | Manager thấy đơn READY | **PASS** |
| TC06 | Manager phê duyệt: SHIPPING | **PASS** |
| TC07 | Khách thấy SHIPPING | **PASS** |
| TC08 | Khách xác nhận COMPLETED | **PASS** |
| TC09 | Tồn kho âm bị rollback | **PASS** |
| TC10 | Xung đột dữ liệu | **PASS** (optimistic lock) |
| TC11 | Sai quyền truy cập | **PASS** |
| TC12 | Lịch sử đầy đủ | **PASS** |

## Lỗi đã sửa

1. **BCrypt hash** - Hash cũ trong seed data không verify đúng. Tạo hash mới và cập nhật vào DB.
2. **JSP formatDate** - `<fmt:formatDate>` không nhận LocalDateTime. Đổi sang hiển thị string trực tiếp.
3. **OrderServlet doPost** - Không xử lý query params (action, id) khi pathInfo null. Thêm logic xử lý cả 2 cách.

## Kiến trúc kỹ thuật

| App | Công nghệ | ORM | Transaction |
|-----|-----------|-----|------------|
| Customer Portal | Jakarta EE 10, Servlet/JSP, Jetty 11 | JPA/Hibernate | EntityTransaction |
| Warehouse Desktop | Java Swing 17, JDBC | DAO pattern | setAutoCommit(false) |
| Management Portal | Spring Boot 3.2.3, Spring MVC, Thymeleaf | Spring Data JPA | @Transactional |
