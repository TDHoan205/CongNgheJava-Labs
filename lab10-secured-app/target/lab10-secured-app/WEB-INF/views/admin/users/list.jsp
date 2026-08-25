<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý tài khoản - Lab 10</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />

    <div class="page-wrapper">
        <div class="page-container">
            <div class="card">
                <div class="card-header">
                    <h1>Quản lý tài khoản</h1>
                    <a href="${pageContext.request.contextPath}/admin/users/new" class="btn btn-primary">+ Thêm tài khoản</a>
                </div>

                <c:if test="${not empty flash_success}">
                    <div class="alert alert-success">${flash_success}</div>
                </c:if>
                <c:if test="${not empty flash_error}">
                    <div class="alert alert-danger">${flash_error}</div>
                </c:if>

                <div class="toolbar">
                    <form method="get" action="${pageContext.request.contextPath}/admin/users" class="toolbar-search">
                        <input type="text" name="keyword" value="${keyword}" placeholder="Tìm theo email..." class="form-control">
                        <button type="submit" class="btn btn-secondary">Tìm kiếm</button>
                        <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-outline">Xem tất cả</a>
                    </form>
                </div>

                <div class="table-wrap">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Email</th>
                                <th>Họ tên</th>
                                <th>Vai trò</th>
                                <th>Trạng thái</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty users}">
                                    <tr><td colspan="6" class="empty-state">Không có tài khoản nào.</td></tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="user" items="${users}">
                                        <tr>
                                            <td>${user.id}</td>
                                            <td>${user.email}</td>
                                            <td>${user.fullName}</td>
                                            <td>
                                                <span class="badge badge-${user.role.name().toLowerCase()}">${user.role.name()}</span>
                                            </td>
                                            <td>
                                                <span class="badge ${user.active ? 'badge-success' : 'badge-danger'}">
                                                    ${user.active ? 'Hoạt động' : 'Bị khóa'}
                                                </span>
                                            </td>
                                            <td>
                                                <div class="btn-group">
                                                    <a href="${pageContext.request.contextPath}/admin/users/edit?id=${user.id}" class="btn btn-sm btn-secondary">Sửa</a>
                                                    <form method="post" action="${pageContext.request.contextPath}/admin/users/toggle" style="display:inline;">
                                                        <input type="hidden" name="id" value="${user.id}">
                                                        <button type="submit" class="btn btn-sm ${user.active ? 'btn-outline' : 'btn-secondary'}">
                                                            ${user.active ? 'Khóa' : 'Mở khóa'}
                                                        </button>
                                                    </form>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>

                <c:if test="${not empty users}">
                    <p class="text-muted mt-2" style="font-size:0.85rem;">Tổng: ${users.size()} tài khoản</p>
                </c:if>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
</body>
</html>
