# BÁO CÁO THỰC HÀNH LAB 8: CHUYỂN FORM SANG JSF, THÊM VALIDATION VÀ MESSAGE

**Học phần:** Công nghệ Java (IT3242)  
**Tên bài lab:** Lab 8 - Chuyển một form sang JSF, thêm validation và message  
**Mã dự án:** `lab08-jsf-validation`  
**Công nghệ sử dụng:** JDK 21, Jakarta EE 10 (Jakarta Faces 4.0, Weld CDI 5.1, Bean Validation / Hibernate Validator 8.0, Jetty 11)

---

## 1. Mục tiêu bài lab
1. Trình bày được sự khác biệt giữa JSF/Jakarta Faces (Component-based framework) và Servlet + JSP (Action-based / Scriptlet traditional framework).
2. Cấu hình thành công `FacesServlet` trong `web.xml` và CDI container (`beans.xml`, Weld) để xử lý các trang view `.xhtml`.
3. Chuyển đổi các form JSP truyền thống sang form JSF bằng các thẻ component: `h:form`, `h:inputText`, `h:selectOneMenu`, `h:commandButton`.
4. Tạo Managed Bean / CDI Bean (`@Named`, `@SessionScoped`) để binding dữ liệu 2 chiều (Two-way Data Binding) tự động với giao diện UI.
5. Thực thi kiểm tra dữ liệu đầu vào (Validation) kết hợp giữa thuộc tính `requiredMessage` của JSF và Jakarta Bean Validation (`@NotBlank`, `@Size`, `@Email`, `@Min`, `@DecimalMin`).
6. Hiển thị thông báo lỗi theo từng trường nhập liệu (`h:message`) và thông báo toàn cục (`FacesMessage`, `h:messages`).
7. Hiển thị danh sách dữ liệu động bằng `h:dataTable` kết hợp `f:facet`, `f:convertNumber` và tích hợp các nút thao tác Sửa/Xóa.
8. Hoàn thành toàn bộ bài tập mở rộng (Bài 6 đến Bài 13) gồm: Quản lý Sách, Quản lý Sản phẩm, Đăng nhập, Sửa dữ liệu, Tìm kiếm từ khóa, Template layout Facelets, Dropdown chọn lớp và Báo cáo so sánh.

---

## 2. Cấu trúc Project Maven
```text
lab08-jsf-validation/
├── pom.xml
├── BAO_CAO_LAB8.md
└── src/
    └── main/
        ├── java/
        │   └── vn/edu/eaut/lab8/
        │       ├── bean/
        │       │   ├── LoginBean.java          # CDI Bean xử lý Đăng nhập & Đăng xuất (Bài 8)
        │       │   ├── SachBean.java           # CDI Bean quản lý Sách (Bài 6)
        │       │   ├── SanPhamBean.java        # CDI Bean quản lý Sản phẩm (Bài 7)
        │       │   └── SinhVienBean.java      # CDI Bean quản lý Sinh viên, Tìm kiếm, Sửa, Lớp (Bài 3, 9, 10, 12)
        │       ├── model/
        │       │   ├── Sach.java               # Model Sách + Bean Validation (Bài 6)
        │       │   ├── SanPham.java            # Model Sản phẩm + Bean Validation (Bài 7)
        │       │   └── SinhVien.java           # Model Sinh viên + Bean Validation (Bài 2)
        │       └── repository/
        │           ├── SachRepository.java     # Lưu trữ & CRUD Sách trong bộ nhớ
        │           ├── SanPhamRepository.java  # Lưu trữ & CRUD Sản phẩm trong bộ nhớ
        │           └── SinhVienRepository.java # Lưu trữ, CRUD & Tìm kiếm Sinh viên
        └── webapp/
            ├── index.xhtml                     # Trang chủ điều hướng các bài tập (Bài 1)
            ├── login.xhtml                     # Trang đăng nhập hệ thống (Bài 8)
            ├── product-form.xhtml              # Form nhập sản phẩm (Bài 7)
            ├── product-list.xhtml              # Bảng danh sách sản phẩm (Bài 7)
            ├── sach-form.xhtml                 # Form nhập sách (Bài 6)
            ├── sach-list.xhtml                 # Bảng danh mục sách (Bài 6)
            ├── sinhvien-form.xhtml             # Form nhập/sửa sinh viên + selectOneMenu (Bài 4, 9, 12)
            ├── sinhvien-list.xhtml             # Bảng danh sách sinh viên + Tìm kiếm (Bài 5, 9, 10)
            ├── WEB-INF/
            │   ├── beans.xml                   # Cấu hình CDI Container (bean-discovery-mode="all")
            │   ├── web.xml                     # Cấu hình FacesServlet & Weld Listener
            │   └── templates/
            │       ├── footer.xhtml            # Component chân trang Facelets (Bài 11)
            │       ├── header.xhtml            # Component thanh điều hướng Facelets (Bài 11)
            │       └── template.xhtml          # Layout khung mẫu chính (Bài 11)
            └── resources/
                └── css/
                    └── style.css               # Modern Design System (CSS)
```

