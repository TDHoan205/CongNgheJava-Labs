# Task 4.3 — Error 403 ✅ PASS

**Ngày:** 2026-08-25

## Kết nối Filter → 403

```
AuthorizationFilter
    │
    │  sendError(SC_FORBIDDEN)
    │
    ▼
web.xml error-page config
    │
    │  <error-code>403</error-code>
    │  <location>/error</location>
    │
    ▼
ErrorServlet
    │
    │  Lấy status code từ request attribute
    │
    ▼
forward → /error/403.jsp
```

## web.xml

```xml
<error-page>
  <error-code>403</error-code>
  <location>/error</location>
</error-page>
```

## ErrorServlet

```java
@WebServlet(name = "ErrorServlet", urlPatterns = {"/error"})
public class ErrorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer statusCode = (Integer) req.getAttribute("jakarta.servlet.error.status_code");
        int code = (statusCode != null) ? statusCode : resp.getStatus();

        String viewPath = switch (code) {
            case 403 -> "/error/403.jsp";
            case 404 -> "/error/404.jsp";
            case 500 -> "/error/500.jsp";
            default -> "/error/error.jsp";
        };
        req.getRequestDispatcher(viewPath).forward(req, resp);
    }
}
```

## 403.jsp

```jsp
<div class="error-code">403</div>
<div class="error-title">Không có quyền truy cập</div>
<div class="error-message">
    Bạn không có quyền truy cập trang này.<br>
    Vui lòng liên hệ quản trị viên nếu bạn cần hỗ trợ.
</div>
<a href="<%= request.getContextPath() %>/" class="btn"> Quay về Dashboard </a>
```

## Test cases

| # | Scenario | Expected |
|---|----------|----------|
| 1 | USER → `/admin/*` | 403 page + "Quay về Dashboard" |
| 2 | STAFF → `/admin/*` | 403 page + "Quay về Dashboard" |

## Các file đã tạo

| File | Thay đổi |
|------|----------|
| `controller/ErrorServlet.java` | Tạo mới — xử lý error codes |
| `webapp/error/403.jsp` | Tạo mới — trang 403 thân thiện |
| `webapp/WEB-INF/web.xml` | Thêm error-page config |
