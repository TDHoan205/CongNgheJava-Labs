<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/views/layout/header.jsp" />

<div class="card">
    <div class="card-header">
        <div>
            <h1 class="card-title">Danh sách Sinh viên thuộc lớp: <span style="color: var(--secondary);">${lopHoc.tenLop}</span> (${lopHoc.maLop})</h1>
            <p style="color: var(--text-secondary); margin-top: 0.3rem;">Khóa học: <strong>${lopHoc.khoaHoc}</strong> | Sĩ số: <strong>${dsSinhVien.size()}</strong> sinh viên</p>
        </div>
        <a href="${pageContext.request.contextPath}/lop-hoc" class="btn btn-secondary">↩️ Quay lại danh sách Lớp</a>
    </div>

    <div class="table-responsive">
        <table>
            <thead>
                <tr>
                    <th>STT</th>
                    <th>Mã SV</th>
                    <th>Họ và tên</th>
                    <th>Email</th>
                    <th>Ngày sinh</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="sv" items="${dsSinhVien}" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td><strong style="color: var(--secondary);">${sv.maSinhVien}</strong></td>
                        <td>${sv.hoTen}</td>
                        <td>${sv.email}</td>
                        <td>${sv.ngaySinh}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty dsSinhVien}">
                    <tr>
                        <td colspan="5" style="text-align: center; color: var(--text-secondary); padding: 2rem;">Chưa có sinh viên nào thuộc lớp học này.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/views/layout/footer.jsp" />
