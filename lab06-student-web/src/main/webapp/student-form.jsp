<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${isEdit ? 'Cập Nhật Sinh Viên' : 'Thêm Sinh Viên Mới'}</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f6f9; color: #333; }
        .navbar { background: #2c3e50; color: white; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .navbar h1 { font-size: 18px; font-weight: 600; }
        .navbar a { color: white; text-decoration: none; font-size: 14px; }
        .container { max-width: 600px; margin: 30px auto; padding: 0 20px; }
        .card { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.05); }
        .card h2 { color: #2c3e50; margin-bottom: 20px; border-bottom: 2px solid #3498db; padding-bottom: 10px; }
        .form-group { margin-bottom: 18px; }
        .form-group label { display: block; font-weight: 600; color: #34495e; margin-bottom: 6px; font-size: 14px; }
        .form-group input { width: 100%; padding: 10px 14px; border: 1px solid #ccc; border-radius: 4px; font-size: 14px; }
        .form-group input[readonly] { background-color: #e9ecef; cursor: not-allowed; color: #6c757d; }
        .btn-group { display: flex; gap: 10px; margin-top: 25px; }
        .btn { padding: 10px 20px; border-radius: 4px; text-decoration: none; font-size: 14px; font-weight: 600; border: none; cursor: pointer; }
        .btn-primary { background: #3498db; color: white; }
        .btn-secondary { background: #95a5a6; color: white; }
        .alert-error { background-color: #fde8e8; color: #e74c3c; border: 1px solid #f5c6cb; padding: 10px; border-radius: 4px; margin-bottom: 15px; font-size: 14px; }
    </style>
</head>
<body>

<div class="navbar">
    <h1>Quản Lý Sinh Viên</h1>
    <a href="${pageContext.request.contextPath}/students">Quay lại Danh Sách</a>
</div>

<div class="container">
    <div class="card">
        <h2>${isEdit ? 'Cập Nhật Thông Tin Sinh Viên' : 'Thêm Sinh Viên Mới'}</h2>

        <c:if test="${not empty error}">
            <div class="alert-error">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}${isEdit ? '/students/edit' : '/students/add'}" method="post">
            <div class="form-group">
                <label for="id">Mã sinh viên ${isEdit ? '(Không được sửa)' : '*'}:</label>
                <input type="text" id="id" name="id" value="${student != null ? student.id : param.id}" 
                       ${isEdit ? 'readonly' : 'required'} placeholder="VD: SV006">
                <c:if test="${isEdit}">
                    <!-- Hidden field to ensure ID is passed if readonly isn't submitted in some edge cases -->
                    <input type="hidden" name="id" value="${student.id}">
                </c:if>
            </div>

            <div class="form-group">
                <label for="name">Họ và tên *:</label>
                <input type="text" id="name" name="name" value="${student != null ? student.name : param.name}" required placeholder="VD: Nguyễn Văn A">
            </div>

            <div class="form-group">
                <label for="className">Lớp học:</label>
                <input type="text" id="className" name="className" value="${student != null ? student.className : param.className}" placeholder="VD: DCCNTT12">
            </div>

            <div class="form-group">
                <label for="email">Email liên hệ:</label>
                <input type="email" id="email" name="email" value="${student != null ? student.email : param.email}" placeholder="VD: nguyenvana@example.com">
            </div>

            <div class="btn-group">
                <button type="submit" class="btn btn-primary">${isEdit ? 'Lưu Cập Nhật' : 'Thêm Sinh Viên'}</button>
                <a href="${pageContext.request.contextPath}/students" class="btn btn-secondary">Hủy Bỏ</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>
