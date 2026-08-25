# Task 3.5 — Logout ✅ PASS

**Ngày:** 2026-08-25

## Endpoint `/logout`

```
GET  /logout → invalidate session → redirect /auth
POST /logout → invalidate session → redirect /auth
```

## Luồng xử lý

```
GET/POST /logout
    │
    ▼
LogoutController
    │
    │  HttpSession session = req.getSession(false);
    │  if (session != null) session.invalidate();
    │
    ▼
session.invalidate()
    │
    │  Xóa toàn bộ session attributes:
    │  ├─ currentUser    → null
    │  ├─ userRole       → null
    │  └─ errorMessage   → null
    │
    ▼
redirect → /auth (login page)
```

## Test cases

| Bước | Hành động | Kết quả mong đợi |
|------|-----------|-----------------|
| 1 | Login với account hợp lệ | `currentUser` có trong session |
| 2 | GET /logout | Session invalidate, redirect `/auth` |
| 3 | Browser Back | Về lại trang trước logout (nội dung đã bị server reject nếu có Filter) |
| 4 | Reload trang bất kỳ | Không có `currentUser` → yêu cầu login (nếu Filter đã hoàn thành) |

## Các file đã tạo

| File | Thay đổi |
|------|----------|
| `controller/LogoutController.java` | Tạo mới — `@WebServlet("/logout")` |

## Build

```
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## Ghi chú

- `session.invalidate()` hủy toàn bộ session — KHÔNG chỉ xóa `currentUser`
- Filter bảo vệ trang (Task 4.1/4.2) sẽ đảm bảo Back/Reload sau logout bị reject
