<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/views/layout/header.jsp" />

<div class="card" style="max-width: 600px; margin: 0 auto;">
    <div class="card-header">
        <h1 class="card-title">${not empty lopHoc.id ? '✏️ Cập nhật Lớp học' : '➕ Thêm Lớp học mới'}</h1>
    </div>

    <form action="${pageContext.request.contextPath}/lop-hoc" method="post">
        <input type="hidden" name="id" value="${lopHoc.id}">

        <div class="form-group">
            <label>Mã Lớp (<span style="color: var(--danger);">*</span>):</label>
            <input type="text" name="maLop" value="${lopHoc.maLop}" class="form-control" placeholder="Ví dụ: CNTT14-01" required>
        </div>

        <div class="form-group">
            <label>Tên Lớp Học (<span style="color: var(--danger);">*</span>):</label>
            <input type="text" name="tenLop" value="${lopHoc.tenLop}" class="form-control" placeholder="Ví dụ: Công nghệ thông tin 1" required>
        </div>

        <div class="form-group">
            <label>Khóa Học:</label>
            <input type="text" name="khoaHoc" value="${lopHoc.khoaHoc}" class="form-control" placeholder="Ví dụ: K14">
        </div>

        <div style="display: flex; gap: 1rem; margin-top: 1.5rem;">
            <button type="submit" class="btn btn-primary">💾 Lưu Lớp học</button>
            <a href="${pageContext.request.contextPath}/lop-hoc" class="btn btn-secondary">↩️ Quay lại</a>
        </div>
    </form>
</div>

<jsp:include page="/views/layout/footer.jsp" />
