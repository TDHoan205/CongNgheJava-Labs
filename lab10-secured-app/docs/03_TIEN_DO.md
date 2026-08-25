# LAB 10 - TIẾN ĐỘ

> Quy tắc: Chỉ đánh dấu `[x]` sau khi task đã được kiểm tra và PASS.

---

# PHASE 0 - AUDIT

- [ ] 0.1 Audit project
- [ ] 0.2 Xác định technology stack
- [ ] 0.3 Xác định database
- [ ] 0.4 Xác định module Lab 6-9
- [ ] 0.5 Xác định phần Lab 10 còn thiếu

# PHASE 1 - DATABASE + 3 MODULES ✅

- [x] 1.1 JDK 21 + Maven check
- [x] 1.2 Database MySQL setup
- [x] 1.3 SQL script (CREATE + INSERT)
- [x] 1.4 JPA Entities (SinhVien, SanPham, Sach)
- [x] 1.5 JPA Repositories
- [x] 1.6 Services
- [x] 1.7 Controllers (Servlets)
- [x] 1.8 JSP Views
- [x] 1.9 Validation
- [x] 1.10 Transaction
- [x] 1.11 Build & Test
- [x] 1.12 Dashboard

# PHASE 2 - USER + ROLE

- [x] 2.1 Role
- [x] 2.2 User Entity
- [x] 2.3 Database
- [x] 2.4 UserRepository
- [x] 2.5 Sample accounts

# PHASE 3 - LOGIN / LOGOUT / SESSION

- [x] 3.1 Login JSP
- [x] 3.2 AuthService
- [x] 3.3 AuthController
- [x] 3.4 Session
- [x] 3.5 Logout

# PHASE 4 - SECURITY

- [x] 4.1 AuthenticationFilter
- [x] 4.2 AuthorizationFilter
- [x] 4.3 403
- [x] 4.4 Security test
- [x] 4.5 404
- [x] 4.6 500

# PHASE 5 - USER MANAGEMENT

- [x] 5.1 User list
- [x] 5.2 Create user
- [x] 5.3 Update user
- [x] 5.4 Change role
- [x] 5.5 Active/inactive
- [x] 5.6 Search email
- [x] 5.7 Validation

# PHASE 6 - PROFILE + PASSWORD

- [x] 6.1 Profile
- [x] 6.2 Update profile
- [x] 6.3 Change password
- [x] 6.4 Transaction (BaseRepository.update uses EntityTransaction)
- [x] 6.5 Validation

# PHASE 2 - LOGIN / SESSION / SECURITY ✅

- [x] 2.1 Login với database thật
- [x] 2.2 Logout invalidate session
- [x] 2.3 HttpSession (UserSession lưu id, email, fullName, role)
- [x] 2.4 AuthenticationFilter - bảo vệ /admin/*, /staff/*, /user/*
- [x] 2.5 AuthorizationFilter - phân quyền theo role
- [x] 2.6 PUBLIC paths - /sinh-vien, /san-pham, /sach không bị chặn
- [x] 2.7 login.jsp - xóa error message sau khi hiển thị
- [x] 2.8 header.jsp - menu theo role
- [x] 2.9 Sample accounts (admin/staff/user)

# PHASE 7 - ERROR + LOG + UI

- [x] 7.1 403
- [x] 7.2 404
- [x] 7.3 500
- [x] 7.4 Logging
- [x] 7.5 Header/Footer
- [x] 7.6 Menu theo role
- [x] 7.7 Success/Error message

# PHASE 8 - BUSINESS MODULES

- [ ] 8.1 Chọn module 1
- [ ] 8.2 Kiểm tra module 1
- [ ] 8.3 Chọn module 2
- [ ] 8.4 Kiểm tra module 2
- [ ] 8.5 Chọn module 3
- [ ] 8.6 Kiểm tra module 3
- [ ] 8.7 Authorization
- [ ] 8.8 Regression test

# PHASE 9 - FULL TEST

- [ ] Authentication test
- [ ] Authorization test
- [ ] CRUD test
- [ ] Validation test
- [ ] Error test
- [ ] Session test
- [ ] Database test

# PHASE 10 - SUBMISSION

- [ ] Database script
- [ ] Screenshots
- [ ] Report
- [ ] README
- [ ] Clean source
- [ ] ZIP
- [ ] Final verification

---

# GHI CHÚ

## Lỗi đang gặp

-

## Quyết định thiết kế

- Task 2.4 pom.xml: thêm JPA/Hibernate/MySQL/H2/JUnit5 (pom cũ trống)
- Test files cũ dùng JUnit 3 → migrate lên JUnit 5
- `lab10PU`: hibernate.hbm2ddl.auto=validate (DB đã tạo ở Task 2.3)
- `lab10TestPU`: H2 in-memory, hbm2ddl.auto=create-drop (cho unit test)

## Việc cần làm tiếp theo

-
