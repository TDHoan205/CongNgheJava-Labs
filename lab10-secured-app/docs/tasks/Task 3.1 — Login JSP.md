# Task 3.1 — Login JSP ✅ PASS

**Ngày:** 2026-08-25

## Mục tiêu

Chỉ tạo giao diện login. Không có authentication logic.

## Giao diện

```
┌──────────────────────────────┐
│  EAUT Lab 10                │
└──────────────────────────────┘

    ┌──────────────────────┐
    │        🔐            │
    │    Đăng nhập         │
    │  Hệ thống quản lý    │
    │                      │
    │  [Email input   ]     │
    │  [Password input]    │
    │  [ Đăng nhập  ]      │
    └──────────────────────┘
```

## Các file đã tạo

| File | Vai trò |
|------|---------|
| `webapp/css/style.css` | CSS shared (dark theme, Inter font, CSS variables) |
| `webapp/login.jsp` | Trang login |

## Chi tiết login.jsp

- Form POST đến `/login`
- Input: `email` (type=email), `password` (type=password)
- Hiển thị error từ `sessionScope.errorMessage`
- CSS class: `.login-card`, `.form-control`, `.btn-primary`, `.alert-danger`
- Không có authentication logic

## Build

```
BUILD SUCCESS
```

## Ghi chú

- Authentication logic sẽ làm ở Task 3.2 (AuthService) và Task 3.3 (AuthController)
