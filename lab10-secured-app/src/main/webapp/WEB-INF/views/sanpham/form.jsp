<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty sanPham.id ? 'Thêm' : 'Sửa'} Sản phẩm - Lab 10</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />

    <div class="page-wrapper">
        <div class="page-container">
            <div class="card">
                <div class="card-header">
                    <h1>${empty sanPham.id ? 'Thêm Sản phẩm mới' : 'Sửa Sản phẩm'}</h1>
                </div>

                <form method="post" action="${pageContext.request.contextPath}/san-pham">
                    <input type="hidden" name="id" value="${sanPham.id}">

                    <div class="row-2">
                        <div class="form-group">
                            <label for="ma">Mã sản phẩm *</label>
                            <input type="text" id="ma" name="ma" value="${sanPham.ma}" class="form-control" required placeholder="VD: SP001">
                        </div>
                        <div class="form-group">
                            <label for="ten">Tên sản phẩm *</label>
                            <input type="text" id="ten" name="ten" value="${sanPham.ten}" class="form-control" required placeholder="VD: Laptop Dell XPS 15">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="moTa">Mô tả</label>
                        <textarea id="moTa" name="moTa" class="form-control" placeholder="Mô tả chi tiết...">${sanPham.moTa}</textarea>
                    </div>

                    <div class="row-2">
                        <div class="form-group">
                            <label for="gia">Giá (VNĐ) *</label>
                            <input type="number" id="gia" name="gia" value="${sanPham.gia}" class="form-control" required min="1" step="1000" placeholder="VD: 28990000">
                        </div>
                        <div class="form-group">
                            <label for="soLuong">Số lượng *</label>
                            <input type="number" id="soLuong" name="soLuong" value="${sanPham.soLuong}" class="form-control" required min="0" placeholder="VD: 15">
                        </div>
                    </div>

                    <div class="btn-group mt-2">
                        <button type="submit" class="btn btn-primary">${empty sanPham.id ? 'Thêm mới' : 'Cập nhật'}</button>
                        <a href="${pageContext.request.contextPath}/san-pham" class="btn btn-outline">Hủy</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
</body>
</html>
