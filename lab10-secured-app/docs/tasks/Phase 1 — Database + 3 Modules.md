# Phase 1 — Hoàn thiện nền tảng và dữ liệu ✅

**Ngày:** 2026-08-25

---

## Kết quả

| Tiêu chí | Trạng thái |
|-----------|------------|
| JDK 21 + Maven + Tomcat | ✅ PASS |
| Database (MySQL) | ✅ PASS |
| 3 Module nghiệp vụ | ✅ PASS |
| CRUD | ✅ PASS |
| Validation | ✅ PASS |
| Transaction | ✅ PASS |
| BUILD | ✅ PASS |

---

## 1. Kiểm tra môi trường

- Java: `21.0.12`
- Maven: `3.9.16`
- pom.xml: `hibernate 6.4.4`, `mysql-connector-j 8.0.33`, `jakarta.servlet 6.0.0`

---

## 2. Database

### SQL Script

**File:** `docs/db/lab10_full.sql`

### Bảng đã tạo

| Bảng | Mô tả |
|------|--------|
| `users` | Tài khoản (ADMIN/STAFF/USER) |
| `sinh_vien` | Sinh viên |
| `san_pham` | Sản phẩm |
| `sach` | Sách |

### Dữ liệu mẫu

| Bảng | Số dòng |
|------|----------|
| users | 4 (admin, staff, user, inactive) |
| sinh_vien | 7 |
| san_pham | 8 |
| sach | 8 |

---

## 3. JPA Entities

### SinhVien

```java
@Entity
@Table(name = "sinh_vien")
public class SinhVien {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(unique = true) String maSinhVien;
    String hoTen, email, lop;
}
```

### SanPham

```java
@Entity
@Table(name = "san_pham")
public class SanPham {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(unique = true) String ma;
    String ten, moTa;
    @Column(precision = 12, scale = 2) BigDecimal gia;
    Integer soLuong;
}
```

### Sach

```java
@Entity
@Table(name = "sach")
public class Sach {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(unique = true) String maSach;
    String tenSach, tacGia, nhaXuatBan;
    Integer namXuatBan;
}
```

---

## 4. Repositories

Kế thừa `BaseRepository`, có:
- `findAll()`, `findById()`, `save()`, `update()`, `delete()`
- `search(keyword)` — tìm kiếm theo nhiều trường
- `existsByMa()` — kiểm tra mã trùng

---

## 5. Services

### Validation

| Module | Validation |
|--------|-----------|
| SinhVien | maSinhVien + hoTen không rỗng |
| SanPham | ma + ten không rỗng, gia > 0, soLuong >= 0 |
| Sach | maSach + tenSach không rỗng, namXB > 0 |

### Transaction

Dùng `BaseRepository`:
- `save()`: `EntityTransaction.begin() → persist → commit`
- `update()`: `EntityTransaction.begin() → merge → commit`
- `delete()`: `EntityTransaction.begin() → remove → commit`
- Rollback nếu exception

---

## 6. Controllers (Servlets)

| Servlet | URL | CRUD |
|---------|-----|------|
| SinhVienServlet | `/sinh-vien` | List, New, Edit, Delete, Search |
| SanPhamServlet | `/san-pham` | List, New, Edit, Delete, Search |
| SachServlet | `/sach` | List, New, Edit, Delete, Search |

### Flash Message

```java
setFlash(req, "success", "Thêm thành công.");
resp.sendRedirect("/sinh-vien");
```

---

## 7. Views

| File | Mô tả |
|------|--------|
| `sinhvien/list.jsp` | Bảng sinh viên, search, phân trang |
| `sinhvien/form.jsp` | Form thêm/sửa sinh viên |
| `sanpham/list.jsp` | Bảng sản phẩm, search |
| `sanpham/form.jsp` | Form thêm/sửa sản phẩm |
| `sach/list.jsp` | Bảng sách, search |
| `sach/form.jsp` | Form thêm/sửa sách |

---

## 8. Build & Test

```
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

---

## 9. Hướng dẫn chạy

### Bước 1: Tạo database

```bash
mysql -u root -p < docs/db/lab10_full.sql
```

### Bước 2: Deploy lên Tomcat

```bash
mvn clean package
# Deploy target/lab10-secured-app.war lên Tomcat
```

### Bước 3: Truy cập

- Dashboard: `http://localhost:8080/lab10-secured-app/`
- Sinh viên: `http://localhost:8080/lab10-secured-app/sinh-vien`
- Sản phẩm: `http://localhost:8080/lab10-secured-app/san-pham`
- Sách: `http://localhost:8080/lab10-secured-app/sach`

---

## 10. Các file đã tạo

| # | File | Loại |
|---|------|------|
| 1 | `docs/db/lab10_full.sql` | SQL script |
| 2 | `model/SinhVien.java` | Entity |
| 3 | `model/SanPham.java` | Entity |
| 4 | `model/Sach.java` | Entity |
| 5 | `repository/SinhVienRepository.java` | Repository |
| 6 | `repository/SanPhamRepository.java` | Repository |
| 7 | `repository/SachRepository.java` | Repository |
| 8 | `service/SinhVienService.java` | Service |
| 9 | `service/SanPhamService.java` | Service |
| 10 | `service/SachService.java` | Service |
| 11 | `controller/SinhVienServlet.java` | Servlet |
| 12 | `controller/SanPhamServlet.java` | Servlet |
| 13 | `controller/SachServlet.java` | Servlet |
| 14 | `views/sinhvien/list.jsp` | View |
| 15 | `views/sinhvien/form.jsp` | View |
| 16 | `views/sanpham/list.jsp` | View |
| 17 | `views/sanpham/form.jsp` | View |
| 18 | `views/sach/list.jsp` | View |
| 19 | `views/sach/form.jsp` | View |
| 20 | `views/layout/header.jsp` | Layout |
| 21 | `index.jsp` | Dashboard |
| 22 | `resources/META-INF/persistence.xml` | Cập nhật |

---

## Lưu ý

Phase 1 KHÔNG bao gồm:
- Login / Authentication
- Role / Authorization
- Filter

Chỉ hoàn thiện CRUD cho 3 module với database thật.
