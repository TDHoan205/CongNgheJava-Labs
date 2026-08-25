# Phase 6 — Profile + Password ✅ PASS

**Ngày:** 2026-08-25

## Tasks

| # | Task | Status |
|---|------|--------|
| 6.1 | Profile view/update | ✅ |
| 6.3 | Change Password | ✅ |

---

## Task 6.1 - Profile

### URLs

| Method | URL | Mô tả |
|--------|-----|-------|
| GET | `/user/profile` | Xem thông tin cá nhân |
| POST | `/user/profile` | Cập nhật họ tên |

### Luồng xử lý

```
GET /user/profile
    │
    ▼
ProfileServlet.doGet()
    │
    │  Lấy currentUser từ session
    │
    ▼
UserService.findById(currentUser.getUserId())
    │
    ▼
forward → profile.jsp
```

### Security

- User chỉ xem/sửa được profile của mình (lấy từ session, không có param)
- Không thể xem profile user khác
- Filter bảo vệ: AuthenticationFilter + AuthorizationFilter

---

## Task 6.3 - Change Password

### Form

| Field | Validation |
|-------|------------|
| currentPassword | Bắt buộc, đúng với DB |
| newPassword | Bắt buộc, ít nhất 6 ký tự |
| confirmPassword | Phải giống newPassword |

### URLs

| Method | URL | Mô tả |
|--------|-----|-------|
| GET | `/user/change-password` | Form đổi mật khẩu |
| POST | `/user/change-password` | Xử lý đổi mật khẩu |

### AuthService.changePassword()

```java
public String changePassword(Integer userId, String currentPassword,
                            String newPassword, String confirmPassword) {
    // 1. Validate currentPassword not empty
    // 2. Validate newPassword not empty, >= 6 chars
    // 3. Validate confirmPassword == newPassword
    // 4. Find user by ID
    // 5. Check currentPassword matches DB
    // 6. Update password in transaction
    // 7. Return null on success, error message on failure
}
```

### Transaction

User password update dùng `userRepository.update()` đã có transaction trong `BaseRepository.update()`.

### Security

- Password không lưu trong session
- User chỉ đổi được password của mình (từ session)

---

## Các file đã tạo

| File | Thay đổi |
|------|----------|
| `controller/ProfileServlet.java` | Tạo mới |
| `controller/ChangePasswordServlet.java` | Tạo mới |
| `service/AuthService.java` | Thêm changePassword() |
| `service/UserService.java` | Thêm updateProfile() |
| `model/UserSession.java` | Thêm setFullName(), đổi fullName thành mutable |
| `webapp/WEB-INF/views/user/profile.jsp` | Tạo mới |
| `webapp/WEB-INF/views/user/change-password.jsp` | Tạo mới |

---

## Test cases

### Profile (6.1)

| # | Test | Expected |
|---|------|----------|
| 1 | USER → `/user/profile` | ✅ Xem thông tin |
| 2 | STAFF → `/user/profile` | ✅ Xem thông tin |
| 3 | USER sửa họ tên | ✅ Cập nhật thành công |
| 4 | USER sửa họ tên rỗng | ❌ Validation error |

### Change Password (6.3)

| # | Test | Expected |
|---|------|----------|
| 1 | Đổi password đúng | ✅ Thành công |
| 2 | Sai password cũ | ❌ "Mật khẩu hiện tại không đúng" |
| 3 | Password mới < 6 ký tự | ❌ "Mật khẩu mới phải có ít nhất 6 ký tự" |
| 4 | Confirm khác password mới | ❌ "Xác nhận mật khẩu mới không đúng" |
