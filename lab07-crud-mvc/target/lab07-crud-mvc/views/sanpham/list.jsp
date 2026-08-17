<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Quản lý sản phẩm" scope="request"/>
<c:set var="pageSubtitle" value="Danh sách sản phẩm và kho hàng" scope="request"/>
<c:set var="activeNav" value="san-pham" scope="request"/>
<!DOCTYPE html>
<html lang="vi">
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="app-layout">
    <%@ include file="/WEB-INF/jspf/sidebar.jspf" %>
    <div class="main-wrapper">
        <%@ include file="/WEB-INF/jspf/topbar.jspf" %>
        <main class="main-content">

            <div class="page-header">
                <form method="get" action="${pageContext.request.contextPath}/san-pham" class="search-bar" style="flex:1;margin:0;">
                    <div class="input-icon-wrap">
                        <i class="bi bi-search"></i>
                        <input name="keyword" value="${keyword}" placeholder="Tìm theo mã hoặc tên...">
                    </div>
                    <button type="submit" class="btn btn-primary"><i class="bi bi-search"></i> Tìm</button>
                </form>
                <div class="page-header-actions">
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/gio-hang">
                        <i class="bi bi-cart-fill"></i> Giỏ hàng
                    </a>
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/san-pham?action=new">
                        <i class="bi bi-plus-lg"></i> Thêm sản phẩm
                    </a>
                </div>
            </div>

            <div class="table-wrap">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Mã</th>
                            <th>Tên</th>
                            <th>Mô tả</th>
                            <th>Giá</th>
                            <th>Tồn kho</th>
                            <th>Trạng thái</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="sp" items="${dsSanPham}">
                            <tr>
                                <td>${sp.id}</td>
                                <td>${sp.ma}</td>
                                <td>${sp.ten}</td>
                                <td>${sp.moTa}</td>
                                <td><fmt:formatNumber value="${sp.gia}" type="number" groupingUsed="true" maxFractionDigits="0"/> ₫</td>
                                <td>${sp.soLuong}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${sp.soLuong > 0}">
                                            <span class="badge badge-success">Còn hàng</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-danger">Hết hàng</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <div class="td-actions">
                                        <a class="btn btn-icon btn-cart" title="Thêm giỏ" href="${pageContext.request.contextPath}/gio-hang?action=add&id=${sp.id}">
                                            <i class="bi bi-cart-plus-fill"></i>
                                        </a>
                                        <a class="btn btn-icon btn-edit" title="Sửa" href="${pageContext.request.contextPath}/san-pham?action=edit&id=${sp.id}">
                                            <i class="bi bi-pencil-fill"></i>
                                        </a>
                                        <a class="btn btn-icon btn-delete" title="Xóa" href="${pageContext.request.contextPath}/san-pham?action=delete&id=${sp.id}" onclick="return confirm('Xóa sản phẩm này?')">
                                            <i class="bi bi-trash-fill"></i>
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

        </main>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/layout-scripts.jspf" %>
</body>
</html>
