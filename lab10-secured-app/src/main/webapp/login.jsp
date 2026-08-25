<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập - Lab 10</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="login-page">
        <div class="login-card">
            <div class="login-header">
                <div class="logo">&#128274;</div>
                <h1>Đăng nhập</h1>
                <p>Hệ thống quản lý Lab 10</p>
            </div>

            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="alert alert-danger">${sessionScope.errorMessage}</div>
                <% session.removeAttribute("errorMessage"); %>
            </c:if>

            <form action="${pageContext.request.contextPath}/auth" method="POST">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" class="form-control"
                           placeholder="Nhập email của bạn" required autofocus>
                </div>
                <div class="form-group">
                    <label for="password">Mật khẩu</label>
                    <input type="password" id="password" name="password" class="form-control"
                           placeholder="Nhập mật khẩu" required>
                </div>
                <button type="submit" class="btn btn-primary btn-block">Đăng nhập</button>
            </form>

            <p class="text-center mt-3 text-muted" style="font-size:0.82rem; margin-top: 1.5rem;">
                Tài khoản mẫu: admin@test.com / staff@test.com / user@test.com
            </p>
        </div>
    </div>
</body>
</html>
