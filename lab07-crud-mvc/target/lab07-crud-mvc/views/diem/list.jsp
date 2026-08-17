<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Quản lý điểm" scope="request"/>
<c:set var="pageSubtitle" value="Bảng điểm sinh viên" scope="request"/>
<c:set var="activeNav" value="diem" scope="request"/>
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
                <div></div>
                <div class="page-header-actions">
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/diem?action=new">
                        <i class="bi bi-plus-lg"></i> Nhập điểm
                    </a>
                </div>
            </div>

            <div class="info-banner">
                <i class="bi bi-info-circle-fill"></i>
                <span>Công thức: <strong>Tổng kết = 10% chuyên cần + 30% giữa kỳ + 60% cuối kỳ</strong></span>
            </div>

            <div class="table-wrap">
                <table>
                    <thead>
                        <tr>
                            <th>Mã SV</th>
                            <th>Họ tên</th>
                            <th>Chuyên cần</th>
                            <th>Giữa kỳ</th>
                            <th>Cuối kỳ</th>
                            <th>Tổng kết</th>
                            <th>Xếp loại</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="d" items="${dsDiem}">
                            <tr>
                                <td>${d.maSinhVien}</td>
                                <td>${d.hoTen}</td>
                                <td><fmt:formatNumber value="${d.chuyenCan}" maxFractionDigits="1"/></td>
                                <td><fmt:formatNumber value="${d.giuaKy}" maxFractionDigits="1"/></td>
                                <td><fmt:formatNumber value="${d.cuoiKy}" maxFractionDigits="1"/></td>
                                <td><strong><fmt:formatNumber value="${d.tongKet}" maxFractionDigits="2"/></strong></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${d.xepLoai == 'A'}"><span class="badge badge-grade-a">A</span></c:when>
                                        <c:when test="${d.xepLoai == 'B'}"><span class="badge badge-grade-b">B</span></c:when>
                                        <c:when test="${d.xepLoai == 'C'}"><span class="badge badge-grade-c">C</span></c:when>
                                        <c:when test="${d.xepLoai == 'D'}"><span class="badge badge-grade-d">D</span></c:when>
                                        <c:otherwise><span class="badge badge-grade-f">${d.xepLoai}</span></c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <div class="td-actions">
                                        <a class="btn btn-icon btn-edit" title="Sửa" href="${pageContext.request.contextPath}/diem?action=edit&id=${d.id}">
                                            <i class="bi bi-pencil-fill"></i>
                                        </a>
                                        <a class="btn btn-icon btn-delete" title="Xóa" href="${pageContext.request.contextPath}/diem?action=delete&id=${d.id}" onclick="return confirm('Xóa bản ghi điểm này?')">
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
