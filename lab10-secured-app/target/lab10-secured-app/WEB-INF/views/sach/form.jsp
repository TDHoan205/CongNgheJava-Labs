<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty sach.id ? 'Thêm' : 'Sửa'} Sách - Lab 10</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />

    <div class="page-wrapper">
        <div class="page-container">
            <div class="card">
                <div class="card-header">
                    <h1>${empty sach.id ? 'Thêm Sách mới' : 'Sửa Sách'}</h1>
                </div>

                <form method="post" action="${pageContext.request.contextPath}/sach">
                    <input type="hidden" name="id" value="${sach.id}">

                    <div class="row-2">
                        <div class="form-group">
                            <label for="maSach">Mã sách *</label>
                            <input type="text" id="maSach" name="maSach" value="${sach.maSach}" class="form-control" required placeholder="VD: BK001">
                        </div>
                        <div class="form-group">
                            <label for="namXuatBan">Năm xuất bản *</label>
                            <input type="number" id="namXuatBan" name="namXuatBan" value="${sach.namXuatBan}" class="form-control" required min="1000" max="2030" placeholder="VD: 2024">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="tenSach">Tên sách *</label>
                        <input type="text" id="tenSach" name="tenSach" value="${sach.tenSach}" class="form-control" required placeholder="VD: Lập trình Java cơ bản">
                    </div>

                    <div class="row-2">
                        <div class="form-group">
                            <label for="tacGia">Tác giả</label>
                            <input type="text" id="tacGia" name="tacGia" value="${sach.tacGia}" class="form-control" placeholder="VD: Nguyễn Thanh Tuấn">
                        </div>
                        <div class="form-group">
                            <label for="nhaXuatBan">Nhà xuất bản</label>
                            <input type="text" id="nhaXuatBan" name="nhaXuatBan" value="${sach.nhaXuatBan}" class="form-control" placeholder="VD: NXB Giáo dục">
                        </div>
                    </div>

                    <div class="btn-group mt-2">
                        <button type="submit" class="btn btn-primary">${empty sach.id ? 'Thêm mới' : 'Cập nhật'}</button>
                        <a href="${pageContext.request.contextPath}/sach" class="btn btn-outline">Hủy</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
</body>
</html>
