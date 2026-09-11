<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Products - Customer Portal</title>
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
            max-width: 1200px;
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
        .products-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 20px;
        }
        .product-card {
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
            padding: 20px;
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .product-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 20px rgba(0,0,0,0.12);
        }
        .product-code {
            color: #888;
            font-size: 13px;
            margin-bottom: 5px;
        }
        .product-name {
            font-size: 18px;
            font-weight: 600;
            color: #333;
            margin-bottom: 10px;
        }
        .product-price {
            font-size: 24px;
            font-weight: 700;
            color: #667eea;
            margin-bottom: 10px;
        }
        .product-stock {
            font-size: 14px;
            margin-bottom: 15px;
        }
        .stock-available {
            color: #28a745;
        }
        .stock-low {
            color: #ffc107;
        }
        .stock-out {
            color: #dc3545;
        }
        .product-form {
            display: flex;
            gap: 10px;
            align-items: flex-end;
        }
        .form-group {
            flex: 1;
        }
        .form-group label {
            display: block;
            font-size: 13px;
            color: #666;
            margin-bottom: 5px;
        }
        .form-group input {
            width: 100%;
            padding: 10px;
            border: 2px solid #e1e1e1;
            border-radius: 6px;
            font-size: 14px;
        }
        .btn-add {
            padding: 10px 20px;
            background: #667eea;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-weight: 600;
            transition: background 0.2s;
        }
        .btn-add:hover {
            background: #5a6fd6;
        }
        .btn-add:disabled {
            background: #ccc;
            cursor: not-allowed;
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
            <a href="${pageContext.request.contextPath}/products" class="active">Products</a>
            <a href="${pageContext.request.contextPath}/cart">Cart</a>
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

        <h2 style="margin-bottom: 20px; color: #333;">Available Products</h2>

        <div class="products-grid">
            <c:forEach var="product" items="${products}">
                <div class="product-card">
                    <div class="product-code">${product.code}</div>
                    <div class="product-name">${product.name}</div>
                    <div class="product-price">
                        <fmt:formatNumber value="${product.price}" pattern="#,##0.00"/> VND
                    </div>
                    <div class="product-stock">
                        <c:choose>
                            <c:when test="${product.stock > 10}">
                                <span class="stock-available">In Stock (${product.stock})</span>
                            </c:when>
                            <c:when test="${product.stock > 0}">
                                <span class="stock-low">Low Stock (${product.stock})</span>
                            </c:when>
                            <c:otherwise>
                                <span class="stock-out">Out of Stock</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <form method="post" action="${pageContext.request.contextPath}/cart" class="product-form">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="productId" value="${product.id}">
                        <div class="form-group">
                            <label for="qty-${product.id}">Quantity</label>
                            <input type="number" id="qty-${product.id}" name="quantity"
                                   value="1" min="1" max="${product.stock}" ${product.stock == 0 ? 'disabled' : ''}>
                        </div>
                        <button type="submit" class="btn-add" ${product.stock == 0 ? 'disabled' : ''}>
                            Add to Cart
                        </button>
                    </form>
                </div>
            </c:forEach>
        </div>

        <c:if test="${empty products}">
            <div style="text-align: center; padding: 50px; color: #666;">
                <p>No products available at the moment.</p>
            </div>
        </c:if>
    </div>
</body>
</html>
