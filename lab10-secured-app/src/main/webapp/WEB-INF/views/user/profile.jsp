<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hồ sơ cá nhân - Lab 10</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />

    <div class="page-wrapper">
        <div class="page-container">
            <div class="profile-card">
                <div class="profile-header">
                    <div class="profile-avatar">
                        ${user.fullName.substring(0, 1).toUpperCase()}
                    </div>
                    <h2>${user.fullName}</h2>
                    <span class="badge badge-${user.role.name().toLowerCase()}">${user.role.name()}</span>
                </div>

                <c:if test="${not empty flash_success}">
                    <div class="alert alert-success">${flash_success}</div>
                </c:if>
                <c:if test="${not empty flash_error}">
                    <div class="alert alert-danger">${flash_error}</div>
                </c:if>

                <div class="profile-info">
                    <div class="info-row">
                        <span class="info-label">Email</span>
                        <span class="info-value">${user.email}</span>
                    </div>
                </div>

                <h3 style="margin-bottom: 1rem; color: var(--text-primary);">Cập nhật thông tin</h3>
                <form action="${pageContext.request.contextPath}/user/profile" method="post">
                    <div class="form-group">
                        <label for="fullName">Họ tên</label>
                        <input type="text" id="fullName" name="fullName" class="form-control" required value="${user.fullName}" placeholder="Nhập họ tên đầy đủ">
                    </div>
                    <div class="btn-group">
                        <a href="${pageContext.request.contextPath}/" class="btn btn-outline">Quay lại</a>
                        <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
</body>
</html>
