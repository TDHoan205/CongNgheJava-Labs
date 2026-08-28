# BÁO CÁO LAB 12: PHÁT TRIỂN ỨNG DỤNG WEB VỚI SPRING MVC
**Học phần:** Công nghệ Java (IT3242)  
**Chương 4:** Phát triển ứng dụng với Spring Framework  
**Sinh viên thực hiện:** Trần Đức Hoàn  
**MSSV:** 2030022  
**Lớp:** DCCNTT14.10.1 (CNTT14-01)  
**Tên dự án:** `lab12-spring-mvc-student`  

---

## 1. MỤC TIÊU BÀI LAB
- Hoàn thành đầy đủ ứng dụng Quản lý Sinh viên CRUD theo mô hình Spring MVC.
- Tách biệt rõ ràng 4 tầng: **Controller** $\rightarrow$ **Service** $\rightarrow$ **Model** $\rightarrow$ **View (Thymeleaf)**.
- Quản lý danh sách sinh viên giả lập trong bộ nhớ RAM qua lớp `StudentService` (chuẩn bị sẵn sàng để nâng cấp lên CSDL ở Lab 13).
- Áp dụng Bean Validation với các annotation:
  - `@NotBlank`, `@Size(min = 5)` cho Mã sinh viên.
  - `@NotBlank` cho Họ và tên, Lớp học.
  - `@NotBlank`, `@Email` cho Email.
  - Kiểm tra mã sinh viên không trùng lặp trong hệ thống.
- Thực hiện đầy đủ các chức năng: Xem danh sách, Thêm mới, Xem chi tiết, Chỉnh sửa, Xóa và Tìm kiếm sinh viên theo tên/mã/lớp.

---

## 2. CẤU TRÚC PROJECT
```text
lab12-spring-mvc-student/
├── pom.xml
└── src/main/
    ├── java/vn/edu/eaut/lab12/
    │   ├── Lab12Application.java
    │   ├── controller/
    │   │   └── StudentController.java
    │   ├── model/
    │   │   └── Student.java
    │   └── service/
    │       └── StudentService.java
    └── resources/
        ├── application.properties
        ├── static/
        │   └── css/
        │       └── style.css
        └── templates/
            └── students/
                ├── layout.html
                ├── list.html
                ├── form.html
                └── detail.html
```

---

## 3. BẢNG KIỂM TRA CHỨC NĂNG VÀ VALIDATION

| Chức năng | Đường dẫn URL | Mô tả & Validation áp dụng |
| :--- | :--- | :--- |
| **Danh sách & Tìm kiếm** | `GET /students` | Hiển thị bảng danh sách sinh viên; hỗ trợ tìm kiếm theo họ tên, mã SV, lớp. |
| **Form Thêm mới** | `GET /students/create` | Trả về form rỗng với `th:object="${student}"`. |
| **Lưu / Cập nhật** | `POST /students/save` | Bắt lỗi `@Valid`, `BindingResult`. Kiểm tra mã SV trùng. Trả về form hiển thị `th:errors` nếu có lỗi, ngược lại lưu và `redirect:/students`. |
| **Xem chi tiết** | `GET /students/detail/{id}` | Tìm sinh viên theo ID và hiển thị trang chi tiết. |
| **Form Chỉnh sửa** | `GET /students/edit/{id}` | Nạp dữ liệu sinh viên vào form để chỉnh sửa. |
| **Xóa sinh viên** | `GET /students/delete/{id}` | Xóa sinh viên khỏi bộ nhớ và redirect về danh sách. |

---

## 4. HƯỚNG DẪN CHẠY VÀ KIỂM THỬ

```bash
# Biên dịch project
mvn clean package

# Chạy ứng dụng Spring Boot
mvn spring-boot:run
```

**Địa chỉ ứng dụng:** 👉 `http://localhost:8080/students`
