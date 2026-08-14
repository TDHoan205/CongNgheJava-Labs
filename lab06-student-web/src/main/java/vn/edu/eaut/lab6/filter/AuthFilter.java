package vn.edu.eaut.lab6.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(urlPatterns = {
        "/dashboard",
        "/welcome.jsp",
        "/students",
        "/student-list.jsp",
        "/students/add",
        "/student-form.jsp",
        "/students/edit",
        "/students/delete"
})
public class AuthFilter implements Filter {

    private static final List<String> ADMIN_ONLY_PATHS = Arrays.asList(
            "/students/add",
            "/student-form.jsp",
            "/students/edit",
            "/students/delete"
    );

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("[LOG Filter] AuthFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("username") != null);

        if (!loggedIn) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String path = req.getServletPath();
        String role = (String) session.getAttribute("role");

        // Bài 9: Check Admin permissions for admin-only paths
        if (ADMIN_ONLY_PATHS.contains(path)) {
            if (!"ADMIN".equalsIgnoreCase(role)) {
                // User is logged in but not Admin -> 403 Forbidden
                req.getRequestDispatcher("/403.jsp").forward(req, resp);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("[LOG Filter] AuthFilter destroyed");
    }
}
