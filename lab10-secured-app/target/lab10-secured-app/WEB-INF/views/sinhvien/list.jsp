<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách Sinh viên - Lab 10</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />

    <div class="page-wrapper">
        <div class="page-container">
            <div class="card">
                <div class="card-header">
                    <h1>Danh sách Sinh viên</h1>
                    <a href="${pageContext.request.contextPath}/sinh-vien?action=new" class="btn btn-primary">+ Thêm sinh viên</a>
                </div>

                <c:if test="${not empty flash_success}">
                    <div class="alert alert-success">${flash_success}</div>
                </c:if>
                <c:if test="${not empty flash_error}">
                    <div class="alert alert-danger">${flash_error}</div>
                </c:if>

                <div class="toolbar">
                    <form method="get" action="${pageContext.request.contextPath}/sinh-vien" class="toolbar-search">
                        <input type="text" name="keyword" value="${keyword}" placeholder="Tìm theo tên, lớp, mã..." class="form-control">
                        <button type="submit" class="btn btn-secondary">Tìm kiếm</button>
                    </form>
                </div>

                <div class="table-wrap">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Mã SV</th>
                                <th>Họ tên</th>
                                <th>Email</th>
                                <th>Lớp</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty dsSV}">
                                    <tr><td colspan="6" class="empty-state">Không có sinh viên nào.</td></tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="sv" items="${dsSV}">
                                        <tr>
                                            <td>${sv.id}</td>
                                            <td><strong>${sv.maSinhVien}</strong></td>
                                            <td>${sv.hoTen}</td>
                                            <td>${sv.email}</td>
                                            <td>${sv.lop}</td>
                                            <td>
                                                <div class="btn-group">
                                                    <a href="${pageContext.request.contextPath}/sinh-vien?action=edit&id=${sv.id}" class="btn btn-sm btn-secondary">Sửa</a>
                                                    <a href="${pageContext.request.contextPath}/sinh-vien?action=delete&id=${sv.id}" class="btn btn-sm btn-danger" onclick="return confirm('Xóa sinh viên này?')">Xóa</a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>

                <c:if test="${not empty dsSV}">
                    <p class="text-muted mt-2" style="font-size:0.85rem;">Tổng: ${dsSV.size()} sinh viên</p>
                </c:if>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
</body>
</html>
