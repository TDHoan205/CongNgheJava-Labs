<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>403 - Access Denied / Truy Cập Bị Từ Chối</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; color: #333; text-align: center; padding-top: 80px; }
        .error-card { background: white; max-width: 520px; margin: 0 auto; padding: 40px; border-radius: 12px; box-shadow: 0 4px 20px rgba(0,0,0,0.1); border-top: 6px solid #e74c3c; }
        h1 { font-size: 72px; color: #e74c3c; margin: 0; line-height: 1; }
        h2 { font-size: 22px; color: #2c3e50; margin: 15px 0 10px; }
        p { color: #7f8c8d; font-size: 15px; margin-bottom: 25px; line-height: 1.5; }
        .btn-home { display: inline-block; padding: 10px 24px; background: #3498db; color: white; text-decoration: none; border-radius: 6px; font-weight: 600; }
        .btn-home:hover { background: #2980b9; }
    </style>
</head>
<body>
<div class="error-card">
    <h1>403</h1>
    <h2>TRUY CẬP BỊ TỪ CHỐI (ACCESS DENIED)</h2>
    <p>Rất tiếc! Tài khoản của bạn (<strong>${sessionScope.username}</strong> - Vai trò: <strong>${sessionScope.role}</strong>) không có quyền thực hiện chức năng quản trị này.<br>Vui lòng đăng nhập bằng tài khoản <strong>Admin</strong> để tiếp tục.</p>
    <a href="${pageContext.request.contextPath}/students" class="btn-home">Quay Lại Danh Sách Sinh Viên</a>
</div>
</body>
</html>
