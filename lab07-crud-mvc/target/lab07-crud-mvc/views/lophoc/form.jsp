<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${empty lop ? 'Thêm lớp học' : 'Sửa lớp học'}" scope="request"/>
<c:set var="pageSubtitle" value="Nhập thông tin lớp học" scope="request"/>
<c:set var="activeNav" value="lop-hoc" scope="request"/>
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
                <form method="post" action="${pageContext.request.contextPath}/lop-hoc" class="form-stack">
                    <input type="hidden" name="id" value="${lop.id}">
                    <div class="form-group">
                        <label>Mã lớp <span class="required">*</span></label>
                        <input name="maLop" value="${lop.maLop}" required placeholder="VD: DCCNTT15.10.1">
                    </div>
                    <div class="form-group">
                        <label>Tên lớp <span class="required">*</span></label>
                        <input name="tenLop" value="${lop.tenLop}" required placeholder="Tên lớp học">
                    </div>
                    <div class="form-group">
                        <label>Cố vấn học tập</label>
                        <input name="coVanHocTap" value="${lop.coVanHocTap}" placeholder="Tên cố vấn">
                    </div>
                    <div class="form-group">
                        <label>Số lượng sinh viên <span class="required">*</span></label>
                        <input type="number" min="0" name="soLuongSinhVien" value="${lop.soLuongSinhVien}" required placeholder="0">
                    </div>
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary"><i class="bi bi-check-lg"></i> Lưu</button>
                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/lop-hoc"><i class="bi bi-x-lg"></i> Hủy</a>
                    </div>
                </form>
            </div>

        </main>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/layout-scripts.jspf" %>
</body>
</html>
