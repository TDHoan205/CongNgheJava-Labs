package vn.edu.eaut.customer.servlet;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Filter for authentication - protects /products, /cart, and /orders routes.
 */
@WebFilter(urlPatterns = {"/products", "/cart", "/orders"})
public class AuthenticationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {

        // Check if user is logged in
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            // User not authenticated, redirect to login
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // User is authenticated, continue with the request
        chain.doFilter(request, response);
    }
}
