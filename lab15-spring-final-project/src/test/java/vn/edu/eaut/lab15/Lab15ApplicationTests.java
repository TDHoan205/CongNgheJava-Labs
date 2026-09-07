package vn.edu.eaut.lab15;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.edu.eaut.lab15.entity.Course;
import vn.edu.eaut.lab15.entity.Enrollment;
import vn.edu.eaut.lab15.entity.Student;
import vn.edu.eaut.lab15.service.CourseService;
import vn.edu.eaut.lab15.service.EnrollmentService;
import vn.edu.eaut.lab15.service.StudentService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Lab15ApplicationTests {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Test
    @DisplayName("Kiểm tra dữ liệu mẫu tự động khởi tạo khi chạy ứng dụng")
    void testInitialDataLoaded() {
        assertTrue(studentService.count() >= 4, "Phải có ít nhất 4 sinh viên mẫu");
        assertTrue(courseService.count() >= 5, "Phải có ít nhất 5 môn học mẫu");
        assertTrue(enrollmentService.count() >= 4, "Phải có ít nhất 4 lượt đăng ký mẫu");
    }

    @Test
    @DisplayName("Kiểm tra logic đăng ký học phần & chống đăng ký trùng")
    void testEnrollmentDuplicatePrevention() {
        Student sv = studentService.save(new Student("SV999", "Test Student", "test@eaut.edu.vn", "CNTT14"));
        Course c = courseService.save(new Course("IT9999", "Test Course", 3));

        // Lần 1: Đăng ký thành công
        Enrollment e = enrollmentService.enroll(sv.getId(), c.getId());
        assertNotNull(e.getId());

        // Lần 2: Đăng ký lại cùng sinh viên và môn học -> Bắt buộc ném ngoại lệ RuntimeException
        Exception exception = assertThrows(RuntimeException.class, () -> {
            enrollmentService.enroll(sv.getId(), c.getId());
        });
        assertTrue(exception.getMessage().contains("đã đăng ký môn học này"));

        // Kiểm tra tra cứu danh sách môn học của sinh viên
        List<Enrollment> svEnrollments = enrollmentService.findByStudentId(sv.getId());
        assertEquals(1, svEnrollments.size());

        // Kiểm tra hủy đăng ký
        enrollmentService.cancel(e.getId());
        assertEquals(0, enrollmentService.findByStudentId(sv.getId()).size());
    }

    @Test
    @DisplayName("Kiểm tra tìm kiếm Sinh viên và Môn học")
    void testSearchFunctionality() {
        List<Student> students = studentService.search("Hoàn");
        assertFalse(students.isEmpty(), "Tìm kiếm sinh viên 'Hoàn' phải có kết quả");

        List<Course> courses = courseService.search("Java");
        assertFalse(courses.isEmpty(), "Tìm kiếm môn học 'Java' phải có kết quả");
    }
}
