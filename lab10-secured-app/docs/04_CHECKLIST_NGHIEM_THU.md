# LAB 10 — CHECKLIST NGHIỆM THU CUỐI

**Sinh viên:** Trần Đức Hoàn — MSSV: 2030022
**Ngày:** 2026-08-25
**Project:** lab10-secured-app

---

## HƯỚNG DẪN CHẠY

### 1. Tạo Database

```bash
mysql -u root -p < docs/db/lab10_full.sql
```

### 2. Deploy WAR lên Tomcat

```bash
# WAR file: target/lab10-secured-app.war
# Deploy vào Tomcat 10.x
```

### 3. Truy cập

- App: `http://localhost:8080/lab10-secured-app/`
- Login: `http://localhost:8080/lab10-secured-app/auth`

### 4. Tài khoản mẫu

| Email | Password | Role |
|-------|----------|------|
| admin@test.com | admin123 | ADMIN |
| staff@test.com | staff123 | STAFF |
| user@test.com | user123 | USER |
| inactive@test.com | inactive123 | USER (bị khóa) |

---

## CHECKLIST ẢNH CẦN CHỤP

### NHÓM 1 — AUTHENTICATION

- [ ] **IMG-01:** Login page (`/auth`)
- [ ] **IMG-02:** Login thành công với ADMIN → Dashboard
- [ ] **IMG-03:** Login thành công với STAFF → Dashboard
- [ ] **IMG-04:** Login thành công với USER → Dashboard
- [ ] **IMG-05:** Login sai password → Error message
- [ ] **IMG-06:** Login sai email → Error message
- [ ] **IMG-07:** Login tài khoản inactive → Error message
- [ ] **IMG-08:** Logout → Quay về login page

### NHÓM 2 — AUTHORIZATION

- [ ] **IMG-09:** Header hiển thị đúng theo role (ADMIN có menu "Quản lý User")
- [ ] **IMG-10:** GUEST truy cập `/admin/users` → Redirect về login
- [ ] **IMG-11:** USER truy cập `/admin/users` → 403 page
- [ ] **IMG-12:** STAFF truy cập `/admin/users` → 403 page
- [ ] **IMG-13:** ADMIN truy cập `/admin/users` → User list

### NHÓM 3 — USER MANAGEMENT (ADMIN)

- [ ] **IMG-14:** User list — danh sách tài khoản
- [ ] **IMG-15:** Tạo user mới — form
- [ ] **IMG-16:** Tạo user trùng email → Validation error
- [ ] **IMG-17:** Tạo user thành công → Danh sách cập nhật
- [ ] **IMG-18:** Sửa user — đổi role
- [ ] **IMG-19:** Toggle active/inactive
- [ ] **IMG-20:** Search user theo email

### NHÓM 4 — PROFILE & PASSWORD

- [ ] **IMG-21:** Profile page hiển thị thông tin
- [ ] **IMG-22:** Cập nhật họ tên thành công
- [ ] **IMG-23:** Cập nhật họ tên rỗng → Validation error
- [ ] **IMG-24:** Change password thành công
- [ ] **IMG-25:** Đổi password sai → Validation error

### NHÓM 5 — 3 MODULE NGHIỆP VỤ

- [ ] **IMG-26:** Dashboard với 3 module cards
- [ ] **IMG-27:** SinhVien list — dữ liệu từ database
- [ ] **IMG-28:** SinhVien — Thêm mới thành công
- [ ] **IMG-29:** SinhVien — Validation rỗng
- [ ] **IMG-30:** SinhVien — Sửa thành công
- [ ] **IMG-31:** SinhVien — Xóa thành công
- [ ] **IMG-32:** SanPham list — dữ liệu từ database
- [ ] **IMG-33:** SanPham — Thêm mới thành công
- [ ] **IMG-34:** SanPham — Validation giá âm
- [ ] **IMG-35:** Sach list — dữ liệu từ database
- [ ] **IMG-36:** Sach — Thêm mới thành công

### NHÓM 6 — ERROR PAGES

- [ ] **IMG-37:** 403 page
- [ ] **IMG-38:** 404 page (truy cập URL không tồn tại)
- [ ] **IMG-39:** 500 page (trigger exception)

### NHÓM 7 — UI/UX

- [ ] **IMG-40:** Login page — giao diện đẹp
- [ ] **IMG-41:** Dashboard — header, cards
- [ ] **IMG-42:** Flash message success (màu xanh)
- [ ] **IMG-43:** Flash message error (màu đỏ)
- [ ] **IMG-44:** Table — style đồng nhất
- [ ] **IMG-45:** Form — style đồng nhất
- [ ] **IMG-46:** Footer

### NHÓM 8 — DATABASE & LOGGING

- [ ] **IMG-47:** MySQL — SELECT * FROM users
- [ ] **IMG-48:** MySQL — SELECT * FROM sinh_vien
- [ ] **IMG-49:** Log file sau login/logout (WEB-INF/logs/app.log)

---

## BÁO CÁO CUỐI CÙNG

### TÓM TẮT

| # | Tiêu chí | Trạng thái | Ảnh |
|---|----------|-------------|------|
| 1 | Project chạy | ☐ | — |
| 2 | Database | ☐ | IMG-47..48 |
| 3 | 3 Module CRUD | ☐ | IMG-26..36 |
| 4 | Login | ☐ | IMG-02..07 |
| 5 | Logout | ☐ | IMG-08 |
| 6 | Session | ☐ | IMG-02 |
| 7 | Role | ☐ | IMG-09..13 |
| 8 | Filter | ☐ | IMG-10..13 |
| 9 | 403 | ☐ | IMG-11..12, 37 |
| 10 | 404 | ☐ | IMG-38 |
| 11 | 500 | ☐ | IMG-39 |
| 12 | User Management | ☐ | IMG-14..20 |
| 13 | Profile | ☐ | IMG-21..23 |
| 14 | Change Password | ☐ | IMG-24..25 |
| 15 | Validation | ☐ | IMG-16, 23, 25, 29, 34 |
| 16 | Message | ☐ | IMG-42..43 |
| 17 | Transaction | ☐ | Code review |
| 18 | Logging | ☐ | IMG-49 |
| 19 | UI | ☐ | IMG-40..46 |
| 20 | SQL Script | ☐ | docs/db/lab10_full.sql |
| 21 | Build | ☐ | BUILD SUCCESS |
| 22 | WAR Deploy | ☐ | target/lab10-secured-app.war |

---

## LỖI CẦN LƯU Ý

1. **MySQL connection:** Kiểm tra `persistence.xml` — user `root`, password `123456`
2. **Chạy SQL script** trước khi deploy để tạo database + dữ liệu mẫu
3. **Tomcat 10.x** — Jakarta EE 9+
4. **WAR file:** `target/lab10-secured-app.war`

---

## FILE CẦN NỘP

1. Source code (ZIP)
2. `docs/db/lab10_full.sql` — Database script
3. Báo cáo (DOCX/PDF) — có ảnh minh chứng
4. Ảnh chụp màn hình (theo checklist ảnh)
