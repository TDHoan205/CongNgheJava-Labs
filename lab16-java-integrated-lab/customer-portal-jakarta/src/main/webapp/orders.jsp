<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Orders - Customer Portal</title>
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
        .orders-table {
            width: 100%;
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
            border-collapse: collapse;
            overflow: hidden;
        }
        .orders-table th {
            background: #667eea;
            color: white;
            padding: 15px;
            text-align: left;
            font-weight: 500;
        }
        .orders-table td {
            padding: 15px;
            border-bottom: 1px solid #eee;
        }
        .orders-table tr:last-child td {
            border-bottom: none;
        }
        .orders-table tr:hover {
            background: #f9f9f9;
        }
        .order-id {
            font-weight: 600;
            color: #667eea;
        }
        .status-badge {
            display: inline-block;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            text-transform: uppercase;
        }
        .status-PENDING {
            background: #fff3cd;
            color: #856404;
        }
        .status-PROCESSING {
            background: #cce5ff;
            color: #004085;
        }
        .status-READY {
            background: #d1ecf1;
            color: #0c5460;
        }
        .status-SHIPPING {
            background: #d4edda;
            color: #155724;
        }
        .status-COMPLETED {
            background: #28a745;
            color: white;
        }
        .status-CANCELLED {
            background: #e2e3e5;
            color: #383d41;
        }
        .btn-view {
            padding: 8px 16px;
            background: #667eea;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            text-decoration: none;
            font-size: 14px;
        }
        .btn-view:hover {
            background: #5a6fd6;
        }
        .empty-orders {
            text-align: center;
            padding: 60px;
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
        }
        .empty-orders p {
            color: #666;
            margin-bottom: 20px;
            font-size: 18px;
        }
        .btn-shop {
            padding: 15px 30px;
            background: #667eea;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 14px;
            text-decoration: none;
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
            <a href="${pageContext.request.contextPath}/cart">Cart</a>
            <a href="${pageContext.request.contextPath}/orders" class="active">My Orders</a>
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

        <h2 style="margin-bottom: 20px; color: #333;">My Orders</h2>

        <c:choose>
            <c:when test="${not empty orders && orders.size() > 0}">
                <table class="orders-table">
                    <thead>
                        <tr>
                            <th>Order ID</th>
                            <th>Date</th>
                            <th>Status</th>
                            <th>Total Amount</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${orders}">
                            <tr>
                                <td class="order-id">#${order.id}</td>
                                <td>${order.createdAt}</td>
                                <td>
                                    <span class="status-badge status-${order.status}">${order.status}</span>
                                </td>
                                <td>
                                    <fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00"/> VND
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/orders/${order.id}"
                                       class="btn-view">View Details</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="empty-orders">
                    <p>You haven't placed any orders yet</p>
                    <a href="${pageContext.request.contextPath}/products" class="btn-shop">Start Shopping</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
