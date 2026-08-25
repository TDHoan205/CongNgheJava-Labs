package vn.edu.eaut.lab10.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.lab10.model.Role;
import vn.edu.eaut.lab10.model.UserSession;

import java.io.IOException;
import java.util.Set;

/**
 * Role-based access control for protected paths.
 * Runs AFTER AuthenticationFilter (user is guaranteed logged in).
 *
 * Access rules:
 *   ADMIN → /admin, /staff, /user (full access)
 *   STAFF → /staff, /user (no admin)
 *   USER  → /user (basic access only)
 */
@WebFilter(urlPatterns = {"/admin/*", "/staff/*", "/user/*"})
public class AuthorizationFilter implements jakarta.servlet.Filter {

    private static final Set<String> ADMIN_PATHS = Set.of("/admin", "/staff", "/user");
    private static final Set<String> STAFF_PATHS = Set.of("/staff", "/user");
    private static final Set<String> USER_PATHS = Set.of("/user");

    @Override
    public void doFilter(jakarta.servlet.ServletRequest servletRequest,
                         jakarta.servlet.ServletResponse servletResponse,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        // Strip context path, get servlet path
        String contextPath = req.getContextPath();
        String servletPath = req.getServletPath();

        // Get the base path (parent directory of the servlet)
        // e.g. /admin/users → /admin, /user/profile → /user
        String basePath = getBasePath(servletPath);

        HttpSession session = req.getSession(false);
        UserSession currentUser = (session != null)
                ? (UserSession) session.getAttribute("currentUser")
                : null;

        if (currentUser == null) {
            resp.sendRedirect(contextPath + "/auth");
            return;
        }

        Role role = currentUser.getRole();

        boolean hasAccess = switch (role) {
            case ADMIN -> ADMIN_PATHS.contains(basePath);
            case STAFF -> STAFF_PATHS.contains(basePath);
            case USER  -> USER_PATHS.contains(basePath);
        };

        if (!hasAccess) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập trang này.");
            return;
        }

        chain.doFilter(servletRequest, servletResponse);
    }

    private String getBasePath(String servletPath) {
        int idx = servletPath.lastIndexOf('/');
        return (idx > 0) ? servletPath.substring(0, idx) : servletPath;
    }
}
