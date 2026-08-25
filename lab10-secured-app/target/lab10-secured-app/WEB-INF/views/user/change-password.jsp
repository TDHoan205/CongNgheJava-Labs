<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đổi mật khẩu - Lab 10</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />

    <div class="page-wrapper">
        <div class="page-container">
            <div class="password-card">
                <div class="profile-header" style="margin-bottom:1.5rem;">
                    <h2 style="color:var(--text-primary);">Đổi mật khẩu</h2>
                </div>

                <c:if test="${not empty flash_success}">
                    <div class="alert alert-success">${flash_success}</div>
                </c:if>
                <c:if test="${not empty flash_error}">
                    <div class="alert alert-danger">${flash_error}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/user/change-password" method="post">
                    <div class="form-group">
                        <label for="currentPassword">Mật khẩu hiện tại *</label>
                        <input type="password" id="currentPassword" name="currentPassword" class="form-control" required placeholder="Nhập mật khẩu hiện tại">
                    </div>
                    <div class="form-group">
                        <label for="newPassword">Mật khẩu mới *</label>
                        <input type="password" id="newPassword" name="newPassword" class="form-control" required placeholder="Ít nhất 6 ký tự">
                        <div class="password-hint">Ít nhất 6 ký tự</div>
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword">Xác nhận mật khẩu mới *</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required placeholder="Nhập lại mật khẩu mới">
                    </div>
                    <div class="btn-group">
                        <a href="${pageContext.request.contextPath}/user/profile" class="btn btn-outline">Hủy</a>
                        <button type="submit" class="btn btn-primary">Đổi mật khẩu</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
</body>
</html>
