<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${empty sv ? 'Thêm sinh viên' : 'Sửa sinh viên'}" scope="request"/>
<c:set var="pageSubtitle" value="Nhập thông tin sinh viên" scope="request"/>
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

            <div class="card form-card">
                <form method="post" action="${pageContext.request.contextPath}/sinh-vien" class="form-stack">
                    <input type="hidden" name="id" value="${sv.id}">
                    <div class="form-group">
                        <label>Mã sinh viên <span class="required">*</span></label>
                        <input name="maSinhVien" value="${sv.maSinhVien}" required placeholder="VD: 20240001">
                    </div>
                    <div class="form-group">
                        <label>Họ tên <span class="required">*</span></label>
                        <input name="hoTen" value="${sv.hoTen}" required placeholder="Nhập họ và tên">
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input name="email" value="${sv.email}" type="email" placeholder="email@example.com">
                    </div>
                    <div class="form-group">
                        <label>Lớp</label>
                        <input name="lop" value="${sv.lop}" placeholder="VD: DCCNTT15.10.1">
                    </div>
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary"><i class="bi bi-check-lg"></i> Lưu</button>
                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/sinh-vien"><i class="bi bi-x-lg"></i> Hủy</a>
                    </div>
                </form>
            </div>

        </main>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/layout-scripts.jspf" %>
</body>
</html>
