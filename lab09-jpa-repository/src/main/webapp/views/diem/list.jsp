<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/views/layout/header.jsp" />

<div class="card">
    <div class="card-header">
        <div>
            <h1 class="card-title">Quản lý Bảng điểm Sinh viên (Entity MonHoc & Diem)</h1>
            <p style="color: var(--text-secondary); margin-top: 0.3rem;">Tự động tính Điểm tổng kết = DQT * 0.3 + ĐThi * 0.7 & Xếp loại</p>
        </div>
        <a href="${pageContext.request.contextPath}/diem?action=new" class="btn btn-primary">➕ Nhập điểm mới</a>
    </div>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">✅ ${successMessage}</div>
    </c:if>

    <!-- Search Form -->
    <form action="${pageContext.request.contextPath}/diem" method="get" style="display: flex; gap: 0.8rem; margin-bottom: 1.5rem;">
        <input type="text" name="keyword" value="${keyword}" class="form-control" placeholder="Tìm kiếm theo mã/tên SV, môn học hoặc xếp loại..." style="max-width: 400px;">
        <button type="submit" class="btn btn-secondary">🔍 Tìm kiếm</button>
    </form>

    <div class="table-responsive">
        <table>
            <thead>
                <tr>
                    <th>STT</th>
                    <th>Sinh viên</th>
                    <th>Môn học</th>
                    <th>Điểm quá trình (30%)</th>
                    <th>Điểm thi (70%)</th>
                    <th>Điểm tổng kết</th>
                    <th>Xếp loại</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="d" items="${dsDiem}" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td>
                            <strong>${d.sinhVien.hoTen}</strong>
                            <div style="font-size: 0.8rem; color: var(--text-secondary);">${d.sinhVien.maSinhVien}</div>
                        </td>
                        <td>${d.monHoc.tenMon} (${d.monHoc.maMon})</td>
                        <td>${d.diemQuaTrinh}</td>
                        <td>${d.diemThi}</td>
                        <td><strong style="color: var(--secondary); font-size: 1.05rem;">${d.diemTongKet}</strong></td>
                        <td>
                            <c:choose>
                                <c:when test="${d.xepLoai == 'Xuất sắc' || d.xepLoai == 'Giỏi'}">
                                    <span class="badge badge-success">🏆 ${d.xepLoai}</span>
                                </c:when>
                                <c:when test="${d.xepLoai == 'Khá'}">
                                    <span class="badge badge-info">👍 ${d.xepLoai}</span>
                                </c:when>
                                <c:when test="${d.xepLoai == 'Trung bình'}">
                                    <span class="badge badge-warning">👌 ${d.xepLoai}</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-danger">⚠️ ${d.xepLoai}</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="display: flex; gap: 0.5rem;">
                            <a href="${pageContext.request.contextPath}/diem?action=edit&id=${d.id}" class="btn btn-secondary" style="padding: 0.3rem 0.6rem; font-size: 0.8rem;">✏️ Sửa</a>
                            <a href="${pageContext.request.contextPath}/diem?action=delete&id=${d.id}" class="btn btn-danger" style="padding: 0.3rem 0.6rem; font-size: 0.8rem;" onclick="return confirm('Bạn có chắc chắn muốn xóa điểm này?');">🗑️ Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty dsDiem}">
                    <tr>
                        <td colspan="8" style="text-align: center; color: var(--text-secondary); padding: 2rem;">Chưa có dữ liệu bảng điểm.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/views/layout/footer.jsp" />
