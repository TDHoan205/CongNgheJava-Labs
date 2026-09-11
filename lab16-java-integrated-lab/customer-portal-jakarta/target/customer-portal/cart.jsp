<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Cart - Customer Portal</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }
        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, sans-serif;
            background: #f5f7fa;
            min-height: 100vh;
        }
        .header {
            background: white;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .header h1 {
            color: #667eea;
            font-size: 24px;
        }
        .nav {
            display: flex;
            gap: 20px;
        }
        .nav a {
            text-decoration: none;
            color: #555;
            font-weight: 500;
            padding: 8px 16px;
            border-radius: 6px;
            transition: background 0.2s;
        }
        .nav a:hover {
            background: #f0f0f0;
        }
        .nav a.active {
            background: #667eea;
            color: white;
        }
        .container {
            max-width: 1000px;
            margin: 30px auto;
            padding: 0 20px;
        }
        .alert {
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .alert-success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .alert-error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .cart-table {
            width: 100%;
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
            border-collapse: collapse;
            overflow: hidden;
        }
        .cart-table th {
            background: #667eea;
            color: white;
            padding: 15px;
            text-align: left;
            font-weight: 500;
        }
        .cart-table td {
            padding: 15px;
            border-bottom: 1px solid #eee;
        }
        .cart-table tr:last-child td {
            border-bottom: none;
        }
        .cart-table tr:hover {
            background: #f9f9f9;
        }
        .product-info {
            display: flex;
            flex-direction: column;
        }
        .product-name {
            font-weight: 600;
            color: #333;
        }
        .product-code {
            font-size: 12px;
            color: #888;
        }
        .quantity-form {
            display: flex;
            gap: 5px;
            align-items: center;
        }
        .quantity-input {
            width: 60px;
            padding: 8px;
            border: 2px solid #e1e1e1;
            border-radius: 6px;
            text-align: center;
        }
        .btn-update {
            padding: 8px 12px;
            background: #17a2b8;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 12px;
        }
        .btn-remove {
            padding: 8px 12px;
            background: #dc3545;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 12px;
            text-decoration: none;
        }
        .price {
            font-weight: 600;
            color: #667eea;
        }
        .cart-summary {
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
            padding: 20px;
            margin-top: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .total-amount {
            font-size: 24px;
            font-weight: 700;
            color: #667eea;
        }
        .btn-checkout {
            padding: 15px 40px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: transform 0.2s;
        }
        .btn-checkout:hover {
            transform: translateY(-2px);
        }
        .btn-checkout:disabled {
            background: #ccc;
            cursor: not-allowed;
            transform: none;
        }
        .btn-continue {
            padding: 15px 30px;
            background: #6c757d;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 14px;
            cursor: pointer;
            text-decoration: none;
        }
        .empty-cart {
            text-align: center;
            padding: 60px;
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
        }
        .empty-cart p {
            color: #666;
            margin-bottom: 20px;
            font-size: 18px;
        }
        .user-info {
            color: #555;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>Customer Portal</h1>
        <div class="user-info">
            Welcome, ${sessionScope.user.fullName} (${sessionScope.user.role})
        </div>
        <nav class="nav">
            <a href="${pageContext.request.contextPath}/products">Products</a>
            <a href="${pageContext.request.contextPath}/cart" class="active">Cart</a>
            <a href="${pageContext.request.contextPath}/orders">My Orders</a>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </nav>
    </div>

    <div class="container">
        <c:if test="${not empty param.success}">
            <div class="alert alert-success">${param.success}</div>
        </c:if>
        <c:if test="${not empty param.error}">
            <div class="alert alert-error">${param.error}</div>
        </c:if>

        <h2 style="margin-bottom: 20px; color: #333;">Shopping Cart</h2>

        <c:choose>
            <c:when test="${not empty cart && cart.size() > 0}">
                <table class="cart-table">
                    <thead>
                        <tr>
                            <th>Product</th>
                            <th>Unit Price</th>
                            <th>Quantity</th>
                            <th>Subtotal</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${cart}">
                            <tr>
                                <td>
                                    <div class="product-info">
                                        <span class="product-name">${item.product.name}</span>
                                        <span class="product-code">${item.product.code}</span>
                                    </div>
                                </td>
                                <td>
                                    <fmt:formatNumber value="${item.product.price}" pattern="#,##0.00"/> VND
                                </td>
                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/cart" class="quantity-form">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="productId" value="${item.product.id}">
                                        <input type="number" name="quantity" value="${item.quantity}"
                                               min="0" max="${item.product.stock}" class="quantity-input">
                                        <button type="submit" class="btn-update">Update</button>
                                    </form>
                                </td>
                                <td class="price">
                                    <fmt:formatNumber value="${item.subtotal}" pattern="#,##0.00"/> VND
                                </td>
                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/cart" style="display:inline;">
                                        <input type="hidden" name="action" value="remove">
                                        <input type="hidden" name="productId" value="${item.product.id}">
                                        <button type="submit" class="btn-remove">Remove</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div class="cart-summary">
                    <div>
                        <a href="${pageContext.request.contextPath}/products" class="btn-continue">Continue Shopping</a>
                    </div>
                    <div style="display: flex; align-items: center; gap: 30px;">
                        <div>
                            <span style="color: #666;">Total:</span>
                            <span class="total-amount"><fmt:formatNumber value="${total}" pattern="#,##0.00"/> VND</span>
                        </div>
                        <form method="post" action="${pageContext.request.contextPath}/cart">
                            <input type="hidden" name="action" value="checkout">
                            <button type="submit" class="btn-checkout">Checkout</button>
                        </form>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="empty-cart">
                    <p>Your cart is empty</p>
                    <a href="${pageContext.request.contextPath}/products" class="btn-continue">Browse Products</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
