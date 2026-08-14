<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh Sách Sinh Viên - Lab 6</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f6f9; color: #333; }
        .navbar { background: #2c3e50; color: white; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .navbar h1 { font-size: 18px; font-weight: 600; }
        .navbar a { color: white; text-decoration: none; margin-left: 15px; font-size: 14px; }
        .container { max-width: 1100px; margin: 30px auto; padding: 0 20px; }
        .card { background: white; padding: 25px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.05); }
        .header-action { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; flex-wrap: wrap; gap: 15px; }
        .header-action h2 { color: #2c3e50; }
        .search-box { display: flex; gap: 10px; }
        .search-box input[type="text"] { padding: 8px 12px; border: 1px solid #ccc; border-radius: 4px; width: 250px; font-size: 14px; }
        .btn { display: inline-block; padding: 8px 16px; border-radius: 4px; text-decoration: none; font-size: 14px; font-weight: 600; border: none; cursor: pointer; }
        .btn-primary { background: #3498db; color: white; }
        .btn-success { background: #2ecc71; color: white; }
        .btn-warning { background: #f39c12; color: white; padding: 4px 10px; font-size: 12px; }
        .btn-danger { background: #e74c3c; color: white; padding: 4px 10px; font-size: 12px; }
        .btn-secondary { background: #95a5a6; color: white; }
        .table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        .table th, .table td { padding: 12px 15px; border-bottom: 1px solid #eef2f5; text-align: left; }
        .table th { background-color: #f8f9fa; color: #495057; font-weight: 600; }
        .table tr:hover { background-color: #f1f4f8; }
        .no-data { text-align: center; padding: 30px; color: #888; font-style: italic; }
        .role-notice { font-size: 13px; color: #7f8c8d; margin-top: 10px; }
    </style>
</head>
<body>

<div class="navbar">
    <h1>Hệ Thống Quản Lý Sinh Viên</h1>
    <div>
        <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
    </div>
</div>

<div class="container">
    <div class="card">
        <div class="header-action">
            <h2>Danh Sách Sinh Viên</h2>
            
            <form action="${pageContext.request.contextPath}/students" method="get" class="search-box">
                <input type="text" name="keyword" value="${keyword}" placeholder="Nhập tên sinh viên cần tìm...">
                <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                <c:if test="${not empty keyword}">
                    <a href="${pageContext.request.contextPath}/students" class="btn btn-secondary">Xem tất cả</a>
                </c:if>
            </form>

            <c:if test="${sessionScope.role == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/students/add" class="btn btn-success">+ Thêm sinh viên mới</a>
            </c:if>
        </div>

        <c:if test="${not empty keyword}">
            <p style="margin-bottom: 15px; color: #34495e;">Kết quả tìm kiếm cho từ khóa: <strong>"${keyword}"</strong></p>
        </c:if>

        <table class="table">
            <thead>
                <tr>
                    <th>STT</th>
                    <th>Mã SV</th>
                    <th>Họ và Tên</th>
                    <th>Lớp Học</th>
                    <th>Email Contact</th>
                    <c:if test="${sessionScope.role == 'ADMIN'}">
                        <th>Thao Tác Quản Trị</th>
                    </c:if>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty students}">
                        <c:forEach var="sv" items="${students}" varStatus="loop">
                            <tr>
                                <td>${loop.index + 1}</td>
                                <td><strong>${sv.id}</strong></td>
                                <td>${sv.name}</td>
                                <td>${sv.className}</td>
                                <td>${sv.email}</td>
                                <c:if test="${sessionScope.role == 'ADMIN'}">
                                    <td>
                                        <a href="${pageContext.request.contextPath}/students/edit?id=${sv.id}" class="btn btn-warning">Sửa</a>
                                        <a href="${pageContext.request.contextPath}/students/delete?id=${sv.id}" 
                                           class="btn btn-danger" 
                                           onclick="return confirm('Bạn có chắc chắn muốn xóa sinh viên ${sv.name} (${sv.id}) không?');">Xóa</a>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="${sessionScope.role == 'ADMIN' ? 6 : 5}" class="no-data">
                                Không tìm thấy sinh viên nào phù hợp với yêu cầu.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>

        <div class="role-notice">
            * Bạn đang truy cập với vai trò: <strong>${sessionScope.role}</strong>. 
            <c:if test="${sessionScope.role != 'ADMIN'}">
                (Tài khoản User chỉ có quyền xem danh sách và tìm kiếm. Đăng nhập Admin để thực hiện Thêm/Sửa/Xóa).
            </c:if>
        </div>
    </div>
</div>

</body>
</html>
