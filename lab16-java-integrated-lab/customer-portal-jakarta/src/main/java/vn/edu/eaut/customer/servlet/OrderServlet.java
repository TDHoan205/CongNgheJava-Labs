package vn.edu.eaut.customer.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.customer.model.Order;
import vn.edu.eaut.customer.model.OrderStatus;
import vn.edu.eaut.customer.model.OrderStatusHistory;
import vn.edu.eaut.customer.service.OrderService;

import java.io.IOException;
import java.util.List;

/**
 * Servlet for order operations.
 */
@WebServlet("/orders")
public class OrderServlet extends HttpServlet {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // List all orders for the current customer
            listOrders(request, response);
        } else if (pathInfo.matches("/\\d+")) {
            // Show order detail
            Long orderId = Long.parseLong(pathInfo.substring(1));
            showOrderDetail(orderId, request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/orders");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Long customerId = (Long) session.getAttribute("userId");

        // Handle query-string based actions (for backward compatibility)
        String action = request.getParameter("action");
        String orderIdParam = request.getParameter("id");
        Long orderId = null;
        if (orderIdParam != null) {
            try { orderId = Long.parseLong(orderIdParam); } catch (Exception ignored) {}
        }

        if (pathInfo != null && !pathInfo.equals("/")) {
            if (pathInfo.matches("/\\d+/cancel")) {
                orderId = Long.parseLong(pathInfo.split("/")[1]);
                handleCancelOrder(orderId, customerId, request, response);
            } else if (pathInfo.matches("/\\d+/confirm")) {
                orderId = Long.parseLong(pathInfo.split("/")[1]);
                handleConfirmOrder(orderId, customerId, request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/orders");
            }
        } else if (action != null && orderId != null) {
            if ("confirm".equals(action)) {
                handleConfirmOrder(orderId, customerId, request, response);
            } else if ("cancel".equals(action)) {
                handleCancelOrder(orderId, customerId, request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/orders");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/orders");
        }
    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Long customerId = (Long) session.getAttribute("userId");
        List<Order> orders = orderService.findByCustomer(customerId);
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/orders.jsp").forward(request, response);
    }

    private void showOrderDetail(Long orderId, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Long customerId = (Long) session.getAttribute("userId");
        Order order = orderService.findByIdWithItems(orderId);

        if (order == null || !order.getCustomerId().equals(customerId)) {
            response.sendRedirect(request.getContextPath() + "/orders?error=Order not found");
            return;
        }

        List<OrderStatusHistory> history = orderService.getHistory(orderId);

        request.setAttribute("order", order);
        request.setAttribute("history", history);
        request.setAttribute("canCancel", order.getStatus() == OrderStatus.PENDING);
        request.setAttribute("canConfirm", order.getStatus() == OrderStatus.SHIPPING);

        request.getRequestDispatcher("/order-detail.jsp").forward(request, response);
    }

    private void handleCancelOrder(Long orderId, Long customerId,
                                   HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String note = request.getParameter("note");
            orderService.cancelOrder(orderId, customerId, note);
            response.sendRedirect(request.getContextPath() + "/orders/" + orderId + "?success=Order cancelled");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/orders/" + orderId + "?error=" + e.getMessage());
        }
    }

    private void handleConfirmOrder(Long orderId, Long customerId,
                                    HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            orderService.confirmReceived(orderId, customerId);
            response.sendRedirect(request.getContextPath() + "/orders/" + orderId + "?success=Order confirmed");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/orders/" + orderId + "?error=" + e.getMessage());
        }
    }
}
