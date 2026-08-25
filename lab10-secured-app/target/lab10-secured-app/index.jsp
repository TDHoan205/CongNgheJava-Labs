<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lab 10 - Quản lý</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />

    <div class="page-wrapper">
        <div class="page-container">
            <div class="welcome-card">
                <h1>Lab 10 - Quản lý Dữ liệu</h1>
                <p>Quản lý Sinh viên, Sản phẩm và Sách với JPA + MySQL + Security</p>
            </div>

            <div class="module-cards">
                <div class="module-card">
                    <h4>Sinh viên</h4>
                    <a href="${pageContext.request.contextPath}/sinh-vien">Xem danh sách &rarr;</a>
                </div>
                <div class="module-card">
                    <h4>Sản phẩm</h4>
                    <a href="${pageContext.request.contextPath}/san-pham">Xem danh sách &rarr;</a>
                </div>
                <div class="module-card">
                    <h4>Sách</h4>
                    <a href="${pageContext.request.contextPath}/sach">Xem danh sách &rarr;</a>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
</body>
</html>
