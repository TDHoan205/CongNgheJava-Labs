# LAB 7 - CRUD Servlet + JSP + JSTL + MVC

## Công nghệ
- JDK 17/21
- Maven
- Apache Tomcat 10.x
- Jakarta Servlet 6
- JSP/JSTL 3
- MVC/Model 2
- Session + Filter + Listener

## Tài khoản
- Username: `admin`
- Password: `123456`

## Chạy bằng Maven
```bash
mvn clean package
```
Sau khi build, file WAR nằm trong `target/lab07-crud-mvc.war`.

Copy WAR vào thư mục `webapps` của Tomcat 10.x, khởi động Tomcat và mở:
`http://localhost:8080/lab07-crud-mvc/`

## Chức năng đã làm
1. Trang chủ/menu
2. CRUD + tìm kiếm sinh viên
3. CRUD + tìm kiếm sách
4. CRUD + validate sản phẩm
5. CRUD + tìm kiếm lớp học
6. Quản lý điểm, tính tổng kết và xếp loại
7. Đăng nhập + Session + LoginFilter bảo vệ `/admin/*`
8. Giỏ hàng lưu trong Session
9. Phân trang có thể bổ sung theo yêu cầu; phần CRUD hiện tại dùng danh sách đầy đủ
10. Listener ghi log lifecycle ứng dụng/session
11. Module tổng hợp MVC

## Luồng MVC
Browser -> Servlet Controller -> Repository -> Model -> Controller -> JSP/JSTL -> Browser.

## Lưu ý
Dữ liệu Repository chỉ lưu trong RAM bằng `List`, nên sẽ mất khi Tomcat/ứng dụng restart. Đây là đúng yêu cầu Lab 7 vì chưa dùng CSDL.
