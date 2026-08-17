<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Quản lý lớp học" scope="request"/>
<c:set var="pageSubtitle" value="Danh sách lớp và cố vấn học tập" scope="request"/>
<c:set var="activeNav" value="lop-hoc" scope="request"/>
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
                <form method="get" action="${pageContext.request.contextPath}/lop-hoc" class="search-bar" style="flex:1;margin:0;">
                    <div class="input-icon-wrap">
                        <i class="bi bi-search"></i>
                        <input name="keyword" value="${keyword}" placeholder="Tìm mã hoặc tên lớp...">
                    </div>
                    <button type="submit" class="btn btn-primary"><i class="bi bi-search"></i> Tìm</button>
                </form>
                <div class="page-header-actions">
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/lop-hoc?action=new">
                        <i class="bi bi-plus-lg"></i> Thêm lớp
                    </a>
                </div>
            </div>

            <div class="table-wrap">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Mã lớp</th>
                            <th>Tên lớp</th>
                            <th>Cố vấn học tập</th>
                            <th>Số SV</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="l" items="${dsLop}">
                            <tr>
                                <td>${l.id}</td>
                                <td>${l.maLop}</td>
                                <td>${l.tenLop}</td>
                                <td>${l.coVanHocTap}</td>
                                <td><span class="badge badge-count">${l.soLuongSinhVien} SV</span></td>
                                <td>
                                    <div class="td-actions">
                                        <a class="btn btn-icon btn-edit" title="Sửa" href="${pageContext.request.contextPath}/lop-hoc?action=edit&id=${l.id}">
                                            <i class="bi bi-pencil-fill"></i>
                                        </a>
                                        <a class="btn btn-icon btn-delete" title="Xóa" href="${pageContext.request.contextPath}/lop-hoc?action=delete&id=${l.id}" onclick="return confirm('Xóa lớp học này?')">
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
