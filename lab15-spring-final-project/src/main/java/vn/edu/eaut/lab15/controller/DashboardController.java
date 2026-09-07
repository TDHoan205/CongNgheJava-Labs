package vn.edu.eaut.lab15.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.edu.eaut.lab15.service.CourseService;
import vn.edu.eaut.lab15.service.EnrollmentService;
import vn.edu.eaut.lab15.service.StudentService;

@Controller
public class DashboardController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public DashboardController(StudentService studentService,
                               CourseService courseService,
                               EnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        // Bài 9: Thống kê tổng số sinh viên, tổng số môn học, tổng số lượt đăng ký
        model.addAttribute("totalStudents", studentService.count());
        model.addAttribute("totalCourses", courseService.count());
        model.addAttribute("totalEnrollments", enrollmentService.count());

        model.addAttribute("recentStudents", studentService.findAll());
        model.addAttribute("recentCourses", courseService.findAll());
        model.addAttribute("recentEnrollments", enrollmentService.findAll());

        return "dashboard";
    }
}
