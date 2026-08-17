<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Quản lý sách" scope="request"/>
<c:set var="pageSubtitle" value="Danh sách sách trong thư viện" scope="request"/>
<c:set var="activeNav" value="sach" scope="request"/>
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
                <form method="get" action="${pageContext.request.contextPath}/sach" class="search-bar" style="flex:1;margin:0;">
                    <div class="input-icon-wrap">
                        <i class="bi bi-search"></i>
                        <input name="keyword" value="${keyword}" placeholder="Tìm tên hoặc tác giả...">
                    </div>
                    <button type="submit" class="btn btn-primary"><i class="bi bi-search"></i> Tìm</button>
                </form>
                <div class="page-header-actions">
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/sach?action=new">
                        <i class="bi bi-plus-lg"></i> Thêm sách
                    </a>
                </div>
            </div>

            <div class="table-wrap">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Mã</th>
                            <th>Tên sách</th>
                            <th>Tác giả</th>
                            <th>NXB</th>
                            <th>Năm</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${dsSach}">
                            <tr>
                                <td>${s.id}</td>
                                <td>${s.maSach}</td>
                                <td>${s.tenSach}</td>
                                <td>${s.tacGia}</td>
                                <td>${s.nhaXuatBan}</td>
                                <td>${s.namXuatBan}</td>
                                <td>
                                    <div class="td-actions">
                                        <a class="btn btn-icon btn-edit" title="Sửa" href="${pageContext.request.contextPath}/sach?action=edit&id=${s.id}">
                                            <i class="bi bi-pencil-fill"></i>
                                        </a>
                                        <a class="btn btn-icon btn-delete" title="Xóa" href="${pageContext.request.contextPath}/sach?action=delete&id=${s.id}" onclick="return confirm('Xóa sách này?')">
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
