<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Không tìm thấy trang</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />
    <div class="page-wrapper">
        <div class="page-container" style="display:flex; align-items:center; justify-content:center; min-height:70vh;">
            <div class="error-card">
                <div class="error-code error-404">404</div>
                <div class="error-title">Không tìm thấy trang</div>
                <div class="error-message">
                    Trang bạn đang tìm kiếm không tồn tại<br>
                    hoặc đã bị di chuyển.
                </div>
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary"> Quay về Dashboard </a>
            </div>
        </div>
    </div>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
</body>
</html>
