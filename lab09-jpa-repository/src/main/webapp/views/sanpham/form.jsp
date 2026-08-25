<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/views/layout/header.jsp" />

<div class="card" style="max-width: 600px; margin: 0 auto;">
    <div class="card-header">
        <h1 class="card-title">${not empty sanPham.id ? '✏️ Cập nhật Sản phẩm' : '➕ Thêm Sản phẩm Mới'}</h1>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">⚠️ ${errorMessage}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/san-pham" method="post">
        <input type="hidden" name="id" value="${sanPham.id}">

        <div class="form-group">
            <label>Mã Sản Phẩm (<span style="color: var(--danger);">*</span>):</label>
            <input type="text" name="maSp" value="${sanPham.maSp}" class="form-control" placeholder="Ví dụ: SP001" required>
        </div>

        <div class="form-group">
            <label>Tên Sản Phẩm (<span style="color: var(--danger);">*</span>):</label>
            <input type="text" name="tenSp" value="${sanPham.tenSp}" class="form-control" placeholder="Nhập tên sản phẩm" required>
        </div>

        <div class="form-group">
            <label>Loại Sản Phẩm:</label>
            <input type="text" name="loaiSp" value="${sanPham.loaiSp}" class="form-control" placeholder="Ví dụ: Điện tử, Phụ kiện...">
        </div>

        <div class="form-group">
            <label>Đơn giá (VNĐ):</label>
            <input type="number" step="1000" name="gia" value="${sanPham.gia}" class="form-control" required>
        </div>

        <div class="form-group">
            <label>Số lượng tồn kho:</label>
            <input type="number" name="soLuong" value="${sanPham.soLuong}" class="form-control" required>
        </div>

        <div style="display: flex; gap: 1rem; margin-top: 1.5rem;">
            <button type="submit" class="btn btn-primary">💾 Lưu Sản phẩm</button>
            <a href="${pageContext.request.contextPath}/san-pham" class="btn btn-secondary">↩️ Quay lại</a>
        </div>
    </form>
</div>

<jsp:include page="/views/layout/footer.jsp" />
