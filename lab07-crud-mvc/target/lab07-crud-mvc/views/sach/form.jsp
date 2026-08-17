<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${empty sach ? 'Thêm sách' : 'Sửa sách'}" scope="request"/>
<c:set var="pageSubtitle" value="Nhập thông tin sách" scope="request"/>
<c:set var="activeNav" value="sach" scope="request"/>
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
                <form method="post" action="${pageContext.request.contextPath}/sach" class="form-stack">
                    <input type="hidden" name="id" value="${sach.id}">
                    <div class="form-group">
                        <label>Mã sách <span class="required">*</span></label>
                        <input name="maSach" value="${sach.maSach}" required placeholder="VD: S001">
                    </div>
                    <div class="form-group">
                        <label>Tên sách <span class="required">*</span></label>
                        <input name="tenSach" value="${sach.tenSach}" required placeholder="Nhập tên sách">
                    </div>
                    <div class="form-group">
                        <label>Tác giả</label>
                        <input name="tacGia" value="${sach.tacGia}" placeholder="Tên tác giả">
                    </div>
                    <div class="form-group">
                        <label>Nhà xuất bản</label>
                        <input name="nhaXuatBan" value="${sach.nhaXuatBan}" placeholder="Tên NXB">
                    </div>
                    <div class="form-group">
                        <label>Năm xuất bản <span class="required">*</span></label>
                        <input type="number" name="namXuatBan" value="${sach.namXuatBan}" required placeholder="2024">
                    </div>
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary"><i class="bi bi-check-lg"></i> Lưu</button>
                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/sach"><i class="bi bi-x-lg"></i> Hủy</a>
                    </div>
                </form>
            </div>

        </main>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/layout-scripts.jspf" %>
</body>
</html>
