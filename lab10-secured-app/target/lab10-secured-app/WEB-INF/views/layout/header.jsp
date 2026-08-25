<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav class="navbar">
    <a href="${pageContext.request.contextPath}/" class="navbar-brand">
        EAUT <span>Lab 10</span>
    </a>
    <c:choose>
        <c:when test="${not empty sessionScope.currentUser}">
            <div class="navbar-links">
                <a href="${pageContext.request.contextPath}/sinh-vien">Sinh viên</a>
                <a href="${pageContext.request.contextPath}/san-pham">Sản phẩm</a>
                <a href="${pageContext.request.contextPath}/sach">Sách</a>
                <span class="navbar-separator"></span>
                <c:if test="${sessionScope.currentUser.role.name() == 'ADMIN'}">
                    <a href="${pageContext.request.contextPath}/admin/users">Quản lý User</a>
                </c:if>
                <a href="${pageContext.request.contextPath}/user/profile">Hồ sơ</a>
                <a href="${pageContext.request.contextPath}/user/change-password">Đổi mật khẩu</a>
                <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="navbar-links">
                <a href="${pageContext.request.contextPath}/auth">Đăng nhập</a>
            </div>
        </c:otherwise>
    </c:choose>
</nav>
