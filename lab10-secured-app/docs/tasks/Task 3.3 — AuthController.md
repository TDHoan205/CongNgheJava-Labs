# Task 3.3 — AuthController ✅ PASS

**Ngày:** 2026-08-25

## Endpoint `/auth`

```
GET  /auth → hiển thị login.jsp
POST /auth → xử lý login
```

## Luồng xử lý POST /auth

```
Browser POST /auth
    │
    ▼
AuthController.doPost()
    │
    │  1. Nhận email, password từ request
    │  2. authService.login(email, password)
    │
    ├─ login thất bại (user == null)
    │      │
    │      │  session.setAttribute("errorMessage", "Email hoặc mật khẩu không đúng.")
    │      ▼
    │  resp.sendRedirect("/auth")  → quay lại login.jsp
    │
    └─ login thành công (user != null)
           │
           │  session.setAttribute("loggedInUser", user)
           ▼
       resp.sendRedirect("/")  → trang chủ
```

## Các file đã tạo/sửa

| File | Thay đổi |
|------|----------|
| `controller/AuthController.java` | Tạo mới |
| `webapp/WEB-INF/web.xml` | Thêm welcome-file |
| `webapp/login.jsp` | Sửa action `/login` → `/auth` |

## Build

```
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## Ghi chú

- Filter bảo vệ trang chủ (yêu cầu đăng nhập) sẽ làm ở Task 4.1
- `welcome-file` = `auth` → Tomcat redirect `/` → `/auth` → login.jsp
