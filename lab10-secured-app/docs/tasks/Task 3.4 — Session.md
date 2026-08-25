# Task 3.4 — Session ✅ PASS

**Ngày:** 2026-08-25

## Thiết kế session identity

```
User (Entity — có password)
    │
    │  sau khi login thành công
    ▼
UserSession (DTO — KHÔNG có password)
    │
    │  Lưu vào session
    ▼
HttpSession
    ├─ currentUser : UserSession   ← role identity
    ├─ userRole    : String       ← role name (ADMIN/STAFF/USER)
    └─ errorMessage: String       ← login error
```

## UserSession

```java
public class UserSession {
    private final Integer userId;
    private final String email;
    private final String fullName;
    private final Role   role;      // enum ADMIN/STAFF/USER
    // NO password field
}
```

## AuthController (login thành công)

```java
UserSession userSession = new UserSession(
        user.getId(),
        user.getEmail(),
        user.getFullName(),
        user.getRole()
);
HttpSession session = req.getSession();
session.setAttribute("currentUser", userSession);
session.setAttribute("userRole", user.getRole().name());
```

## Các file đã tạo/sửa

| File | Thay đổi |
|------|----------|
| `model/UserSession.java` | Tạo mới — session identity, không có password |
| `controller/AuthController.java` | Tạo UserSession từ User, lưu vào session |

## Build

```
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## Ghi chú

- `UserSession` là DTO — tách biệt Entity (có password) với session object (không có password)
- AuthorizationFilter (sử dụng session) sẽ làm ở Task 4.2
- Logout invalidate session sẽ làm ở Task 3.5
