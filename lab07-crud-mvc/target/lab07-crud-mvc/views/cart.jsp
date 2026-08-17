<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="pageTitle" value="Giỏ hàng" scope="request"/>
<c:set var="pageSubtitle" value="Quản lý giỏ hàng Session" scope="request"/>
<c:set var="activeNav" value="gio-hang" scope="request"/>
<!DOCTYPE html>
<html lang="vi">
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="app-layout">
    <%@ include file="/WEB-INF/jspf/sidebar.jspf" %>
    <div class="main-wrapper">
        <%@ include file="/WEB-INF/jspf/topbar.jspf" %>
        <main class="main-content">

            <c:choose>
                <c:when test="${empty cart}">
                    <div class="empty-state">
                        <i class="bi bi-cart-x"></i>
                        <h3>Giỏ hàng trống</h3>
                        <p>Bạn chưa thêm sản phẩm nào vào giỏ hàng.</p>
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/san-pham">
                            <i class="bi bi-box-seam"></i> Chọn sản phẩm
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="cart-layout">
                        <div>
                            <form method="post" action="${pageContext.request.contextPath}/gio-hang">
                                <div class="table-wrap">
                                    <table>
                                        <thead>
                                            <tr>
                                                <th>Sản phẩm</th>
                                                <th>Đơn giá</th>
                                                <th>Số lượng</th>
                                                <th>Thành tiền</th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="item" items="${cart}">
                                                <tr>
                                                    <td><strong>${item.sanPham.ten}</strong></td>
                                                    <td><fmt:formatNumber value="${item.sanPham.gia}" type="number" groupingUsed="true" maxFractionDigits="0"/> ₫</td>
                                                    <td>
                                                        <input class="qty" type="number" min="1" name="qty_${item.sanPham.id}" value="${item.soLuong}">
                                                    </td>
                                                    <td><strong><fmt:formatNumber value="${item.thanhTien}" type="number" groupingUsed="true" maxFractionDigits="0"/> ₫</strong></td>
                                                    <td>
                                                        <a class="btn btn-icon btn-delete" title="Xóa" href="${pageContext.request.contextPath}/gio-hang?action=remove&id=${item.sanPham.id}">
                                                            <i class="bi bi-trash-fill"></i>
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="form-actions" style="margin-top:16px;">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="bi bi-arrow-repeat"></i> Cập nhật số lượng
                                    </button>
                                    <a class="btn btn-danger" href="${pageContext.request.contextPath}/gio-hang?action=clear">
                                        <i class="bi bi-trash"></i> Xóa toàn bộ
                                    </a>
                                </div>
                            </form>
                        </div>

                        <div class="cart-summary">
                            <h3><i class="bi bi-receipt"></i> Tóm tắt đơn hàng</h3>
                            <div class="summary-row">
                                <span>Số sản phẩm</span>
                                <span>${fn:length(cart)}</span>
                            </div>
                            <div class="summary-total">
                                <span>Tổng tiền</span>
                                <span><fmt:formatNumber value="${total}" type="number" groupingUsed="true" maxFractionDigits="0"/> ₫</span>
                            </div>
                            <a class="btn btn-secondary" style="width:100%;justify-content:center;margin-top:16px;" href="${pageContext.request.contextPath}/san-pham">
                                <i class="bi bi-arrow-left"></i> Tiếp tục mua
                            </a>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

        </main>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/layout-scripts.jspf" %>
</body>
</html>
