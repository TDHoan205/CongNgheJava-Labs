<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lab 9 - Tích hợp JPA, Entity, Repository & Transaction</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/" class="navbar-brand">
            ⚡ <span>EAUT</span> Lab 9 JPA
        </a>
        <ul class="nav-links">
            <li><a href="${pageContext.request.contextPath}/">Trang chủ</a></li>
            <li><a href="${pageContext.request.contextPath}/sinh-vien">Sinh viên</a></li>
            <li><a href="${pageContext.request.contextPath}/lop-hoc">Lớp học</a></li>
            <li><a href="${pageContext.request.contextPath}/diem">Điểm môn học</a></li>
            <li><a href="${pageContext.request.contextPath}/sach">Quản lý Sách</a></li>
            <li><a href="${pageContext.request.contextPath}/san-pham">Sản phẩm</a></li>
        </ul>
    </nav>
    <div class="container">
