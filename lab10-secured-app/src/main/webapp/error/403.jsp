<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%
    int status = 403;
    String statusParam = request.getParameter("status");
    if (statusParam != null) {
        try { status = Integer.parseInt(statusParam); } catch (Exception ignored) {}
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>403 - Không có quyền truy cập</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />
    <div class="page-wrapper">
        <div class="page-container" style="display:flex; align-items:center; justify-content:center; min-height:70vh;">
            <div class="error-card">
                <div class="error-code error-403">403</div>
                <div class="error-title">Không có quyền truy cập</div>
                <div class="error-message">
                    Bạn không có quyền truy cập trang này.<br>
                    Vui lòng liên hệ quản trị viên nếu cần hỗ trợ.
                </div>
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary"> Quay về Dashboard </a>
            </div>
        </div>
    </div>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
</body>
</html>
