# Phase 5 — User Management ✅ PASS

**Ngày:** 2026-08-25

## Tasks

| # | Task | Status |
|---|------|--------|
| 5.1 | User List | ✅ |
| 5.2 | Create User | ✅ |
| 5.3 | Update User | ✅ |
| 5.4 | Change Role | ✅ |
| 5.5 | Active/Inactive | ✅ |
| 5.6 | Search Email | ✅ |
| 5.7 | Validation | ✅ |

## URLs

| Method | URL | Mô tả |
|--------|-----|-------|
| GET | `/admin/users` | Danh sách tài khoản |
| GET | `/admin/users/new` | Form tạo tài khoản |
| POST | `/admin/users/create` | Tạo tài khoản |
| GET | `/admin/users/edit?id=` | Form sửa tài khoản |
| POST | `/admin/users/update` | Cập nhật tài khoản |
| POST | `/admin/users/toggle?id=` | Toggle active/inactive |

## Validation (Task 5.7)

### Create User
- Email: bắt buộc, format hợp lệ
- Password: bắt buộc, ít nhất 6 ký tự
- Họ tên: bắt buộc, 2-100 ký tự
- Vai trò: bắt buộc (ADMIN/STAFF/USER)
- Email không được trùng

### Update User
- Email: bắt buộc, format hợp lệ
- Họ tên: bắt buộc, 2-100 ký tự
- Vai trò: bắt buộc
- Email không được trùng với tài khoản khác

## Các file đã tạo

| File | Thay đổi |
|------|----------|
| `service/UserService.java` | Tạo mới — business logic |
| `controller/UserServlet.java` | Tạo mới — servlet xử lý CRUD |
| `repository/UserRepository.java` | Mở rộng — search, existsByEmail |
| `webapp/WEB-INF/views/admin/users/list.jsp` | Tạo mới |
| `webapp/WEB-INF/views/admin/users/form.jsp` | Tạo mới |

## Test cases

| # | Test | Expected |
|---|------|----------|
| 1 | ADMIN → `/admin/users` | ✅ Hiển thị danh sách |
| 2 | STAFF → `/admin/users` | ❌ 403 |
| 3 | USER → `/admin/users` | ❌ 403 |
| 4 | Tạo user trùng email | ❌ Báo lỗi |
| 5 | Tạo user thiếu email | ❌ Báo lỗi validation |
| 6 | Toggle active/inactive | ✅ Đổi trạng thái |
| 7 | Search email | ✅ Lọc đúng |
