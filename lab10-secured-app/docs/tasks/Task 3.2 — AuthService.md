# Task 3.2 — AuthService ✅ PASS

**Ngày:** 2026-08-25

## Luồng xác thực (Authentication Flow)

```
Client (Browser)
    │
    │  POST /login?email=&password=
    ▼
AuthController        ← Task 3.3 (chưa làm)
    │
    │  authService.login(email, password)
    ▼
AuthService           ← ✅ Task 3.2 (đã làm)
    │
    │  1. null check email, password
    │  2. userRepository.findByEmail(email)
    ▼
UserRepository        ← Task 2.4 (đã làm)
    │
    │  SELECT * FROM users WHERE email = ?
    ▼
Database (MySQL)
    │
    │  User record hoặc null
    ▼
AuthService (tiếp tục)
    │
    │  3. user == null         → return null
    │  4. !user.isActive()     → return null
    │  5. !password.equals()   → return null
    │  6. all passed           → return user
    ▼
AuthController        ← nhận User, lưu vào session
```

## AuthService.login()

```java
public User login(String email, String password) {
    // 1. Validate input
    if (email == null || email.isBlank()) return null;
    if (password == null || password.isBlank()) return null;

    // 2. Find user by email
    User user = userRepository.findByEmail(email);
    if (user == null) return null;

    // 3. Check active status
    if (!user.isActive()) return null;

    // 4. Check password
    if (!password.equals(user.getPassword())) return null;

    // 5. Success
    return user;
}
```

## Các file đã tạo/sửa

| File | Thay đổi |
|------|-----------|
| `model/User.java` | Thêm `@Entity`, `@Table(name="users")` |
| `service/AuthService.java` | Tạo mới |

## Build

```
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## Ghi chú

- Password so sánh plaintext (chưa mã hóa) — sẽ cải thiện ở Task 6.3
- `findByEmail` trả `null` nếu không tìm thấy → `AuthService` không phân biệt "sai email" hay "sai password" (tránh user enumeration)
