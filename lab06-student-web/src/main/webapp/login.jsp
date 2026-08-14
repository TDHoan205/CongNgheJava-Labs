<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập hệ thống - Lab 6</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%); min-height: 100vh; display: flex; align-items: center; justify-content: center; }
        .login-card { background: #ffffff; width: 100%; max-width: 420px; padding: 40px; border-radius: 12px; box-shadow: 0 10px 30px rgba(0,0,0,0.25); }
        .login-card h2 { text-align: center; color: #2c3e50; margin-bottom: 8px; font-size: 24px; }
        .login-card p.subtitle { text-align: center; color: #7f8c8d; font-size: 14px; margin-bottom: 25px; }
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; font-weight: 600; color: #34495e; margin-bottom: 8px; font-size: 14px; }
        .form-group input { width: 100%; padding: 12px 15px; border: 1px solid #dcdfe6; border-radius: 6px; font-size: 14px; transition: border-color 0.3s; }
        .form-group input:focus { border-color: #3498db; outline: none; box-shadow: 0 0 5px rgba(52,152,219,0.3); }
        .btn-submit { width: 100%; padding: 12px; background-color: #3498db; border: none; color: white; border-radius: 6px; font-size: 16px; font-weight: 600; cursor: pointer; transition: background 0.3s; }
        .btn-submit:hover { background-color: #2980b9; }
        .alert-error { background-color: #fde8e8; color: #e74c3c; border: 1px solid #f5c6cb; padding: 12px; border-radius: 6px; margin-bottom: 20px; font-size: 14px; text-align: center; }
        .account-hint { margin-top: 25px; padding-top: 20px; border-top: 1px solid #eee; font-size: 13px; color: #555; line-height: 1.6; }
        .account-hint code { background: #f1f2f6; padding: 2px 6px; border-radius: 4px; color: #e74c3c; font-family: monospace; }
    </style>
</head>
<body>
<div class="login-card">
    <h2>Đăng Nhập Hệ Thống</h2>
    <p class="subtitle">Quản lý sinh viên - Lab 6 Jakarta EE</p>

    <c:if test="${not empty error}">
        <div class="alert-error">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label for="username">Tên đăng nhập:</label>
            <input type="text" id="username" name="username" placeholder="Nhập username (admin / user)" required>
        </div>

        <div class="form-group">
            <label for="password">Mật khẩu:</label>
            <input type="password" id="password" name="password" placeholder="Nhập mật khẩu (123456)" required>
        </div>

        <button type="submit" class="btn-submit">Đăng Nhập</button>
    </form>

    <div class="account-hint">
        <strong>Tài khoản thử nghiệm:</strong><br>
        • Quản trị viên (Admin): <code>admin</code> / <code>123456</code> (Full quyền Thêm/Sửa/Xóa)<br>
        • Người dùng (User): <code>user</code> / <code>123456</code> (Chỉ Xem/Tìm kiếm)
    </div>
</div>
</body>
</html>
