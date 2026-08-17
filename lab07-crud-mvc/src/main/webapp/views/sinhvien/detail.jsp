<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Chi tiết sinh viên" scope="request"/>
<c:set var="pageSubtitle" value="${sv.hoTen}" scope="request"/>
<c:set var="activeNav" value="sinh-vien" scope="request"/>
<!DOCTYPE html>
<html lang="vi">
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="app-layout">
    <%@ include file="/WEB-INF/jspf/sidebar.jspf" %>
    <div class="main-wrapper">
        <%@ include file="/WEB-INF/jspf/topbar.jspf" %>
        <main class="main-content">

            <div class="card">
                <div class="detail-grid">
                    <div class="detail-item">
                        <label>ID</label>
                        <span>${sv.id}</span>
                    </div>
                    <div class="detail-item">
                        <label>Mã sinh viên</label>
                        <span>${sv.maSinhVien}</span>
                    </div>
                    <div class="detail-item">
                        <label>Họ tên</label>
                        <span>${sv.hoTen}</span>
                    </div>
                    <div class="detail-item">
                        <label>Email</label>
                        <span>${sv.email}</span>
                    </div>
                    <div class="detail-item">
                        <label>Lớp</label>
                        <span>${sv.lop}</span>
                    </div>
                </div>
                <div class="form-actions" style="margin-top:24px;">
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/sinh-vien?action=edit&id=${sv.id}">
                        <i class="bi bi-pencil-fill"></i> Sửa
                    </a>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/sinh-vien">
                        <i class="bi bi-arrow-left"></i> Quay lại
                    </a>
                </div>
            </div>

        </main>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/layout-scripts.jspf" %>
</body>
</html>
