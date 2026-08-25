package vn.edu.eaut.lab10.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

/**
 * Protects /admin/*, /staff/*, /user/* paths.
 * Redirects unauthenticated users to /auth (login page).
 * Public URLs bypass this filter completely.
 */
@WebFilter(urlPatterns = {"/admin/*", "/staff/*", "/user/*"})
public class AuthenticationFilter implements jakarta.servlet.Filter {

    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/auth", "/login.jsp", "/", "/index.jsp"
    );

    @Override
    public void doFilter(jakarta.servlet.ServletRequest servletRequest,
                         jakarta.servlet.ServletResponse servletResponse,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String path = req.getServletPath();

        // Public paths bypass authentication
        if (PUBLIC_PATHS.contains(path)) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        // Assets bypass authentication
        if (path.startsWith("/assets/") || path.startsWith("/css/") || path.startsWith("/js/")) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpSession session = req.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("currentUser") != null);

        if (!isLoggedIn) {
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        chain.doFilter(servletRequest, servletResponse);
    }
}
