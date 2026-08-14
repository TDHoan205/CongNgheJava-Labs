package vn.edu.eaut.lab6.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebFilter(urlPatterns = "/*")
public class AccessLogFilter implements Filter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("[LOG AccessFilter] AccessLogFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        long startTime = System.currentTimeMillis();
        String timeStr = LocalDateTime.now().format(FORMATTER);

        HttpSession session = req.getSession(false);
        String username = (session != null && session.getAttribute("username") != null)
                ? (String) session.getAttribute("username")
                : "Anonymous";

        String uri = req.getRequestURI();
        String method = req.getMethod();

        try {
            chain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            System.out.printf("[ACCESS LOG] %s | Method: %-4s | URI: %-30s | User: %-10s | Duration: %d ms%n",
                    timeStr, method, uri, username, duration);
        }
    }

    @Override
    public void destroy() {
        System.out.println("[LOG AccessFilter] AccessLogFilter destroyed");
    }
}
