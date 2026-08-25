<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="/views/layout/header.jsp" />

<div class="card">
    <div class="card-header">
        <div>
            <h1 class="card-title">Quản lý Sản phẩm (Chuyển đổi JPA - Bài 13)</h1>
            <p style="color: var(--text-secondary); margin-top: 0.3rem;">Quản lý kho sản phẩm bằng Jakarta Persistence Entity & Repository</p>
        </div>
        <a href="${pageContext.request.contextPath}/san-pham?action=new" class="btn btn-primary">➕ Thêm Sản phẩm mới</a>
    </div>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">✅ ${successMessage}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/san-pham" method="get" style="display: flex; gap: 0.8rem; margin-bottom: 1.5rem;">
        <input type="text" name="keyword" value="${keyword}" class="form-control" placeholder="Tìm kiếm mã SP, tên SP, loại sản phẩm..." style="max-width: 400px;">
        <button type="submit" class="btn btn-secondary">🔍 Tìm kiếm</button>
    </form>

    <div class="table-responsive">
        <table>
            <thead>
                <tr>
                    <th>STT</th>
                    <th>Mã SP</th>
                    <th>Tên Sản phẩm</th>
                    <th>Loại SP</th>
                    <th>Đơn giá</th>
                    <th>Tồn kho</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="sp" items="${dsSanPham}" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td><strong style="color: var(--secondary);">${sp.maSp}</strong></td>
                        <td>${sp.tenSp}</td>
                        <td><span class="badge badge-info">${sp.loaiSp}</span></td>
                        <td><strong style="color: var(--accent);"><fmt:formatNumber value="${sp.gia}" type="currency" currencySymbol="VNĐ"/></strong></td>
                        <td><span class="badge badge-warning">${sp.soLuong} cái</span></td>
                        <td style="display: flex; gap: 0.5rem;">
                            <a href="${pageContext.request.contextPath}/san-pham?action=edit&id=${sp.id}" class="btn btn-secondary" style="padding: 0.3rem 0.6rem; font-size: 0.8rem;">✏️ Sửa</a>
                            <a href="${pageContext.request.contextPath}/san-pham?action=delete&id=${sp.id}" class="btn btn-danger" style="padding: 0.3rem 0.6rem; font-size: 0.8rem;" onclick="return confirm('Bạn có muốn xóa sản phẩm ${sp.tenSp}?');">🗑️ Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty dsSanPham}">
                    <tr>
                        <td colspan="7" style="text-align: center; color: var(--text-secondary); padding: 2rem;">Chưa có dữ liệu sản phẩm.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/views/layout/footer.jsp" />
