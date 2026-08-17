package vn.edu.eaut.lab7.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.eaut.lab7.model.*;
import vn.edu.eaut.lab7.repository.SanPhamRepository;
import java.io.IOException;
import java.util.*;

@WebServlet("/gio-hang")
public class CartController extends HttpServlet {
    private final SanPhamRepository repo = new SanPhamRepository();

    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) { cart = new ArrayList<>(); session.setAttribute("cart", cart); }

        String action = req.getParameter("action");
        if ("add".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            SanPham sp = repo.findById(id);
            if (sp != null) {
                CartItem item = cart.stream().filter(x -> x.getSanPham().getId() == id).findFirst().orElse(null);
                if (item == null) cart.add(new CartItem(sp, 1)); else item.setSoLuong(item.getSoLuong() + 1);
            }
            resp.sendRedirect(req.getContextPath() + "/gio-hang"); return;
        }
        if ("remove".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            cart.removeIf(x -> x.getSanPham().getId() == id);
            resp.sendRedirect(req.getContextPath() + "/gio-hang"); return;
        }
        if ("clear".equals(action)) {
            cart.clear();
            resp.sendRedirect(req.getContextPath() + "/gio-hang"); return;
        }

        req.setAttribute("cart", cart);
        req.setAttribute("total", cart.stream().mapToDouble(CartItem::getThanhTien).sum());
        req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
    }

    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            for (CartItem item : cart) {
                String value = req.getParameter("qty_" + item.getSanPham().getId());
                try {
                    int qty = Integer.parseInt(value);
                    if (qty <= 0) cart.remove(item); else item.setSoLuong(qty);
                } catch (Exception ignored) {}
            }
        }
        resp.sendRedirect(req.getContextPath() + "/gio-hang");
    }
}
