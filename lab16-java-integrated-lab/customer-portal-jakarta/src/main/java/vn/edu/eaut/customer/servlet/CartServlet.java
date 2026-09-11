package vn.edu.eaut.customer.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.customer.dto.CartItem;
import vn.edu.eaut.customer.model.Product;
import vn.edu.eaut.customer.service.OrderService;
import vn.edu.eaut.customer.service.ProductService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servlet for shopping cart operations.
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private final ProductService productService = new ProductService();
    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<CartItem> cart = getCartFromSession(request);
        request.setAttribute("cart", cart);
        request.setAttribute("total", calculateTotal(cart));
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        switch (action) {
            case "add":
                handleAddToCart(request, response);
                break;
            case "remove":
                handleRemoveFromCart(request, response);
                break;
            case "update":
                handleUpdateCart(request, response);
                break;
            case "checkout":
                handleCheckout(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/cart");
        }
    }

    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long productId = Long.parseLong(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            if (quantity <= 0) {
                response.sendRedirect(request.getContextPath() + "/products?error=Invalid quantity");
                return;
            }

            Product product = productService.findById(productId);
            if (product == null || !product.getActive()) {
                response.sendRedirect(request.getContextPath() + "/products?error=Product not found");
                return;
            }

            if (product.getStock() < quantity) {
                response.sendRedirect(request.getContextPath() + "/products?error=Insufficient stock");
                return;
            }

            List<CartItem> cart = getCartFromSession(request);

            // Check if product already in cart
            Optional<CartItem> existingItem = cart.stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst();

            if (existingItem.isPresent()) {
                CartItem item = existingItem.get();
                int newQuantity = item.getQuantity() + quantity;
                if (product.getStock() < newQuantity) {
                    response.sendRedirect(request.getContextPath() + "/products?error=Insufficient stock for total quantity");
                    return;
                }
                item.setQuantity(newQuantity);
            } else {
                cart.add(new CartItem(product, quantity));
            }

            updateCartInSession(request, cart);
            response.sendRedirect(request.getContextPath() + "/cart?success=Added to cart");

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/products?error=Invalid input");
        }
    }

    private void handleRemoveFromCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long productId = Long.parseLong(request.getParameter("productId"));
            List<CartItem> cart = getCartFromSession(request);

            cart.removeIf(item -> item.getProduct().getId().equals(productId));
            updateCartInSession(request, cart);

            response.sendRedirect(request.getContextPath() + "/cart?success=Item removed");

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/cart?error=Invalid input");
        }
    }

    private void handleUpdateCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long productId = Long.parseLong(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            List<CartItem> cart = getCartFromSession(request);

            if (quantity <= 0) {
                // Remove item if quantity is 0 or less
                cart.removeIf(item -> item.getProduct().getId().equals(productId));
            } else {
                Optional<CartItem> itemOpt = cart.stream()
                        .filter(item -> item.getProduct().getId().equals(productId))
                        .findFirst();

                if (itemOpt.isPresent()) {
                    CartItem item = itemOpt.get();
                    Product product = productService.findById(productId);

                    if (product != null && product.getStock() < quantity) {
                        response.sendRedirect(request.getContextPath() + "/cart?error=Insufficient stock");
                        return;
                    }
                    item.setQuantity(quantity);
                }
            }

            updateCartInSession(request, cart);
            response.sendRedirect(request.getContextPath() + "/cart?success=Cart updated");

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/cart?error=Invalid input");
        }
    }

    private void handleCheckout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<CartItem> cart = getCartFromSession(request);

        if (cart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart?error=Cart is empty");
            return;
        }

        Long customerId = (Long) session.getAttribute("userId");

        try {
            orderService.createOrder(customerId, cart, "");
            // Clear the cart
            session.removeAttribute("cart");
            response.sendRedirect(request.getContextPath() + "/orders?success=Order created successfully");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/cart?error=" + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<CartItem> getCartFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    private void updateCartInSession(HttpServletRequest request, List<CartItem> cart) {
        HttpSession session = request.getSession(true);
        session.setAttribute("cart", cart);
    }

    private BigDecimal calculateTotal(List<CartItem> cart) {
        return cart.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
