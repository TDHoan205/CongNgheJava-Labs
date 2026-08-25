# Task 4.2 — Authorization Filter ✅ PASS

**Ngày:** 2026-08-25

## Access Matrix

| Role  | /admin/* | /staff/* | /user/* |
|-------|:--------:|:--------:|:--------:|
| ADMIN |    ✅    |    ✅    |    ✅    |
| STAFF |    ❌    |    ✅    |    ✅    |
| USER  |    ❌    |    ❌    |    ✅    |

## Luồng xử lý

```
Request → /admin/* | /staff/* | /user/*
    │
    ▼
AuthorizationFilter
    │
    │  Lấy currentUser từ session
    │
    ├─ currentUser == null → redirect /auth
    │
    └─ currentUser != null
            │
            │  Kiểm tra role vs path
            │
            ├─ có quyền → chain.doFilter() → controller
            │
            └─ không có quyền → 403 Forbidden
```

## AuthorizationFilter

```java
@WebFilter(urlPatterns = {"/admin/*", "/staff/*", "/user/*"})
public class AuthorizationFilter implements Filter {

    private static final Set<String> ADMIN_PATHS = Set.of("/admin", "/staff", "/user");
    private static final Set<String> STAFF_PATHS = Set.of("/staff", "/user");
    private static final Set<String> USER_PATHS  = Set.of("/user");

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) req).getSession(false);
        UserSession currentUser = (session != null)
                ? (UserSession) session.getAttribute("currentUser")
                : null;

        if (currentUser == null) {
            ((HttpServletResponse) resp).sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        String basePath = getBasePath(req.getContextPath() + req.getServletPath());
        boolean hasAccess = switch (currentUser.getRole()) {
            case ADMIN -> ADMIN_PATHS.contains(basePath);
            case STAFF -> STAFF_PATHS.contains(basePath);
            case USER  -> USER_PATHS.contains(basePath);
        };

        if (!hasAccess) {
            ((HttpServletResponse) resp)
                .sendError(HttpServletResponse.SC_FORBIDDEN,
                           "Bạn không có quyền truy cập trang này.");
            return;
        }
        chain.doFilter(req, resp);
    }
}
```

## Test cases

| # | User Role | URL | Expected |
|---|-----------|-----|----------|
| 1 | ADMIN | `/admin/*` | ✅ 200 OK |
| 2 | ADMIN | `/staff/*` | ✅ 200 OK |
| 3 | ADMIN | `/user/*` | ✅ 200 OK |
| 4 | STAFF | `/admin/*` | ❌ 403 Forbidden |
| 5 | STAFF | `/staff/*` | ✅ 200 OK |
| 6 | STAFF | `/user/*` | ✅ 200 OK |
| 7 | USER | `/admin/*` | ❌ 403 Forbidden |
| 8 | USER | `/staff/*` | ❌ 403 Forbidden |
| 9 | USER | `/user/*` | ✅ 200 OK |

## Filter chain order

```
Request → AuthenticationFilter (kiểm tra login)
               │
               ├─ NOT logged in → redirect /auth
               │
               └─ logged in → AuthorizationFilter (kiểm tra role)
                                   │
                                   ├─ NO permission → 403
                                   │
                                   └─ has permission → chain.doFilter()
```

## Ghi chú

- AuthorizationFilter chạy SAU AuthenticationFilter (đã đảm bảo user đã login)
- Filter bảo vệ bằng server-side check — KHÔNG chỉ ẩn menu
- 403 Handler xử lý hiển thị trang lỗi sẽ làm ở Task 4.3
