package vn.edu.eaut.lab15.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab15.entity.Student;
import vn.edu.eaut.lab15.service.CourseService;
import vn.edu.eaut.lab15.service.EnrollmentService;
import vn.edu.eaut.lab15.service.StudentService;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentController(EnrollmentService enrollmentService,
                                StudentService studentService,
                                CourseService courseService) {
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    // Bài 6: Xem danh sách toàn bộ đăng ký học phần
    @GetMapping
    public String list(Model model) {
        model.addAttribute("enrollments", enrollmentService.findAll());
        return "enrollments/list";
    }

    // Bài 5: Form đăng ký học phần
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("courses", courseService.findAll());
        return "enrollments/form";
    }

    // Bài 5: Xử lý lưu đăng ký học phần, kiểm tra trùng lặp
    @PostMapping("/save")
    public String save(@RequestParam Long studentId,
                       @RequestParam Long courseId,
                       RedirectAttributes redirectAttributes) {
        try {
            enrollmentService.enroll(studentId, courseId);
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký học phần thành công!");
            return "redirect:/enrollments";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/enrollments/create";
        }
    }

    // Bài 7: Hủy đăng ký học phần
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            enrollmentService.cancel(id);
            redirectAttributes.addFlashAttribute("successMessage", "Hủy đăng ký học phần thành công!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể hủy đăng ký: " + ex.getMessage());
        }
        return "redirect:/enrollments";
    }

    // Bài 8: Xem danh sách môn học mà một sinh viên cụ thể đã đăng ký
    @GetMapping("/student/{studentId}")
    public String viewStudentCourses(@PathVariable Long studentId, Model model) {
        try {
            Student student = studentService.findById(studentId);
            model.addAttribute("student", student);
            model.addAttribute("enrollments", enrollmentService.findByStudentId(studentId));
            return "enrollments/student_courses";
        } catch (Exception ex) {
            return "redirect:/enrollments";
        }
    }
}
