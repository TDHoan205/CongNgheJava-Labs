<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách Sách - Lab 10</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />

    <div class="page-wrapper">
        <div class="page-container">
            <div class="card">
                <div class="card-header">
                    <h1>Danh sách Sách</h1>
                    <a href="${pageContext.request.contextPath}/sach?action=new" class="btn btn-primary">+ Thêm sách</a>
                </div>

                <c:if test="${not empty flash_success}">
                    <div class="alert alert-success">${flash_success}</div>
                </c:if>
                <c:if test="${not empty flash_error}">
                    <div class="alert alert-danger">${flash_error}</div>
                </c:if>

                <div class="toolbar">
                    <form method="get" action="${pageContext.request.contextPath}/sach" class="toolbar-search">
                        <input type="text" name="keyword" value="${keyword}" placeholder="Tìm theo tên, tác giả..." class="form-control">
                        <button type="submit" class="btn btn-secondary">Tìm kiếm</button>
                    </form>
                </div>

                <div class="table-wrap">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Mã</th>
                                <th>Tên sách</th>
                                <th>Tác giả</th>
                                <th>Nhà XB</th>
                                <th>Năm</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty dsSach}">
                                    <tr><td colspan="7" class="empty-state">Không có sách nào.</td></tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="sach" items="${dsSach}">
                                        <tr>
                                            <td>${sach.id}</td>
                                            <td><strong>${sach.maSach}</strong></td>
                                            <td>${sach.tenSach}</td>
                                            <td>${sach.tacGia}</td>
                                            <td>${sach.nhaXuatBan}</td>
                                            <td>${sach.namXuatBan}</td>
                                            <td>
                                                <div class="btn-group">
                                                    <a href="${pageContext.request.contextPath}/sach?action=edit&id=${sach.id}" class="btn btn-sm btn-secondary">Sửa</a>
                                                    <a href="${pageContext.request.contextPath}/sach?action=delete&id=${sach.id}" class="btn btn-sm btn-danger" onclick="return confirm('Xóa sách này?')">Xóa</a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>

                <c:if test="${not empty dsSach}">
                    <p class="text-muted mt-2" style="font-size:0.85rem;">Tổng: ${dsSach.size()} sách</p>
                </c:if>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
</body>
</html>
