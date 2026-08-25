<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách Sản phẩm - Lab 10</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />

    <div class="page-wrapper">
        <div class="page-container">
            <div class="card">
                <div class="card-header">
                    <h1>Danh sách Sản phẩm</h1>
                    <a href="${pageContext.request.contextPath}/san-pham?action=new" class="btn btn-primary">+ Thêm sản phẩm</a>
                </div>

                <c:if test="${not empty flash_success}">
                    <div class="alert alert-success">${flash_success}</div>
                </c:if>
                <c:if test="${not empty flash_error}">
                    <div class="alert alert-danger">${flash_error}</div>
                </c:if>

                <div class="toolbar">
                    <form method="get" action="${pageContext.request.contextPath}/san-pham" class="toolbar-search">
                        <input type="text" name="keyword" value="${keyword}" placeholder="Tìm theo tên, mã..." class="form-control">
                        <button type="submit" class="btn btn-secondary">Tìm kiếm</button>
                    </form>
                </div>

                <div class="table-wrap">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Mã</th>
                                <th>Tên sản phẩm</th>
                                <th>Giá (VNĐ)</th>
                                <th>SL</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty dsSP}">
                                    <tr><td colspan="6" class="empty-state">Không có sản phẩm nào.</td></tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="sp" items="${dsSP}">
                                        <tr>
                                            <td>${sp.id}</td>
                                            <td><strong>${sp.ma}</strong></td>
                                            <td>${sp.ten}</td>
                                            <td class="text-success">${sp.gia}đ</td>
                                            <td>${sp.soLuong}</td>
                                            <td>
                                                <div class="btn-group">
                                                    <a href="${pageContext.request.contextPath}/san-pham?action=edit&id=${sp.id}" class="btn btn-sm btn-secondary">Sửa</a>
                                                    <a href="${pageContext.request.contextPath}/san-pham?action=delete&id=${sp.id}" class="btn btn-sm btn-danger" onclick="return confirm('Xóa sản phẩm này?')">Xóa</a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>

                <c:if test="${not empty dsSP}">
                    <p class="text-muted mt-2" style="font-size:0.85rem;">Tổng: ${dsSP.size()} sản phẩm</p>
                </c:if>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
</body>
</html>
