<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>500 - Lỗi hệ thống</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />
    <div class="page-wrapper">
        <div class="page-container" style="display:flex; align-items:center; justify-content:center; min-height:70vh;">
            <div class="error-card">
                <div class="error-code error-500">500</div>
                <div class="error-title">Lỗi hệ thống</div>
                <div class="error-message">
                    Đã xảy ra lỗi phía máy chủ.<br>
                    Vui lòng thử lại sau hoặc liên hệ quản trị viên.
                </div>
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary"> Quay về Dashboard </a>
            </div>
        </div>
    </div>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
</body>
</html>
