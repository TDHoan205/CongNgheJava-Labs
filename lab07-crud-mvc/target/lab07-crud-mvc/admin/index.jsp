<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Khu vực quản trị" scope="request"/>
<c:set var="pageSubtitle" value="Được bảo vệ bởi LoginFilter — /admin/*" scope="request"/>
<c:set var="activeNav" value="admin" scope="request"/>
<!DOCTYPE html>
<html lang="vi">
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="app-layout">
    <%@ include file="/WEB-INF/jspf/sidebar.jspf" %>
    <div class="main-wrapper">
        <%@ include file="/WEB-INF/jspf/topbar.jspf" %>
        <main class="main-content">

            <div class="card" style="max-width:640px;">
                <div class="info-banner">
                    <i class="bi bi-shield-check"></i>
                    <div>
                        Xin chào, <strong>${sessionScope.username}</strong>! Trang này được bảo vệ bởi
                        <strong>LoginFilter</strong> với URL pattern <code>/admin/*</code>.
                    </div>
                </div>

                <h3 style="margin:0 0 14px;">Liên kết nhanh</h3>
                <div class="action-grid">
                    <a class="action-link" href="${pageContext.request.contextPath}/sinh-vien">
                        <i class="bi bi-people-fill"></i> Quản lý sinh viên
                    </a>
                    <a class="action-link" href="${pageContext.request.contextPath}/sach">
                        <i class="bi bi-book-fill"></i> Quản lý sách
                    </a>
                    <a class="action-link" href="${pageContext.request.contextPath}/san-pham">
                        <i class="bi bi-box-seam-fill"></i> Quản lý sản phẩm
                    </a>
                    <a class="action-link" href="${pageContext.request.contextPath}/lop-hoc">
                        <i class="bi bi-building"></i> Quản lý lớp học
                    </a>
                    <a class="action-link" href="${pageContext.request.contextPath}/diem">
                        <i class="bi bi-clipboard-data-fill"></i> Quản lý điểm
                    </a>
                    <a class="action-link" href="${pageContext.request.contextPath}/gio-hang">
                        <i class="bi bi-cart-fill"></i> Giỏ hàng
                    </a>
                </div>

                <div class="form-actions" style="margin-top:24px;">
                    <a class="btn btn-danger" href="${pageContext.request.contextPath}/logout">
                        <i class="bi bi-box-arrow-right"></i> Đăng xuất
                    </a>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/">
                        <i class="bi bi-house-fill"></i> Trang chủ
                    </a>
                </div>
            </div>

        </main>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/layout-scripts.jspf" %>
</body>
</html>
