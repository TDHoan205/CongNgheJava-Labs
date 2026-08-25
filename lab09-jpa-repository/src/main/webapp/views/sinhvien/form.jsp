<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/views/layout/header.jsp" />

<div class="card" style="max-width: 650px; margin: 0 auto;">
    <div class="card-header">
        <h1 class="card-title">
            <c:choose>
                <c:when test="${not empty sinhVien.id}">✏️ Cập nhật Sinh viên</c:when>
                <c:otherwise>➕ Thêm mới Sinh viên (JPA Transaction)</c:otherwise>
            </c:choose>
        </h1>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">
            ⚠️ ${errorMessage}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/sinh-vien" method="post">
        <input type="hidden" name="id" value="${sinhVien.id}">

        <div class="form-group">
            <label>Mã Sinh Viên (<span style="color: var(--danger);">*</span>):</label>
            <input type="text" name="maSinhVien" value="${sinhVien.maSinhVien}" class="form-control" placeholder="Ví dụ: SV2030022" required ${not empty sinhVien.id ? 'readonly' : ''}>
        </div>

        <div class="form-group">
            <label>Họ và Tên (<span style="color: var(--danger);">*</span>):</label>
            <input type="text" name="hoTen" value="${sinhVien.hoTen}" class="form-control" placeholder="Nhập họ và tên sinh viên" required>
        </div>

        <div class="form-group">
            <label>Email:</label>
            <input type="email" name="email" value="${sinhVien.email}" class="form-control" placeholder="vi_du@eaut.edu.vn">
        </div>

        <div class="form-group">
            <label>Lớp học (Entity LopHoc):</label>
            <select name="lopId" class="form-control">
                <option value="">-- Chọn lớp học --</option>
                <c:forEach var="l" items="${dsLopHoc}">
                    <option value="${l.id}" ${sinhVien.lopHoc != null && sinhVien.lopHoc.id == l.id ? 'selected' : ''}>
                        ${l.tenLop} (${l.maLop})
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label>Ngày sinh:</label>
            <input type="date" name="ngaySinh" value="${sinhVien.ngaySinh}" class="form-control">
        </div>

        <div style="display: flex; gap: 1rem; margin-top: 1.5rem;">
            <button type="submit" class="btn btn-primary">💾 Lưu thông tin</button>
            <a href="${pageContext.request.contextPath}/sinh-vien" class="btn btn-secondary">↩️ Quay lại</a>
        </div>
    </form>
</div>

<jsp:include page="/views/layout/footer.jsp" />
