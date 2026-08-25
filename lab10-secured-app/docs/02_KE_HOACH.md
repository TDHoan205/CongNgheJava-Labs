# LAB 10 - KẾ HOẠCH THỰC HIỆN

## Nguyên tắc làm việc với AI

Không giao toàn bộ Lab 10 cho AI trong một prompt.

Quy trình chuẩn của mỗi task:

1. Hiểu mục tiêu.
2. AI phân tích trước.
3. Chốt thiết kế.
4. AI implement đúng phạm vi task.
5. AI build/test.
6. Người học kiểm tra.
7. Chỉ khi PASS mới sang task tiếp theo.

AI không được tự ý:
- Refactor ngoài phạm vi.
- Đổi kiến trúc khi chưa được phép.
- Thêm dependency không cần thiết.
- Xóa code cũ.
- Làm task tiếp theo.
- Sửa phần không liên quan.

---

# PHASE 0 - AUDIT PROJECT

## Mục tiêu

Hiểu project Lab 6-9 đang có gì trước khi sửa.

## Tasks

### Task 0.1 - Audit

Kiểm tra:

- Java
- Maven
- Tomcat/Jakarta
- Database
- Entity
- Repository/DAO
- Service
- Controller/Servlet
- JSP/JSF
- JPA
- Transaction
- Validation
- Message
- Các module nghiệp vụ
- Chức năng đã có
- Chức năng Lab 10 còn thiếu

## Kết quả cần có

- Báo cáo hiện trạng.
- Không sửa code.

---

# PHASE 1 - DESIGN

## Mục tiêu

Thiết kế trước khi code.

## Tasks

### Task 1.1 - Architecture

Xác định:

Browser
→ Filter
→ Controller
→ Service
→ Repository
→ JPA
→ Database

và View/JSP.

### Task 1.2 - Security design

Thiết kế:

- Authentication
- Authorization
- Session
- Role
- Public URL
- Protected URL
- 403

### Task 1.3 - Database design

Thiết kế:

- users
- role
- các bảng nghiệp vụ hiện có
- quan hệ nếu có
- dữ liệu mẫu

### Task 1.4 - Login flow

Thiết kế luồng:

login.jsp
→ AuthController
→ AuthService
→ UserRepository
→ Database
→ Session
→ Dashboard

## Kết quả cần có

Một thiết kế rõ ràng trước khi implementation.

---

# PHASE 2 - USER + ROLE

## Mục tiêu

Tạo nền tảng tài khoản.

## Tasks

### Task 2.1 - Role

Tạo/hoàn thiện:

- ADMIN
- STAFF
- USER

### Task 2.2 - User Entity

Các thông tin tối thiểu:

- id
- email
- password
- fullName
- role
- active

### Task 2.3 - Database

Tạo bảng users và constraint cần thiết.

### Task 2.4 - UserRepository

Tối thiểu:

- findByEmail
- các method cần cho User Management sau này

### Task 2.5 - Sample accounts

Tạo dữ liệu:

- ADMIN
- STAFF
- USER

## Acceptance Criteria

- Build PASS.
- Database tạo được.
- Query User hoạt động.
- Email unique.
- Có tài khoản test.

---

# PHASE 3 - LOGIN / LOGOUT / SESSION

## Mục tiêu

Hoàn thiện Authentication.

## Tasks

### Task 3.1 - Login JSP

Form:

- email
- password
- message lỗi

### Task 3.2 - AuthService

Kiểm tra:

- User tồn tại.
- Active.
- Password đúng.

### Task 3.3 - AuthController

POST `/auth`.

### Task 3.4 - Session

Sau login:

`currentUser`

hoặc thiết kế session attribute tương đương.

### Task 3.5 - Logout

Invalidate session.

## Acceptance Criteria

- Login đúng → dashboard.
- Login sai → message.
- User inactive → không login.
- Logout → session bị hủy.

---

# PHASE 4 - AUTHENTICATION + AUTHORIZATION

## Mục tiêu

Bảo vệ URL.

## Tasks

### Task 4.1 - AuthenticationFilter

Bảo vệ:

- `/admin/*`
- `/staff/*`
- `/user/*`

Guest → login.

### Task 4.2 - AuthorizationFilter

ADMIN:

- admin
- staff
- user

STAFF:

- staff
- user

USER:

- user

### Task 4.3 - 403

Tạo trang 403.

### Task 4.4 - Security test

Test:

- Guest → private
- USER → admin
- STAFF → admin
- STAFF → staff
- ADMIN → admin

## Acceptance Criteria

URL phải được bảo vệ thật bằng Filter.

---

# PHASE 5 - USER MANAGEMENT

## Mục tiêu

ADMIN quản lý tài khoản.

## Tasks

### Task 5.1 - User list

### Task 5.2 - Create user

### Task 5.3 - Update user

### Task 5.4 - Change role

### Task 5.5 - Active/inactive

### Task 5.6 - Search email

### Task 5.7 - Validation

## Acceptance Criteria

Chỉ ADMIN sử dụng được chức năng quản lý User.

---

# PHASE 6 - PROFILE + PASSWORD

## Tasks

### Task 6.1 - Profile

### Task 6.2 - Update profile

### Task 6.3 - Change password

### Task 6.4 - Transaction

### Task 6.5 - Validation

## Acceptance Criteria

- User chỉ sửa được dữ liệu được phép.
- Password cũ phải đúng.
- Password mới xác nhận đúng.
- Update thành công hoặc rollback đúng.

---

# PHASE 7 - ERROR + LOG + UI

## Tasks

### Task 7.1 - 403

### Task 7.2 - 404

### Task 7.3 - 500

### Task 7.4 - Logging

### Task 7.5 - Header/Footer

### Task 7.6 - Menu theo role

### Task 7.7 - Success/Error message

## Acceptance Criteria

Giao diện thống nhất và lỗi thân thiện.

---

# PHASE 8 - GHÉP 3 MODULE NGHIỆP VỤ

## Mục tiêu

Tích hợp Lab 6-9 với security của Lab 10.

## Tasks

### Task 8.1

Chọn 3 module đang có.

### Task 8.2

Kiểm tra CRUD.

### Task 8.3

Kiểm tra JPA.

### Task 8.4

Kiểm tra validation.

### Task 8.5

Áp dụng authorization phù hợp.

### Task 8.6

Regression test.

## Nguyên tắc

Không tạo lại module nếu module cũ đã đáp ứng yêu cầu.

---

# PHASE 9 - FULL TEST

## Nhóm A - Authentication

- Login đúng.
- Login sai.
- Inactive.
- Logout.

## Nhóm B - Authorization

- Guest.
- USER.
- STAFF.
- ADMIN.

## Nhóm C - CRUD

- Create.
- Read.
- Update.
- Delete nếu có.

## Nhóm D - Validation

- Empty.
- Sai format.
- Trùng email.
- Password mismatch.

## Nhóm E - Error

- 403.
- 404.
- 500.

## Nhóm F - Session

- Logout.
- Back.
- Reload.

---

# PHASE 10 - SUBMISSION

## Tasks

### Task 10.1

Database script.

### Task 10.2

Screenshot.

### Task 10.3

Báo cáo.

### Task 10.4

README nếu cần.

### Task 10.5

Clean project.

### Task 10.6

ZIP source.

### Task 10.7

Final verification.
