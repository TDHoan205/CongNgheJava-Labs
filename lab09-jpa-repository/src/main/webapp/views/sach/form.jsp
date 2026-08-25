<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/views/layout/header.jsp" />

<div class="card" style="max-width: 600px; margin: 0 auto;">
    <div class="card-header">
        <h1 class="card-title">${not empty sach.id ? '✏️ Cập nhật Sách' : '➕ Thêm Sách Mới'}</h1>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">⚠️ ${errorMessage}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/sach" method="post">
        <input type="hidden" name="id" value="${sach.id}">

        <div class="form-group">
            <label>Mã Sách (<span style="color: var(--danger);">*</span>):</label>
            <input type="text" name="maSach" value="${sach.maSach}" class="form-control" placeholder="Ví dụ: S001" required>
        </div>

        <div class="form-group">
            <label>Tên Sách (<span style="color: var(--danger);">*</span>):</label>
            <input type="text" name="tenSach" value="${sach.tenSach}" class="form-control" placeholder="Nhập tên cuốn sách" required>
        </div>

        <div class="form-group">
            <label>Tác giả:</label>
            <input type="text" name="tacGia" value="${sach.tacGia}" class="form-control" placeholder="Tên tác giả">
        </div>

        <div class="form-group">
            <label>Đơn giá (VNĐ):</label>
            <input type="number" step="1000" name="gia" value="${sach.gia}" class="form-control" required>
        </div>

        <div class="form-group">
            <label>Số lượng kho:</label>
            <input type="number" name="soLuong" value="${sach.soLuong}" class="form-control" required>
        </div>

        <div style="display: flex; gap: 1rem; margin-top: 1.5rem;">
            <button type="submit" class="btn btn-primary">💾 Lưu Sách</button>
            <a href="${pageContext.request.contextPath}/sach" class="btn btn-secondary">↩️ Quay lại</a>
        </div>
    </form>
</div>

<jsp:include page="/views/layout/footer.jsp" />
