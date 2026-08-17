<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Danh sách sinh viên" scope="request"/>
<c:set var="pageSubtitle" value="Quản lý thông tin sinh viên" scope="request"/>
<c:set var="activeNav" value="sinh-vien" scope="request"/>
<!DOCTYPE html>
<html lang="vi">
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="app-layout">
    <%@ include file="/WEB-INF/jspf/sidebar.jspf" %>
    <div class="main-wrapper">
        <%@ include file="/WEB-INF/jspf/topbar.jspf" %>
        <main class="main-content">

            <c:if test="${not empty param.error}">
                <div class="alert alert-error"><i class="bi bi-exclamation-circle-fill"></i> ${param.error}</div>
            </c:if>

            <div class="page-header">
                <form method="get" action="${pageContext.request.contextPath}/sinh-vien" class="search-bar" style="flex:1;margin:0;">
                    <div class="input-icon-wrap">
                        <i class="bi bi-search"></i>
                        <input name="keyword" value="${keyword}" placeholder="Tìm theo tên hoặc lớp...">
                    </div>
                    <button type="submit" class="btn btn-primary"><i class="bi bi-search"></i> Tìm</button>
                </form>
                <div class="page-header-actions">
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/sinh-vien?action=new">
                        <i class="bi bi-person-plus-fill"></i> Thêm sinh viên
                    </a>
                </div>
            </div>

            <div class="table-wrap">
                <table>
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
                        <c:forEach var="sv" items="${dsSinhVien}">
                            <tr>
                                <td>${sv.id}</td>
                                <td>${sv.maSinhVien}</td>
                                <td>
                                    <a class="td-link" href="${pageContext.request.contextPath}/sinh-vien?action=detail&id=${sv.id}">${sv.hoTen}</a>
                                </td>
                                <td>${sv.email}</td>
                                <td>${sv.lop}</td>
                                <td>
                                    <div class="td-actions">
                                        <a class="btn btn-icon btn-view" title="Chi tiết" href="${pageContext.request.contextPath}/sinh-vien?action=detail&id=${sv.id}">
                                            <i class="bi bi-eye-fill"></i>
                                        </a>
                                        <a class="btn btn-icon btn-edit" title="Sửa" href="${pageContext.request.contextPath}/sinh-vien?action=edit&id=${sv.id}">
                                            <i class="bi bi-pencil-fill"></i>
                                        </a>
                                        <a class="btn btn-icon btn-delete" title="Xóa" href="${pageContext.request.contextPath}/sinh-vien?action=delete&id=${sv.id}" onclick="return confirm('Xóa sinh viên này?')">
                                            <i class="bi bi-trash-fill"></i>
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="pagination">
                <c:if test="${page > 1}">
                    <a class="btn btn-secondary btn-sm" href="${pageContext.request.contextPath}/sinh-vien?page=${page-1}&keyword=${keyword}">
                        <i class="bi bi-chevron-left"></i> Trước
                    </a>
                </c:if>
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <c:choose>
                        <c:when test="${i == page}"><span class="page-current">${i}</span></c:when>
                        <c:otherwise>
                            <a class="btn btn-secondary btn-sm" href="${pageContext.request.contextPath}/sinh-vien?page=${i}&keyword=${keyword}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${page < totalPages}">
                    <a class="btn btn-secondary btn-sm" href="${pageContext.request.contextPath}/sinh-vien?page=${page+1}&keyword=${keyword}">
                        Tiếp <i class="bi bi-chevron-right"></i>
                    </a>
                </c:if>
                <span class="page-info">Trang ${page} / ${totalPages}</span>
            </div>

        </main>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/layout-scripts.jspf" %>
</body>
</html>
