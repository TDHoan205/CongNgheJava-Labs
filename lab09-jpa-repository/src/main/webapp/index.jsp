<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/views/layout/header.jsp" />

<div class="card">
    <div class="card-header">
        <div>
            <h1 class="card-title">Chương 3: Phát triển ứng dụng Đa lớp trong Jakarta EE</h1>
            <p style="color: var(--text-secondary); margin-top: 0.4rem;">
                Lab 9 - Tích hợp JPA: Entity, Repository, Transaction, JPQL & Bean Validation
            </p>
        </div>
        <span class="badge badge-info" style="font-size: 0.9rem; padding: 0.5rem 1rem;">Jakarta Persistence 3.1 & Hibernate ORM</span>
    </div>

    <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(240px, 1fr)); gap: 1.5rem; margin-top: 1.5rem;">
        <div class="card" style="margin-bottom: 0; background: rgba(79, 70, 229, 0.15); border-color: rgba(79, 70, 229, 0.3);">
            <h3>🎓 Sinh viên & Lớp học</h3>
            <p style="color: var(--text-secondary); margin: 0.8rem 0;">Quản lý thông tin sinh viên, phân lớp 1-N, tìm kiếm JPQL và phân trang 5 dòng/trang.</p>
            <a href="${pageContext.request.contextPath}/sinh-vien" class="btn btn-primary">Quản lý Sinh viên &rarr;</a>
        </div>

        <div class="card" style="margin-bottom: 0; background: rgba(6, 182, 212, 0.15); border-color: rgba(6, 182, 212, 0.3);">
            <h3>🏫 Lớp học</h3>
            <p style="color: var(--text-secondary); margin: 0.8rem 0;">Entity LopHoc với quan hệ 1-N với SinhVien, hiển thị danh sách sinh viên theo lớp.</p>
            <a href="${pageContext.request.contextPath}/lop-hoc" class="btn btn-primary">Danh sách Lớp học &rarr;</a>
        </div>

        <div class="card" style="margin-bottom: 0; background: rgba(16, 185, 129, 0.15); border-color: rgba(16, 185, 129, 0.3);">
            <h3>📊 Điểm & Môn học</h3>
            <p style="color: var(--text-secondary); margin: 0.8rem 0;">Entity MonHoc & Diem, tự động tính điểm tổng kết và xếp loại (Xuất sắc, Giỏi, Khá, TB, Yếu).</p>
            <a href="${pageContext.request.contextPath}/diem" class="btn btn-primary">Quản lý Bảng điểm &rarr;</a>
        </div>

        <div class="card" style="margin-bottom: 0; background: rgba(245, 158, 11, 0.15); border-color: rgba(245, 158, 11, 0.3);">
            <h3>📚 Module Sách (JPA)</h3>
            <p style="color: var(--text-secondary); margin: 0.8rem 0;">Chuyển đổi module Sách từ List bộ nhớ (Lab 8) sang lưu trữ CSDL thật bằng JPA.</p>
            <a href="${pageContext.request.contextPath}/sach" class="btn btn-primary">Quản lý Sách &rarr;</a>
        </div>

        <div class="card" style="margin-bottom: 0; background: rgba(236, 72, 153, 0.15); border-color: rgba(236, 72, 153, 0.3);">
            <h3>🛒 Module Sản phẩm (JPA)</h3>
            <p style="color: var(--text-secondary); margin: 0.8rem 0;">Chuyển đổi module Sản phẩm sang JPA Repository, tự động rollback khi gặp lỗi transaction.</p>
            <a href="${pageContext.request.contextPath}/san-pham" class="btn btn-primary">Quản lý Sản phẩm &rarr;</a>
        </div>
    </div>
</div>

<div class="card">
    <h2 style="font-size: 1.2rem; margin-bottom: 1rem; color: var(--secondary);">📌 Tính năng & Nâng cấp nổi bật trong Lab 9</h2>
    <ul style="padding-left: 1.5rem; color: var(--text-secondary); line-height: 1.8;">
        <li><strong>Generic BaseRepository:</strong> Lớp cha hỗ trợ `findAll`, `findById`, `save`, `update`, `delete`, transaction rollback an toàn và phân trang.</li>
        <li><strong>Transaction Đa thao tác (Bài 11):</strong> Thêm mới SinhVien tự động khởi tạo các dòng điểm mặc định cho tất cả môn học trong cùng 1 Atomic Transaction.</li>
        <li><strong>Ràng buộc dữ liệu & Rollback (Bài 10):</strong> Bắt lỗi trùng mã sinh viên, email sai định dạng, tự động rollback khi có xung đột CSDL.</li>
        <li><strong>JPQL Queries & Pagination (Bài 9):</strong> Tìm kiếm theo từ khóa dynamic JPQL và phân trang 5 bản ghi/trang (`setFirstResult`, `setMaxResults`).</li>
        <li><strong>Chuẩn bị cho Lab 10:</strong> Đã cấu hình đầy đủ Entity `User`, `Role` và `user_roles` (N-N) phục vụ chức năng Login & Phân quyền URL.</li>
    </ul>
</div>

<jsp:include page="/views/layout/footer.jsp" />
