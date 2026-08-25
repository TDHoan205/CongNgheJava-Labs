# Task 4.1 — Authentication Filter ✅ PASS

**Ngày:** 2026-08-25

## Bảo vệ

```
/admin/*
/staff/*
/user/*
```

## Luồng xử lý

```
Request → /admin/* (hoặc /staff/*, /user/*)
    │
    ▼
AuthenticationFilter.doFilter()
    │
    │  HttpSession session = req.getSession(false);
    │  boolean isLoggedIn = (session != null
    │          && session.getAttribute("currentUser") != null);
    │
    ├─ !isLoggedIn (guest)
    │      │
    │      ▼
    │  redirect → /auth (login page)
    │
    └─ isLoggedIn (đã login)
           │
           ▼
       chain.doFilter()  → controller xử lý
```

## AuthenticationFilter

```java
@WebFilter(urlPatterns = {"/admin/*", "/staff/*", "/user/*"})
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) req).getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("currentUser") != null);

        if (!isLoggedIn) {
            ((HttpServletResponse) resp).sendRedirect(req.getContextPath() + "/auth");
            return;
        }
        chain.doFilter(req, resp);
    }
}
```

## Test cases

| # | Scenario | URL | Expected |
|---|----------|-----|----------|
| 1 | Guest chưa login | `/admin/*` | redirect → `/auth` |
| 2 | Guest chưa login | `/staff/*` | redirect → `/auth` |
| 3 | Guest chưa login | `/user/*` | redirect → `/auth` |
| 4 | User đã login | `/admin/*` | cho qua → controller xử lý |

## Ghi chú

- Filter chỉ kiểm tra **authentication** (có `currentUser` hay không)
- **Authorization** (ADMIN/STAFF/USER role) sẽ xử lý ở Task 4.2
- Filter dùng `@WebFilter` annotation — không cần khai báo trong `web.xml`
- Filter bảo vệ trước khi request đến controller nghiệp vụ
