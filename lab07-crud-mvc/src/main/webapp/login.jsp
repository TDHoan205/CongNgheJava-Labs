<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<% request.setAttribute("pageTitle", "Đăng nhập"); %>
<!DOCTYPE html>
<html lang="vi">
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body class="login-page">
<div class="login-card">
    <div class="login-brand">
        <div class="brand-icon"><i class="bi bi-mortarboard-fill"></i></div>
        <h2>Đăng nhập quản trị</h2>
        <p>Modern Academic Management System</p>
    </div>

    <% if (request.getParameter("error") != null) { %>
    <div class="alert alert-error">
        <i class="bi bi-exclamation-circle-fill"></i>
        ${param.error}
    </div>
    <% } %>

    <form method="post" action="${pageContext.request.contextPath}/login" class="form-stack">
        <div class="form-group">
            <label>Tài khoản <span class="required">*</span></label>
            <div class="input-icon-wrap">
                <i class="bi bi-person-fill"></i>
                <input name="username" required value="admin" placeholder="Nhập tài khoản">
            </div>
        </div>
        <div class="form-group">
            <label>Mật khẩu <span class="required">*</span></label>
            <div class="input-icon-wrap">
                <i class="bi bi-lock-fill"></i>
                <input type="password" name="password" required value="123456" placeholder="Nhập mật khẩu">
            </div>
        </div>
        <button type="submit" class="btn btn-primary" style="width:100%;justify-content:center;padding:12px;">
            <i class="bi bi-box-arrow-in-right"></i> Đăng nhập
        </button>
    </form>

    <p class="login-hint">Tài khoản mẫu: <strong>admin</strong> / <strong>123456</strong></p>
    <a class="login-back" href="${pageContext.request.contextPath}/">
        <i class="bi bi-arrow-left"></i> Về trang chủ
    </a>
</div>
</body>
</html>
