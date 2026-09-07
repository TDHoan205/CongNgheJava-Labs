# BÁO CÁO LAB 15: BÀI TẬP TỔNG HỢP XÂY DỰNG ỨNG DỤNG HOÀN CHỈNH VỚI SPRING FRAMEWORK
**Học phần:** Công nghệ Java (IT3242)  
**Chương 4:** Phát triển ứng dụng với Spring Framework  
**Sinh viên thực hiện:** Trần Đức Hoàn  
**MSSV:** 2030022  
**Lớp:** DCCNTT14.10.1 (CNTT14-01)  
**Tên dự án:** `lab15-spring-final-project`  

---

## 1. MỤC TIÊU VÀ NỘI DUNG ĐÃ HOÀN THÀNH
- **Kiến trúc nhiều tầng hoàn chỉnh**: Entity $\rightarrow$ Repository $\rightarrow$ Service $\rightarrow$ Controller $\rightarrow$ Thymeleaf View.
- **CRUD Quản lý Sinh viên (Student)**: Xem danh sách, thêm mới, sửa, xóa, xem chi tiết và tìm kiếm theo họ tên / mã SV / lớp.
- **CRUD Quản lý Môn học (Course)**: Xem danh sách, thêm mới, sửa, xóa, xem chi tiết và tìm kiếm theo tên môn / mã môn.
- **Quản lý Đăng ký Học phần (Enrollment)**:
  - Thiết lập quan hệ Many-To-One giữa Enrollment với Student và Course.
  - Form đăng ký học phần và xử lý kiểm tra **chống đăng ký trùng lặp**.
  - Hiển thị danh sách đăng ký toàn hệ thống và chức năng **hủy đăng ký học phần**.
  - Xem danh sách các môn học mà một sinh viên cụ thể đã đăng ký.
- **Dashboard Thống kê & Quản trị**:
  - Thống kê tổng số sinh viên, tổng số môn học và tổng số lượt đăng ký.
  - Hiển thị các hoạt động đăng ký học phần mới nhất.
- **Bảo mật & Phân quyền (Spring Security 6)**:
  - Đăng nhập (Login), Đăng xuất (Logout) với giao diện tùy chỉnh.
  - Phân quyền chi tiết:
    - **ADMIN** (`admin` / `admin123`): Toàn quyền thêm, sửa, xóa sinh viên, môn học, hủy đăng ký học phần.
    - **USER** (`user` / `user123`): Xem danh sách, tra cứu và thực hiện đăng ký môn học.
- **Cơ sở dữ liệu**: Hỗ trợ đồng thời **H2 Database** (mặc định trong RAM) và **MySQL Server**. Tự động khởi tạo dữ liệu mẫu khi ứng dụng khởi động.

---

## 2. BẢNG ĐỐI CHIẾU TỪ BÀI 1 ĐẾN BÀI 10 THEO ĐỀ LAB 15

| STT | Bài tập theo đề Lab 15 | File triển khai chính | Chức năng thực hiện |
| :---: | :--- | :--- | :--- |
| **Bài 1** | **Thiết kế Entity Course** | `vn.edu.eaut.lab15.entity.Course.java` | Ánh xạ bảng `courses` (id, courseCode, courseName, credits). |
| **Bài 2** | **Thiết kế Entity Enrollment** | `vn.edu.eaut.lab15.entity.Enrollment.java` | Ánh xạ bảng `enrollments` thể hiện quan hệ Student - Course và ngày đăng ký. |
| **Bài 3** | **Tạo Repository Đăng ký học phần** | `vn.edu.eaut.lab15.repository.EnrollmentRepository.java` | Kế thừa `JpaRepository`, thêm hàm `findByStudentId` và `existsByStudentIdAndCourseId`. |
| **Bài 4** | **Tạo Service Đăng ký học phần** | `vn.edu.eaut.lab15.service.EnrollmentService.java` | Xử lý logic nghiệp vụ, kiểm tra sinh viên/môn học tồn tại và bắt lỗi đăng ký trùng. |
| **Bài 5** | **Controller Đăng ký học phần** | `vn.edu.eaut.lab15.controller.EnrollmentController.java` | Xử lý form chọn sinh viên, chọn môn học và gọi service lưu đăng ký. |
| **Bài 6** | **Trang danh sách Đăng ký học phần** | `templates/enrollments/list.html` | Hiển thị bảng danh sách đăng ký gồm sinh viên, môn học, số TC, ngày đăng ký. |
| **Bài 7** | **Chức năng Hủy đăng ký học phần** | `EnrollmentController.java` (`/delete/{id}`) | Cho phép hủy đăng ký học phần và cập nhật lại CSDL. |
| **Bài 8** | **Xem danh sách môn học của sinh viên** | `templates/enrollments/student_courses.html` & `students/detail.html` | Tra cứu chi tiết toàn bộ môn học mà một sinh viên cụ thể đã đăng ký. |
| **Bài 9** | **Dashboard Thống kê hệ thống** | `DashboardController.java` & `templates/dashboard.html` | Thống kê tổng số sinh viên, tổng môn học, tổng số lượt đăng ký. |
| **Bài 10** | **Giao diện, Menu & Phân quyền Security** | `config/SecurityConfig.java`, `templates/layout.html`, `static/css/style.css` | Hoàn thiện giao diện, menu điều hướng, thông báo lỗi/thành công và bảo vệ phân quyền ADMIN/USER. |

---

## 3. HƯỚNG DẪN CHẠY VÀ KIỂM THỬ ỨNG DỤNG

```bash
# Biên dịch dự án và chạy test
mvn clean package

# Khởi chạy ứng dụng Spring Boot
mvn spring-boot:run
```

- **Địa chỉ ứng dụng:** 👉 `http://localhost:8080`
- **Tài khoản đăng nhập:**
  - **ADMIN:** `admin` / `admin123` (Toàn quyền CRUD, Hủy đăng ký)
  - **USER:** `user` / `user123` (Xem danh sách, Đăng ký môn học)
- **H2 Console:** 👉 `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:lab15db`, User: `sa`, Password: để trống).
