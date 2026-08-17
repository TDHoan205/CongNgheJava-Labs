<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="vn.edu.eaut.lab7.repository.*" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    int countSV = new SinhVienRepository().findAll().size();
    int countSach = new SachRepository().findAll().size();
    int countSP = new SanPhamRepository().findAll().size();
    int countLop = new LopHocRepository().findAll().size();
    int countDiem = new DiemRepository().findAll().size();
    request.setAttribute("countSV", countSV);
    request.setAttribute("countSach", countSach);
    request.setAttribute("countSP", countSP);
    request.setAttribute("countLop", countLop);
    request.setAttribute("countDiem", countDiem);
%>
<c:set var="pageTitle" value="Dashboard" scope="request"/>
<c:set var="pageSubtitle" value="Tổng quan hệ thống quản lý học tập" scope="request"/>
<c:set var="activeNav" value="dashboard" scope="request"/>
<!DOCTYPE html>
<html lang="vi">
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="app-layout">
    <%@ include file="/WEB-INF/jspf/sidebar.jspf" %>
    <div class="main-wrapper">
        <%@ include file="/WEB-INF/jspf/topbar.jspf" %>
        <main class="main-content">

            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-icon blue"><i class="bi bi-people-fill"></i></div>
                    <div class="stat-info">
                        <div class="stat-value">${countSV}</div>
                        <div class="stat-label">Sinh viên</div>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon green"><i class="bi bi-book-fill"></i></div>
                    <div class="stat-info">
                        <div class="stat-value">${countSach}</div>
                        <div class="stat-label">Sách</div>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon amber"><i class="bi bi-box-seam-fill"></i></div>
                    <div class="stat-info">
                        <div class="stat-value">${countSP}</div>
                        <div class="stat-label">Sản phẩm</div>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon purple"><i class="bi bi-building"></i></div>
                    <div class="stat-info">
                        <div class="stat-value">${countLop}</div>
                        <div class="stat-label">Lớp học</div>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon cyan"><i class="bi bi-clipboard-data-fill"></i></div>
                    <div class="stat-info">
                        <div class="stat-value">${countDiem}</div>
                        <div class="stat-label">Bản ghi điểm</div>
                    </div>
                </div>
            </div>

            <div class="card quick-actions">
                <h3><i class="bi bi-lightning-fill"></i> Thao tác nhanh</h3>
                <div class="action-grid">
                    <a class="action-link" href="${pageContext.request.contextPath}/sinh-vien?action=new">
                        <i class="bi bi-person-plus-fill"></i> Thêm sinh viên
                    </a>
                    <a class="action-link" href="${pageContext.request.contextPath}/sach?action=new">
                        <i class="bi bi-book-half"></i> Thêm sách
                    </a>
                    <a class="action-link" href="${pageContext.request.contextPath}/san-pham?action=new">
                        <i class="bi bi-plus-square-fill"></i> Thêm sản phẩm
                    </a>
                    <a class="action-link" href="${pageContext.request.contextPath}/lop-hoc?action=new">
                        <i class="bi bi-door-open-fill"></i> Thêm lớp học
                    </a>
                    <a class="action-link" href="${pageContext.request.contextPath}/diem?action=new">
                        <i class="bi bi-pencil-square"></i> Nhập điểm
                    </a>
                    <a class="action-link" href="${pageContext.request.contextPath}/gio-hang">
                        <i class="bi bi-cart-fill"></i> Xem giỏ hàng
                    </a>
                </div>
            </div>

        </main>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/layout-scripts.jspf" %>
</body>
</html>