---

## 3. Nội dung thực hiện chi tiết

### Bài 1. Tạo trang JSF đầu tiên (`index.xhtml`)
- Tạo file `index.xhtml` làm portal trung tâm kết nối các bài tập.
- Sử dụng các thẻ `h:link` với thuộc tính `outcome` để điều hướng tĩnh qua lại giữa các trang mà không cần qua Servlet Controller thủ công.

### Bài 2. Tạo Model và Repository
- **`SinhVien.java`**: Khai báo các thuộc tính `id`, `maSinhVien`, `hoTen`, `email`, `lop`. Gắn annotation Bean Validation:
  - `@NotBlank` kiểm tra không rỗng.
  - `@Size(min = 5)` bắt buộc họ tên dài từ 5 ký tự.
  - `@Email` kiểm tra định dạng email hợp lệ.
- **`SinhVienRepository.java`**: Dùng `List<SinhVien>` làm kho lưu trữ trong bộ nhớ, cung cấp các phương thức CRUD (`findAll`, `findById`, `add`, `update`, `delete`) và phương thức `search(keyword)`.

### Bài 3. Managed Bean (`SinhVienBean.java`)
- Khai báo `@Named("sinhVienBean")` và `@SessionScoped` để duy trì trạng thái dữ liệu trong phiên làm việc người dùng.
- Tự động nhận dữ liệu từ form JSF thông qua Getter/Setter.
- Phương thức `save()` lưu/cập nhật dữ liệu, bắn tin nhắn thành công qua `FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(...))`.
- Phương thức `delete(id)` xóa sinh viên khỏi repository và tạo thông báo.

### Bài 4. Form JSF có validation và message (`sinhvien-form.xhtml`)
- Sử dụng `<h:form>`, `<h:inputText>`, `<h:message for="...">` và `<h:messages globalOnly="true">`.
- Khi người dùng bỏ trống hoặc nhập sai định dạng, JSF tự động chặn quá trình Render Response, quay lại hiển thị câu thông báo lỗi ngay dưới ô nhập liệu tương ứng.
- Khi thành công, thông báo toàn cục hiển thị màu xanh nhờ CSS `alert-list`.

### Bài 5. Hiển thị danh sách bằng `h:dataTable` (`sinhvien-list.xhtml`)
- Sử dụng `<h:dataTable value="#{sinhVienBean.dsSinhVien}" var="sv">`.
- Khai báo cột bằng `<h:column>` và tiêu đề cột bằng `<f:facet name="header">`.
- Nút xóa gọi trực tiếp method của Bean: `action="#{sinhVienBean.delete(sv.id)}"`.

### Bài 6. Form Sách sang JSF
- **`Sach.java`**: Gắn `@NotBlank` cho tên sách/tác giả, `@Min(value = 1800)` cho năm xuất bản, `@Min(value = 0)` cho giá sách.
- **`SachBean.java`** & **`SachRepository.java`**: Xử lý logic lưu trữ và danh sách sách.
- **`sach-form.xhtml`** & **`sach-list.xhtml`**: Giao diện JSF hoàn chỉnh, có định dạng tiền tệ `f:convertNumber pattern="#,##0 VNĐ"`.

### Bài 7. Form Sản phẩm sang JSF
- **`SanPham.java`**: Gắn `@NotBlank` cho mã/tên SP, `@DecimalMin(value = "0.01")` cho giá > 0, `@Min(value = 0)` cho số lượng >= 0.
- **`SanPhamBean.java`** & **`SanPhamRepository.java`**: Quản lý CRUD sản phẩm.
- **`product-form.xhtml`** & **`product-list.xhtml`**: Giao diện CRUD sản phẩm đầy đủ validation.

### Bài 8. Form đăng nhập JSF
- **`LoginBean.java`**: Nhận `username` và `password`. Kiểm tra mật khẩu (mặc định `123456`).
  - Nêu sai: bắn `FacesMessage.SEVERITY_ERROR` và trả về `null` (giữ nguyên trang login hiển thị lỗi).
  - Nếu đúng: set `loggedIn = true`, bắn `FacesMessage.SEVERITY_INFO` và điều hướng về `index?faces-redirect=true`.
