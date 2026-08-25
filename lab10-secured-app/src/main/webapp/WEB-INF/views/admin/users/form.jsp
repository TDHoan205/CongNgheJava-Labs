<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${action == 'create' ? 'Thêm' : 'Sửa'} tài khoản - Lab 10</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />

    <div class="page-wrapper">
        <div class="page-container">
            <div class="card">
                <div class="card-header">
                    <h1>${action == 'create' ? 'Thêm Tài khoản mới' : 'Sửa Tài khoản'}</h1>
                </div>

                <form method="post" action="${pageContext.request.contextPath}/admin/users/${action == 'create' ? 'create' : 'update'}">
                    <input type="hidden" name="id" value="${user.id}">

                    <div class="form-group">
                        <label for="email">Email *</label>
                        <input type="email" id="email" name="email" value="${user.email != null ? user.email : email}" class="form-control" required placeholder="VD: user@example.com">
                    </div>

                    <c:if test="${action == 'create'}">
                        <div class="form-group">
                            <label for="password">Mật khẩu *</label>
                            <input type="password" id="password" name="password" value="${password}" class="form-control" required placeholder="Ít nhất 6 ký tự">
                        </div>
                    </c:if>

                    <div class="form-group">
                        <label for="fullName">Họ tên *</label>
                        <input type="text" id="fullName" name="fullName" value="${user.fullName != null ? user.fullName : fullName}" class="form-control" required placeholder="VD: Nguyễn Văn A">
                    </div>

                    <div class="row-2">
                        <div class="form-group">
                            <label for="role">Vai trò *</label>
                            <select id="role" name="role" class="form-control" required>
                                <option value="">-- Chọn vai trò --</option>
                                <option value="ADMIN" ${(user.role != null and user.role.name() == 'ADMIN') || role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
                                <option value="STAFF" ${(user.role != null and user.role.name() == 'STAFF') || role == 'STAFF' ? 'selected' : ''}>STAFF</option>
                                <option value="USER" ${(user.role != null and user.role.name() == 'USER') || role == 'USER' ? 'selected' : ''}>USER</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="active">Trạng thái</label>
                            <select id="active" name="active" class="form-control">
                                <option value="true">Hoạt động</option>
                                <option value="false" ${!user.active ? 'selected' : ''}>Bị khóa</option>
                            </select>
                        </div>
                    </div>

                    <div class="btn-group mt-2">
                        <button type="submit" class="btn btn-primary">${action == 'create' ? 'Thêm mới' : 'Cập nhật'}</button>
                        <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-outline">Hủy</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
</body>
</html>
