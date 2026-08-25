<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/views/layout/header.jsp" />

<div class="card">
    <div class="card-header">
        <div>
            <h1 class="card-title">Danh sách Lớp học (Entity LopHoc)</h1>
            <p style="color: var(--text-secondary); margin-top: 0.3rem;">Quản lý các lớp và danh sách sinh viên theo lớp (Quan hệ 1-N)</p>
        </div>
        <a href="${pageContext.request.contextPath}/lop-hoc?action=new" class="btn btn-primary">➕ Thêm Lớp học mới</a>
    </div>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">✅ ${successMessage}</div>
    </c:if>

    <div class="table-responsive">
        <table>
            <thead>
                <tr>
                    <th>STT</th>
                    <th>Mã lớp</th>
                    <th>Tên lớp học</th>
                    <th>Khóa học</th>
                    <th>Số sinh viên</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="l" items="${dsLopHoc}" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td><strong style="color: var(--secondary);">${l.maLop}</strong></td>
                        <td>${l.tenLop}</td>
                        <td><span class="badge badge-info">${l.khoaHoc}</span></td>
                        <td><strong>${l.dsSinhVien.size()}</strong> sinh viên</td>
                        <td style="display: flex; gap: 0.5rem;">
                            <a href="${pageContext.request.contextPath}/lop-hoc?action=detail&id=${l.id}" class="btn btn-secondary" style="padding: 0.3rem 0.6rem; font-size: 0.8rem;">👁️ Xem SV</a>
                            <a href="${pageContext.request.contextPath}/lop-hoc?action=edit&id=${l.id}" class="btn btn-secondary" style="padding: 0.3rem 0.6rem; font-size: 0.8rem;">✏️ Sửa</a>
                            <a href="${pageContext.request.contextPath}/lop-hoc?action=delete&id=${l.id}" class="btn btn-danger" style="padding: 0.3rem 0.6rem; font-size: 0.8rem;" onclick="return confirm('Bạn có chắc chắn muốn xóa lớp ${l.tenLop}?');">🗑️ Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty dsLopHoc}">
                    <tr>
                        <td colspan="6" style="text-align: center; color: var(--text-secondary); padding: 2rem;">Chưa có dữ liệu lớp học.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/views/layout/footer.jsp" />