- **`login.xhtml`**: Form đăng nhập đẹp mắt với `h:inputSecret`.

### Bài 9. Sửa thông tin sinh viên
- Trong `SinhVienBean.java`, thêm thuộc tính `isEdit` và phương thức `prepareEdit(SinhVien sv)` để nạp thông tin sinh viên cần sửa lên form.
- Khi người dùng nhấn nút "✏️ Sửa" ở `sinhvien-list.xhtml`, JSF gọi `prepareEdit(sv)` và chuyển sang `sinhvien-form.xhtml`. Form tự chuyển tiêu đề thành "Cập nhật sinh viên" và nút nhấn thành "Cập nhật Sinh viên".

### Bài 10. Tìm kiếm sinh viên
- Trong `SinhVienBean.java`, thêm thuộc tính `keyword` và phương thức `search()`.
- Phương thức `getDsSinhVien()` tự động gọi `repo.search(keyword)` để lọc sinh viên theo Họ tên, Lớp hoặc Mã sinh viên.
- `sinhvien-list.xhtml` tích hợp ô tìm kiếm JSF tiện lợi.

### Bài 11. Layout dùng chung Facelets (`template.xhtml`)
- Xây dựng layout mẫu bằng Facelets:
  - `header.xhtml`: Thanh điều hướng chung (Navigation Bar) chứa logo và menu các bài lab.
  - `footer.xhtml`: Chân trang chứa thông tin bản quyền và môn học.
  - `template.xhtml`: Bộ khung tổng thể chứa `<ui:include src="..." />` và vùng thay đổi `<ui:insert name="content" />`.
- Tất cả các trang view (`index.xhtml`, `sinhvien-form.xhtml`, `sinhvien-list.xhtml`, v.v.) đều kế thừa layout bằng `<ui:composition template="/WEB-INF/templates/template.xhtml">`.

### Bài 12. Dropdown chọn Lớp bằng `h:selectOneMenu`
- Trong `SinhVienBean.java`, cung cấp danh sách tên lớp mẫu (`danhSachLop`).
- Trong `sinhvien-form.xhtml`, thay thế ô nhập text rảnh tay bằng thẻ JSF `<h:selectOneMenu>` kết hợp `<f:selectItems value="#{sinhVienBean.danhSachLop}" />` giúp chuẩn hóa dữ liệu đầu vào.

---

## 4. Bài 13: Báo cáo so sánh Servlet/JSP và JSF

| Tiêu chí so sánh | Servlet + JSP (Lab 7) | Jakarta Faces / JSF (Lab 8) |
| :--- | :--- | :--- |
| **Kiến trúc ứng dụng** | **Action-based (Request-Driven)**: Request gửi trực tiếp tới Servlet URL mapping (ví dụ: `/sinhvien?action=add`). | **Component-based (Event-Driven)**: Quản lý theo cây thành phần UI (Component Tree), sự kiện gắn trực tiếp vào Bean Method. |
| **Nhận dữ liệu từ Form** | Đọc thủ công qua `request.getParameter("name")`, phải ép kiểu dữ liệu từ String sang int, double, date... | **Two-way Data Binding**: JSF tự động bind dữ liệu từ thẻ HTML vào thuộc tính Java Bean và ngược lại (`#{bean.property}`). |
| **Validation dữ liệu** | Phải viết mã Java `if/else` thủ công trong Servlet hoặc dùng script JS client side, tạo List chứa thông báo lỗi rồi đẩy sang request attribute. | **Bean Validation & Built-in Validation**: Dùng Annotation (`@NotBlank`, `@Email`, `@Min`) hoặc thuộc tính `required="true"`. Lỗi tự động gắn vào từng component. |
| **Hiển thị thông báo (Message)** | Đẩy thông báo qua `request.setAttribute("error", msg)`, trang JSP dùng thẻ EL `${error}` hoặc JSTL `<c:if>` để hiển thị. | **FacesMessage System**: Dùng `FacesContext.addMessage()`. Giao diện hiển thị dễ dàng qua `<h:message for="id">` hoặc `<h:messages>`. |
| **Điều hướng (Navigation)** | Dùng `request.getRequestDispatcher("...").forward()` hoặc `response.sendRedirect("...")`. | **Outcome Navigation**: Trả về String tên trang (ví dụ `"sinhvien-list?faces-redirect=true"`), JSF tự động giải quyết URL. |
| **Mức độ tách mã & Tái sử dụng** | JSP thường lộn xộn nếu trộn lẫn JSTL, Scriptlet và HTML; tái sử dụng giao diện bằng `<jsp:include>`. | Tách biệt hoàn toàn logic (Managed Bean) và View (Facelets XHTML); Tái sử dụng linh hoạt bằng `ui:composition`, `ui:include`, Custom Components. |

