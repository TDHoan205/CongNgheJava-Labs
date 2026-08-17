<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${empty diem ? 'Nhập điểm' : 'Sửa điểm'}" scope="request"/>
<c:set var="pageSubtitle" value="Nhập điểm thành phần sinh viên" scope="request"/>
<c:set var="activeNav" value="diem" scope="request"/>
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

            <div class="info-banner">
                <i class="bi bi-calculator-fill"></i>
                <span>Công thức tính điểm: <strong>Tổng kết = 10% × Chuyên cần + 30% × Giữa kỳ + 60% × Cuối kỳ</strong>. Điểm từng thành phần: 0 – 10.</span>
            </div>

            <div class="card form-card">
                <form method="post" action="${pageContext.request.contextPath}/diem" class="form-stack">
                    <input type="hidden" name="id" value="${diem.id}">
                    <div class="form-group">
                        <label>ID sinh viên <span class="required">*</span></label>
                        <input type="number" name="sinhVienId" value="${diem.sinhVienId}" required placeholder="1">
                    </div>
                    <div class="form-group">
                        <label>Mã sinh viên <span class="required">*</span></label>
                        <input name="maSinhVien" value="${diem.maSinhVien}" required placeholder="20240001">
                    </div>
                    <div class="form-group">
                        <label>Họ tên <span class="required">*</span></label>
                        <input name="hoTen" value="${diem.hoTen}" required placeholder="Họ và tên sinh viên">
                    </div>
                    <div class="form-group">
                        <label>Chuyên cần (10%) <span class="required">*</span></label>
                        <input type="number" step="0.1" min="0" max="10" name="chuyenCan" value="${diem.chuyenCan}" required placeholder="0 – 10">
                    </div>
                    <div class="form-group">
                        <label>Giữa kỳ (30%) <span class="required">*</span></label>
                        <input type="number" step="0.1" min="0" max="10" name="giuaKy" value="${diem.giuaKy}" required placeholder="0 – 10">
                    </div>
                    <div class="form-group">
                        <label>Cuối kỳ (60%) <span class="required">*</span></label>
                        <input type="number" step="0.1" min="0" max="10" name="cuoiKy" value="${diem.cuoiKy}" required placeholder="0 – 10">
                    </div>
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary"><i class="bi bi-check-lg"></i> Lưu</button>
                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/diem"><i class="bi bi-x-lg"></i> Hủy</a>
                    </div>
                </form>
            </div>

        </main>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/layout-scripts.jspf" %>
</body>
</html>
