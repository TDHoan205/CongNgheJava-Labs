<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/views/layout/header.jsp" />

<div class="card">
    <div class="card-header">
        <div>
            <h1 class="card-title">Danh sách Sinh viên (JPA Repository)</h1>
            <p style="color: var(--text-secondary); margin-top: 0.3rem;">Tổng số bản ghi: <strong>${totalElements}</strong> | Phân trang JPQL (5 dòng/trang)</p>
        </div>
        <a href="${pageContext.request.contextPath}/sinh-vien?action=new" class="btn btn-primary">
            ➕ Thêm Sinh viên mới (Tự động khởi tạo bảng điểm - Transaction Bài 11)
        </a>
    </div>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">
            ✅ ${successMessage}
        </div>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">
            ⚠️ ${errorMessage}
        </div>
    </c:if>

    <!-- Search Form (Bài 4 & Bài 9 - JPQL Search) -->
    <form action="${pageContext.request.contextPath}/sinh-vien" method="get" style="display: flex; gap: 0.8rem; margin-bottom: 1.5rem;">
        <input type="text" name="keyword" value="${keyword}" class="form-control" placeholder="Tìm kiếm theo mã sinh viên, họ tên hoặc lớp..." style="max-width: 400px;">
        <button type="submit" class="btn btn-secondary">🔍 Tìm kiếm</button>
        <c:if test="${not empty keyword}">
            <a href="${pageContext.request.contextPath}/sinh-vien" class="btn btn-secondary">❌ Bỏ lọc</a>
        </c:if>
    </form>

    <div class="table-responsive">
        <table>
            <thead>
                <tr>
                    <th>STT</th>
                    <th>Mã SV</th>
                    <th>Họ và tên</th>
                    <th>Email</th>
                    <th>Lớp học</th>
                    <th>Ngày sinh</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="sv" items="${dsSinhVien}" varStatus="loop">
                    <tr>
                        <td>${(currentPage - 1) * 5 + loop.index + 1}</td>
                        <td><strong style="color: var(--secondary);">${sv.maSinhVien}</strong></td>
                        <td>${sv.hoTen}</td>
                        <td>${sv.email}</td>
                        <td>
                            <span class="badge badge-info">
                                <c:choose>
                                    <c:when test="${not empty sv.lopHoc}">${sv.lopHoc.tenLop}</c:when>
                                    <c:otherwise>${not empty sv.lop ? sv.lop : 'Chưa phân lớp'}</c:otherwise>
                                </c:choose>
                            </span>
                        </td>
                        <td>${sv.ngaySinh}</td>
                        <td style="display: flex; gap: 0.5rem;">
                            <a href="${pageContext.request.contextPath}/sinh-vien?action=edit&id=${sv.id}" class="btn btn-secondary" style="padding: 0.3rem 0.6rem; font-size: 0.8rem;">✏️ Sửa</a>
                            <a href="${pageContext.request.contextPath}/sinh-vien?action=delete&id=${sv.id}" class="btn btn-danger" style="padding: 0.3rem 0.6rem; font-size: 0.8rem;" onclick="return confirm('Bạn có chắc chắn muốn xóa sinh viên ${sv.hoTen}?');">🗑️ Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty dsSinhVien}">
                    <tr>
                        <td colspan="7" style="text-align: center; color: var(--text-secondary); padding: 2rem;">
                            Không tìm thấy dữ liệu sinh viên nào.
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>

    <!-- Phân trang (Bài 9) -->
    <c:if test="${totalPages > 1}">
        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <a href="${pageContext.request.contextPath}/sinh-vien?page=${currentPage - 1}&keyword=${keyword}">&laquo; Trước</a>
            </c:if>
            <c:forEach var="p" begin="1" end="${totalPages}">
                <c:choose>
                    <c:when test="${p == currentPage}">
                        <span class="active">${p}</span>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/sinh-vien?page=${p}&keyword=${keyword}">${p}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${currentPage < totalPages}">
                <a href="${pageContext.request.contextPath}/sinh-vien?page=${currentPage + 1}&keyword=${keyword}">Sau &raquo;</a>
            </c:if>
        </div>
    </c:if>
</div>

<jsp:include page="/views/layout/footer.jsp" />