---

## 5. Trả lời các câu hỏi củng cố (Mục 11)

### 1. JSF xử lý form khác Servlet/JSP ở điểm nào?
- **Servlet/JSP** xử lý theo mô hình **Action-driven**: Mỗi submit form gửi một HTTP POST request đến URL của Servlet. Developer phải tự bóc tách các tham số `request.getParameter()`, tự chuyển kiểu dữ liệu (String -> int/double), tự thực thi validation và tự forward dữ liệu sang trang JSP.
- **JSF** xử lý theo mô hình **Component-based & Lifecycle-driven** (qua 6 giai đoạn Lifecycle: Restore View, Apply Request Values, Process Validations, Update Model Values, Invoke Application, Render Response). JSF tự động liên kết thành phần giao diện với thuộc tính Managed Bean thông qua biểu thức EL (`#{...}`), tự động ép kiểu và tự động kích hoạt validation.

### 2. `h:inputText`, `h:commandButton` và `h:messages` có vai trò gì?
- `<h:inputText>`: Đại diện cho ô nhập dữ liệu văn bản (`<input type="text">`), hỗ trợ ràng buộc dữ liệu 2 chiều với Managed Bean và đăng ký validator.
- `<h:commandButton>`: Đại diện cho nút bấm gửi form (`<input type="submit">`), kích hoạt một Action Method trong Managed Bean khi người dùng nhấp chuột.
- `<h:messages>`: Thành phần hiển thị danh sách các thông báo (FacesMessage) toàn cục hoặc của tất cả các trường trên trang.

### 3. Managed Bean nhận dữ liệu từ giao diện bằng cơ chế nào?
- Managed Bean nhận dữ liệu từ giao diện thông qua cơ chế **Two-way Data Binding** (Ràng buộc dữ liệu 2 chiều) kết hợp với **Jakarta Expression Language (EL)**.
- Trong giai đoạn *Apply Request Values* và *Update Model Values* của vòng đời JSF, framework tự động gọi phương thức Setter (ví dụ: `setHoTen(...)`) của Bean để ghi dữ liệu từ form vào object Java. Khi hiển thị trang (Render Response), JSF tự động gọi phương thức Getter (ví dụ: `getHoTen()`) để đổ dữ liệu lên UI.

### 4. Bean Validation khác `requiredMessage` của JSF ở điểm nào?
- **`requiredMessage` của JSF**: Là cơ chế validation tích hợp sẵn ở tầng UI component của JSF (chỉ kiểm tra thuộc tính `required="true"`). Thông báo lỗi này chỉ áp dụng tại trang JSF cụ thể đó.
- **Bean Validation (Jakarta Validation / Hibernate Validator)**: Là tiêu chuẩn validation ở tầng Model / Domain Objects bằng Annotation (`@NotBlank`, `@Size`, `@Email`, `@Min`). Ưu điểm là validation nằm trực tiếp tại class Java Model, có thể tái sử dụng cho nhiều tầng khác nhau (JSF, REST API, JPA Persistence Layer) mà không bị phụ thuộc vào giao diện.

### 5. Vì sao Lab 9 mới nên tích hợp JPA, Entity, Repository và transaction?
- **Phân tách mục tiêu học tập (Separation of Concerns)**: Lab 8 tập trung hoàn toàn vào việc giúp sinh viên nắm vững kiến thức tầng Presentation Layer của JSF (Facelets XHTML, Component Tree, Lifecycle, CDI Managed Bean, Bean Validation và FacesMessage).
- Việc tạm thời sử dụng in-memory `List` ở Lab 8 giúp code đơn giản, chạy nhanh, không bị xao nhãng bởi các lỗi cấu hình cơ sở dữ liệu (Database Connection, Dialect, Driver). Sau khi đã làm chủ JSF ở Lab 8, Lab 9 sẽ nối tiếp bằng cách đưa JPA/Hibernate Entity và Transaction vào thay thế cho in-memory List để hoàn thiện kiến trúc Đa lớp (Multi-tier Architecture) chuẩn Jakarta EE.
