<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Details - Customer Portal</title>
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
        .order-info {
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
            padding: 25px;
            margin-bottom: 20px;
        }
        .order-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            padding-bottom: 15px;
            border-bottom: 1px solid #eee;
        }
        .order-id {
            font-size: 24px;
            font-weight: 700;
            color: #333;
        }
        .status-badge {
            display: inline-block;
            padding: 8px 16px;
            border-radius: 20px;
            font-size: 14px;
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
        .info-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 20px;
        }
        .info-item label {
            display: block;
            color: #888;
            font-size: 12px;
            margin-bottom: 5px;
            text-transform: uppercase;
        }
        .info-item span {
            color: #333;
            font-weight: 500;
        }
        .items-table {
            width: 100%;
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
            border-collapse: collapse;
            overflow: hidden;
            margin-bottom: 20px;
        }
        .items-table th {
            background: #667eea;
            color: white;
            padding: 15px;
            text-align: left;
            font-weight: 500;
        }
        .items-table td {
            padding: 15px;
            border-bottom: 1px solid #eee;
        }
        .items-table tr:last-child td {
            border-bottom: none;
        }
        .items-table tr:hover {
            background: #f9f9f9;
        }
        .product-cell {
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
        .price-cell {
            font-weight: 500;
            color: #667eea;
        }
        .total-row {
            background: #f8f9fa;
            font-weight: 700;
        }
        .total-row td {
            font-size: 18px;
            color: #667eea;
        }
        .timeline {
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
            padding: 25px;
            margin-bottom: 20px;
        }
        .timeline h3 {
            margin-bottom: 20px;
            color: #333;
        }
        .timeline-item {
            display: flex;
            gap: 15px;
            padding-bottom: 20px;
            position: relative;
        }
        .timeline-item:not(:last-child)::before {
            content: '';
            position: absolute;
            left: 11px;
            top: 25px;
            bottom: 0;
            width: 2px;
            background: #e1e1e1;
        }
        .timeline-dot {
            width: 24px;
            height: 24px;
            border-radius: 50%;
            background: #667eea;
            flex-shrink: 0;
        }
        .timeline-dot.completed {
            background: #28a745;
        }
        .timeline-dot.cancelled {
            background: #dc3545;
        }
        .timeline-content {
            flex: 1;
        }
        .timeline-status {
            font-weight: 600;
            color: #333;
        }
        .timeline-time {
            font-size: 12px;
            color: #888;
            margin-top: 3px;
        }
        .timeline-note {
            font-size: 13px;
            color: #666;
            margin-top: 5px;
        }
        .actions {
            display: flex;
            gap: 15px;
            margin-top: 20px;
        }
        .btn-back {
            padding: 12px 24px;
            background: #6c757d;
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            text-decoration: none;
        }
        .btn-cancel {
            padding: 12px 24px;
            background: #dc3545;
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
        }
        .btn-cancel:hover {
            background: #c82333;
        }
        .btn-confirm {
            padding: 12px 24px;
            background: #28a745;
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
        }
        .btn-confirm:hover {
            background: #218838;
        }
        .cancel-form {
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
            padding: 25px;
        }
        .cancel-form h3 {
            margin-bottom: 15px;
            color: #dc3545;
        }
        .cancel-form textarea {
            width: 100%;
            padding: 12px;
            border: 2px solid #e1e1e1;
            border-radius: 8px;
            margin-bottom: 15px;
            font-family: inherit;
            resize: vertical;
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

        <div class="order-info">
            <div class="order-header">
                <div class="order-id">Order #${order.id}</div>
                <span class="status-badge status-${order.status}">${order.status}</span>
            </div>
            <div class="info-grid">
                <div class="info-item">
                    <label>Order Date</label>
                    <span>${order.createdAt}</span>
                </div>
                <div class="info-item">
                    <label>Total Amount</label>
                    <span><fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00"/> VND</span>
                </div>
                <c:if test="${not empty order.note}">
                    <div class="info-item" style="grid-column: span 2;">
                        <label>Note</label>
                        <span>${order.note}</span>
                    </div>
                </c:if>
            </div>
        </div>

        <table class="items-table">
            <thead>
                <tr>
                    <th>Product</th>
                    <th>Unit Price</th>
                    <th>Quantity</th>
                    <th>Subtotal</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${order.items}">
                    <tr>
                        <td>
                            <div class="product-cell">
                                <span class="product-name">${item.product.name}</span>
                                <span class="product-code">${item.product.code}</span>
                            </div>
                        </td>
                        <td class="price-cell">
                            <fmt:formatNumber value="${item.unitPrice}" pattern="#,##0.00"/> VND
                        </td>
                        <td>${item.quantity}</td>
                        <td class="price-cell">
                            <fmt:formatNumber value="${item.subtotal}" pattern="#,##0.00"/> VND
                        </td>
                    </tr>
                </c:forEach>
                <tr class="total-row">
                    <td colspan="3" style="text-align: right;">Total:</td>
                    <td><fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00"/> VND</td>
                </tr>
            </tbody>
        </table>

        <div class="timeline">
            <h3>Order History</h3>
            <c:forEach var="h" items="${history}">
                <div class="timeline-item">
                    <div class="timeline-dot ${h.newStatus == 'COMPLETED' ? 'completed' : ''} ${h.newStatus == 'CANCELLED' ? 'cancelled' : ''}"></div>
                    <div class="timeline-content">
                        <div class="timeline-status">
                            ${h.oldStatus != null ? h.oldStatus : 'Created'} → ${h.newStatus}
                            <c:if test="${h.platform != null}">
                                <small style="color: #888;">(${h.platform})</small>
                            </c:if>
                        </div>
                        <div class="timeline-time">
                            ${h.changedAt}
                        </div>
                        <c:if test="${not empty h.note}">
                            <div class="timeline-note">${h.note}</div>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="actions">
            <a href="${pageContext.request.contextPath}/orders" class="btn-back">← Back to Orders</a>

            <c:if test="${canCancel}">
                <div class="cancel-form" style="margin-left: auto;">
                    <h3>Cancel Order</h3>
                    <form method="post" action="${pageContext.request.contextPath}/orders/${order.id}/cancel">
                        <textarea name="note" placeholder="Reason for cancellation (optional)"></textarea>
                        <button type="submit" class="btn-cancel"
                                onclick="return confirm('Are you sure you want to cancel this order?')">
                            Cancel Order
                        </button>
                    </form>
                </div>
            </c:if>

            <c:if test="${canConfirm}">
                <form method="post" action="${pageContext.request.contextPath}/orders/${order.id}/confirm"
                      style="margin-left: auto;">
                    <button type="submit" class="btn-confirm"
                            onclick="return confirm('Confirm that you have received this order?')">
                        Confirm Received
                    </button>
                </form>
            </c:if>
        </div>
    </div>
</body>
</html>
