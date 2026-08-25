<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty sinhVien.id ? 'Thêm' : 'Sửa'} Sinh viên - Lab 10</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />

    <div class="page-wrapper">
        <div class="page-container">
            <div class="card">
                <div class="card-header">
                    <h1>${empty sinhVien.id ? 'Thêm Sinh viên mới' : 'Sửa Sinh viên'}</h1>
                </div>

                <form method="post" action="${pageContext.request.contextPath}/sinh-vien">
                    <input type="hidden" name="id" value="${sinhVien.id}">

                    <div class="row-2">
                        <div class="form-group">
                            <label for="maSinhVien">Mã sinh viên *</label>
                            <input type="text" id="maSinhVien" name="maSinhVien" value="${sinhVien.maSinhVien}"
                                   class="form-control" required placeholder="VD: 20240001">
                        </div>
                        <div class="form-group">
                            <label for="hoTen">Họ tên *</label>
                            <input type="text" id="hoTen" name="hoTen" value="${sinhVien.hoTen}"
                                   class="form-control" required placeholder="VD: Nguyễn Văn An">
                        </div>
                    </div>

                    <div class="row-2">
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email" value="${sinhVien.email}"
                                   class="form-control" placeholder="VD: an@gmail.com">
                        </div>
                        <div class="form-group">
                            <label for="lop">Lớp</label>
                            <input type="text" id="lop" name="lop" value="${sinhVien.lop}"
                                   class="form-control" placeholder="VD: DCCNTT15.10.1">
                        </div>
                    </div>

                    <div class="btn-group mt-2">
                        <button type="submit" class="btn btn-primary">
                            ${empty sinhVien.id ? 'Thêm mới' : 'Cập nhật'}
                        </button>
                        <a href="${pageContext.request.contextPath}/sinh-vien" class="btn btn-outline">Hủy</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
</body>
</html>
