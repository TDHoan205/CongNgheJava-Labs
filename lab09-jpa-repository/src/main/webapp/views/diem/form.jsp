<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/views/layout/header.jsp" />

<div class="card" style="max-width: 600px; margin: 0 auto;">
    <div class="card-header">
        <h1 class="card-title">${not empty diem.id ? '✏️ Cập nhật Điểm' : '➕ Nhập Điểm Mới'}</h1>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">⚠️ ${errorMessage}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/diem" method="post">
        <input type="hidden" name="id" value="${diem.id}">

        <div class="form-group">
            <label>Chọn Sinh viên (<span style="color: var(--danger);">*</span>):</label>
            <select name="sinhVienId" class="form-control" required ${not empty diem.id ? 'disabled' : ''}>
                <option value="">-- Chọn sinh viên --</option>
                <c:forEach var="sv" items="${dsSinhVien}">
                    <option value="${sv.id}" ${diem.sinhVien != null && diem.sinhVien.id == sv.id ? 'selected' : ''}>
                        ${sv.hoTen} (${sv.maSinhVien})
                    </option>
                </c:forEach>
            </select>
            <c:if test="${not empty diem.id}">
                <input type="hidden" name="sinhVienId" value="${diem.sinhVien.id}">
            </c:if>
        </div>

        <div class="form-group">
            <label>Chọn Môn học (<span style="color: var(--danger);">*</span>):</label>
            <select name="monHocId" class="form-control" required ${not empty diem.id ? 'disabled' : ''}>
                <option value="">-- Chọn môn học --</option>
                <c:forEach var="mh" items="${dsMonHoc}">
                    <option value="${mh.id}" ${diem.monHoc != null && diem.monHoc.id == mh.id ? 'selected' : ''}>
                        ${mh.tenMon} (${mh.maMon})
                    </option>
                </c:forEach>
            </select>
            <c:if test="${not empty diem.id}">
                <input type="hidden" name="monHocId" value="${diem.monHoc.id}">
            </c:if>
        </div>

        <div class="form-group">
            <label>Điểm quá trình (Hệ số 30%):</label>
            <input type="number" step="0.1" min="0" max="10" name="diemQuaTrinh" value="${diem.diemQuaTrinh}" class="form-control" required>
        </div>

        <div class="form-group">
            <label>Điểm thi kết thúc học phần (Hệ số 70%):</label>
            <input type="number" step="0.1" min="0" max="10" name="diemThi" value="${diem.diemThi}" class="form-control" required>
        </div>

        <div style="display: flex; gap: 1rem; margin-top: 1.5rem;">
            <button type="submit" class="btn btn-primary">💾 Lưu Điểm</button>
            <a href="${pageContext.request.contextPath}/diem" class="btn btn-secondary">↩️ Quay lại</a>
        </div>
    </form>
</div>

<jsp:include page="/views/layout/footer.jsp" />
