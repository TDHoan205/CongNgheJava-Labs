<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${empty sanPham ? 'Thêm sản phẩm' : 'Sửa sản phẩm'}" scope="request"/>
<c:set var="pageSubtitle" value="Nhập thông tin sản phẩm" scope="request"/>
<c:set var="activeNav" value="san-pham" scope="request"/>
<!DOCTYPE html>
<html lang="vi">
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="app-layout">
    <%@ include file="/WEB-INF/jspf/sidebar.jspf" %>
    <div class="main-wrapper">
        <%@ include file="/WEB-INF/jspf/topbar.jspf" %>
        <main class="main-content">

            <% if (request.getParameter("error") != null) { %>
            <div class="alert alert-error"><i class="bi bi-exclamation-circle-fill"></i> ${param.error}</div>
            <% } %>

            <div class="card form-card">
                <form method="post" action="${pageContext.request.contextPath}/san-pham" class="form-stack">
                    <input type="hidden" name="id" value="${sanPham.id}">
                    <div class="form-group">
                        <label>Mã <span class="required">*</span></label>
                        <input name="ma" value="${sanPham.ma}" required placeholder="VD: SP001">
                    </div>
                    <div class="form-group">
                        <label>Tên <span class="required">*</span></label>
                        <input name="ten" value="${sanPham.ten}" required placeholder="Tên sản phẩm">
                    </div>
                    <div class="form-group">
                        <label>Mô tả</label>
                        <textarea name="moTa" placeholder="Mô tả sản phẩm">${sanPham.moTa}</textarea>
                    </div>
                    <div class="form-group">
                        <label>Giá (VNĐ) <span class="required">*</span></label>
                        <input type="number" step="0.01" min="0.01" name="gia" value="${sanPham.gia}" required placeholder="Phải lớn hơn 0">
                        <span class="form-hint">Giá phải lớn hơn 0</span>
                    </div>
                    <div class="form-group">
                        <label>Số lượng <span class="required">*</span></label>
                        <input type="number" min="0" name="soLuong" value="${sanPham.soLuong}" required placeholder="0">
                    </div>
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary"><i class="bi bi-check-lg"></i> Lưu</button>
                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/san-pham"><i class="bi bi-x-lg"></i> Hủy</a>
                    </div>
                </form>
            </div>

        </main>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/layout-scripts.jspf" %>
</body>
</html>
