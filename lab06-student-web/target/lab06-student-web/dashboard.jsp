<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang Quản Trị - Dashboard</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f6f9; color: #333; }
        .navbar { background: #2c3e50; color: white; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .navbar h1 { font-size: 20px; font-weight: 600; }
        .navbar .user-info { font-size: 14px; }
        .navbar a.btn-logout { background: #e74c3c; color: white; padding: 8px 15px; border-radius: 4px; text-decoration: none; font-size: 13px; margin-left: 15px; }
        .container { max-width: 1100px; margin: 30px auto; padding: 0 20px; }
        .welcome-card { background: white; padding: 25px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.05); margin-bottom: 25px; }
        .welcome-card h2 { color: #2c3e50; margin-bottom: 10px; }
        .badge { display: inline-block; padding: 4px 10px; border-radius: 12px; font-size: 12px; font-weight: bold; }
        .badge-admin { background: #e74c3c; color: white; }
        .badge-user { background: #3498db; color: white; }
        .grid-stats { display: grid; grid-template-columns: repeat(auto-fit, minmax(240px, 1fr)); gap: 20px; margin-bottom: 30px; }
        .stat-card { background: white; padding: 20px; border-radius: 8px; border-left: 5px solid #3498db; box-shadow: 0 2px 10px rgba(0,0,0,0.05); }
        .stat-card.green { border-left-color: #2ecc71; }
        .stat-card.orange { border-left-color: #f39c12; }
        .stat-card h4 { color: #7f8c8d; font-size: 14px; text-transform: uppercase; margin-bottom: 10px; }
        .stat-card .number { font-size: 32px; font-weight: bold; color: #2c3e50; }
        .class-list { width: 100%; border-collapse: collapse; margin-top: 10px; }
        .class-list th, .class-list td { padding: 10px; border-bottom: 1px solid #eee; text-align: left; }
        .class-list th { background: #f8f9fa; color: #555; font-size: 13px; }
        .actions-nav { display: flex; gap: 15px; margin-top: 20px; }
        .btn-action { display: inline-block; padding: 12px 24px; background-color: #2ec4b6; color: white; text-decoration: none; border-radius: 6px; font-weight: 600; transition: background 0.3s; }
        .btn-action:hover { background-color: #20a498; }
    </style>
</head>
<body>

<div class="navbar">
    <h1>Hệ Thống Quản Lý Sinh Viên - Lab 6</h1>
    <div class="user-info">
        Xin chào, <strong>${sessionScope.fullName != null ? sessionScope.fullName : sessionScope.username}</strong>
        <span class="badge ${sessionScope.role == 'ADMIN' ? 'badge-admin' : 'badge-user'}">${sessionScope.role}</span>
        <a href="${pageContext.request.contextPath}/logout" class="btn-logout">Đăng xuất</a>
    </div>
</div>

<div class="container">
    <div class="welcome-card">
        <h2>Bảng Điều Khiển (Dashboard)</h2>
        <p>Thời gian đăng nhập: <strong>${sessionScope.loginTime}</strong></p>
    </div>

    <div class="grid-stats">
        <div class="stat-card">
            <h4>Tổng Số Sinh Viên</h4>
            <div class="number">${totalStudents}</div>
        </div>

        <div class="stat-card green">
            <h4>Quyền Hạn Hệ Thống</h4>
            <div class="number" style="font-size: 20px; line-height: 38px;">
                <c:choose>
                    <c:when test="${sessionScope.role == 'ADMIN'}">Quản trị toàn quyền</c:when>
                    <c:otherwise>Xem & Tra cứu</c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <div class="welcome-card">
        <h3>Thống Kê Sinh Viên Theo Lớp</h3>
        <table class="class-list">
            <thead>
                <tr>
                    <th>Tên Lớp Học</th>
                    <th>Số Lượng Sinh Viên</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="entry" items="${countByClass}">
                    <tr>
                        <td><strong>${entry.key}</strong></td>
                        <td>${entry.value} sinh viên</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty countByClass}">
                    <tr>
                        <td colspan="2" style="text-align: center; color: #888;">Chưa có dữ liệu sinh viên nào.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>

    <div class="actions-nav">
        <a href="${pageContext.request.contextPath}/students" class="btn-action"> Quản Lý Danh Sách Sinh Viên</a>
        <c:if test="${sessionScope.role == 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/students/add" class="btn-action" style="background-color: #e67e22;">+ Thêm Sinh Viên Mới</a>
        </c:if>
    </div>
</div>

</body>
</html>
