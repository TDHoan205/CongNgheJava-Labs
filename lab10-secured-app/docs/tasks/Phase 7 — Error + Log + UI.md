# Phase 7 — Error / Log / UI ✅ PASS

**Ngày:** 2026-08-25

## Tasks

| # | Task | Status |
|---|------|--------|
| 7.1 | 403 Error | ✅ |
| 7.2 | 404 Error | ✅ |
| 7.3 | 500 Error | ✅ |
| 7.4 | Logging | ✅ |
| 7.5 | Header/Footer | ✅ |
| 7.6 | Role Menu | ✅ |
| 7.7 | Success/Error Message | ✅ |

---

## 7.1 - 403 Error

Đã hoàn thành ở Task 4.3. Trang `error/403.jsp` hiển thị:
- "Không có quyền truy cập"
- Nút Quay về Dashboard

---

## 7.2 - 404 Error

**Trang:** `error/404.jsp`

```jsp
<div class="error-code">404</div>
<div class="error-title">Không tìm thấy trang</div>
<div class="error-message">
    Trang bạn đang tìm kiếm không tồn tại<br>
    hoặc đã bị di chuyển.
</div>
<a href="${pageContext.request.contextPath}/" class="btn"> Quay về Dashboard </a>
```

---

## 7.3 - 500 Error

**Trang:** `error/500.jsp`

```jsp
<div class="error-code">500</div>
<div class="error-title">Lỗi hệ thống</div>
<div class="error-message">
    Đã xảy ra lỗi phía máy chủ.<br>
    Vui lòng thử lại sau hoặc liên hệ quản trị viên.
</div>
```

---

## 7.4 - Logging

**Logger:** `config/Logger.java`

### Methods
- `Logger.info(String message)` — Login, logout thành công
- `Logger.warn(String message)` — Login thất bại
- `Logger.error(String message)` — Lỗi hệ thống
- `Logger.error(String message, Throwable)` — Lỗi có exception

### Logged events
- Login thành công: email + role
- Login thất bại: email
- Logout: email

---

## 7.5 - Header/Footer

**Header:** `WEB-INF/views/layout/header.jsp`

```jsp
<nav class="navbar">
    <a href="/" class="navbar-brand">EAUT <span>Lab 10</span></a>
    <c:if test="${not empty sessionScope.currentUser}">
        <!-- Menu links -->
    </c:if>
</nav>
```

**Footer:** `WEB-INF/views/layout/footer.jsp`

```jsp
<footer>
    &copy; 2026 EAUT - Lab 10 Secured App
</footer>
```

---

## 7.6 - Role Menu

### Dashboard (index.jsp)

| Role | Cards hiển thị |
|------|----------------|
| ADMIN | Quản lý User, Hồ sơ, Đổi mật khẩu |
| STAFF | Hồ sơ, Đổi mật khẩu |
| USER | Hồ sơ, Đổi mật khẩu |

### Header Menu

| Role | Menu items |
|------|------------|
| ADMIN | Quản lý User, Hồ sơ, Đổi mật khẩu, Đăng xuất |
| STAFF | Hồ sơ, Đổi mật khẩu, Đăng xuất |
| USER | Hồ sơ, Đổi mật khẩu, Đăng xuất |

---

## 7.7 - Success/Error Message

### Flash Messages
- `flash_success` — Thành công (CRUD, đổi password)
- `flash_error` — Lỗi validation

### Sử dụng
```jsp
<c:if test="${not empty sessionScope.flash_success}">
    <div class="alert alert-success">${sessionScope.flash_success}</div>
    <% session.removeAttribute("flash_success"); %>
</c:if>
```

---

## Các file đã tạo

| File | Thay đổi |
|------|----------|
| `error/404.jsp` | Tạo mới |
| `error/500.jsp` | Tạo mới |
| `config/Logger.java` | Tạo mới |
| `WEB-INF/views/layout/header.jsp` | Tạo mới |
| `WEB-INF/views/layout/footer.jsp` | Tạo mới |
| `index.jsp` | Cập nhật — Dashboard + Role Menu |
| `controller/AuthController.java` | Thêm logging |
| `controller/LogoutController.java` | Thêm logging |

---

## Test cases

| # | Test | Expected |
|---|------|----------|
| 1 | Truy cập URL không tồn tại | 404 page |
| 2 | USER vào /admin | 403 page |
| 3 | Login thành công | → Dashboard + log info |
| 4 | Login thất bại | → Error message + log warn |
| 5 | Logout | → Redirect + log info |
| 6 | ADMIN login | → Dashboard có "Quản lý User" |
| 7 | STAFF login | → Dashboard không có "Quản lý User" |
