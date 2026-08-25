# LAB 10 - YÊU CẦU

## 1. Mục tiêu

Hoàn thiện ứng dụng Java/Jakarta EE từ Lab 6-9 bằng cách bổ sung:

- Login
- Logout
- HttpSession
- Role-based access control
- Authentication Filter
- Authorization Filter
- Quản lý người dùng
- Profile
- Đổi mật khẩu
- Trang lỗi 403/404/500
- Logging
- Validate và message
- Hoàn thiện giao diện và điều hướng
- Tích hợp tối thiểu 3 module nghiệp vụ
- Database script
- Báo cáo và minh chứng

---

## 2. Authentication

- Người dùng đăng nhập bằng tài khoản trong database.
- Kiểm tra email và password.
- Không cho tài khoản inactive đăng nhập.
- Khi đăng nhập thành công, lưu thông tin cần thiết của người dùng vào HttpSession.
- Logout phải invalidate session.

---

## 3. Authorization

Các role chính:

- ADMIN
- STAFF
- USER

Quy tắc:

| URL | Quyền |
|---|---|
| `/admin/*` | ADMIN |
| `/staff/*` | ADMIN hoặc STAFF |
| `/user/*` | Người dùng đã đăng nhập |
| `/login.jsp` | Public |
| `/auth` | Public |
| `/assets/*` | Public |

Lưu ý:

> Ẩn menu không được xem là biện pháp bảo mật. URL vẫn phải được bảo vệ bằng Filter.

---

## 4. User Management

ADMIN có thể:

- Xem danh sách tài khoản.
- Thêm tài khoản.
- Sửa họ tên.
- Đổi role.
- Khóa/mở tài khoản.
- Tìm kiếm theo email.
- Không tạo email trùng.

---

## 5. Profile

Người dùng đã đăng nhập có thể:

- Xem thông tin cá nhân.
- Cập nhật thông tin được phép.
- Validate dữ liệu.
- Hiển thị message thành công/thất bại.

---

## 6. Đổi mật khẩu

Người dùng nhập:

- Mật khẩu cũ.
- Mật khẩu mới.
- Xác nhận mật khẩu mới.

Yêu cầu:

- Kiểm tra mật khẩu cũ.
- Kiểm tra mật khẩu mới hợp lệ.
- Hai mật khẩu mới phải giống nhau.
- Cập nhật database bằng transaction.

> Trong Lab có thể dùng plain text để phục vụ học tập nếu đề yêu cầu, nhưng phải ghi chú rằng hệ thống thực tế nên hash password.

---

## 7. Error Handling

Phải có:

- 403 Forbidden
- 404 Not Found
- 500 Internal Server Error

Các trang lỗi cần thân thiện và có nút quay về dashboard hoặc trang phù hợp.

---

## 8. Logging

Ghi log tối thiểu:

- Login thành công/thất bại nếu phù hợp.
- Logout.
- Các thao tác quan trọng: thêm/sửa/xóa dữ liệu.
- Thời gian thực hiện.
- Người thực hiện nếu xác định được.

---

## 9. Business Modules

Ứng dụng phải có tối thiểu 3 module nghiệp vụ có JPA.

Ưu tiên sử dụng và hoàn thiện các module đã có từ Lab 6-9 thay vì tạo lại module mới không cần thiết.

Ví dụ:

- Sinh viên
- Sách
- Sản phẩm
- Hóa đơn
- Nhân sự
- Điểm

---

## 10. Database

Phải có:

- Script tạo database/table cần thiết.
- Primary key.
- Foreign key nếu có quan hệ.
- Dữ liệu mẫu.
- Tài khoản mẫu cho các role.
- Không để email tài khoản mẫu bị trùng.

---

## 11. Validation và Message

Form phải:

- Kiểm tra dữ liệu đầu vào.
- Hiển thị lỗi rõ ràng.
- Hiển thị thông báo thành công.
- Không làm mất message khi redirect nếu cần dùng flash message hoặc cơ chế tương đương.

---

## 12. Giao diện

Hoàn thiện:

- Header.
- Footer.
- Navigation.
- Dashboard.
- Menu theo role.
- Form.
- Bảng dữ liệu.
- Message thành công/thất bại.

Menu chỉ là giao diện. Security vẫn phải do Filter đảm bảo.

---

## 13. Sản phẩm cần nộp

1. Source code project.
2. File ZIP đặt tên theo yêu cầu giảng viên, ví dụ:
   `Lab10_MSSV_HoTen.zip`
3. Database script.
4. Ảnh chạy chương trình.
5. Báo cáo.
6. Ghi chú lỗi/phần chưa hoàn thành nếu có.

---

## 14. Checklist nghiệm thu cuối

- [ ] Project import được vào IDE.
- [ ] Maven build thành công.
- [ ] Chạy được trên Tomcat.
- [ ] Database tạo được từ script.
- [ ] ADMIN login thành công.
- [ ] STAFF login thành công.
- [ ] USER login thành công.
- [ ] Guest không truy cập được URL private.
- [ ] USER không vào được `/admin/*`.
- [ ] STAFF không vào được `/admin/*`.
- [ ] ADMIN vào được `/admin/*`.
- [ ] ADMIN vào được `/staff/*`.
- [ ] STAFF vào được `/staff/*`.
- [ ] USER vào được `/user/*`.
- [ ] Logout invalidate session.
- [ ] Back + Reload sau logout không vào lại trang protected.
- [ ] CRUD tối thiểu 3 module chạy.
- [ ] Validation hoạt động.
- [ ] Message thành công/thất bại hoạt động.
- [ ] 403 hoạt động.
- [ ] 404 hoạt động.
- [ ] 500 được cấu hình.
- [ ] Logging hoạt động.
- [ ] Database script hoạt động.
- [ ] Báo cáo có ảnh minh chứng.
