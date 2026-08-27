package vn.edu.eaut.lab11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.edu.eaut.lab11.model.Course;

import java.util.List;

@Controller
public class CourseController {

    @GetMapping("/courses")
    public String listCourses(Model model) {
        List<Course> courses = List.of(
            new Course("IT3242", "Công nghệ Java", 3),
            new Course("IT3110", "Cơ sở dữ liệu", 3),
            new Course("IT3200", "Lập trình Web & Frameworks", 3),
            new Course("IT3150", "Cấu trúc dữ liệu và giải thuật", 4),
            new Course("IT4100", "Phát triển ứng dụng với Spring Framework", 3)
        );

        model.addAttribute("title", "Danh sách Khóa học mẫu");
        model.addAttribute("courses", courses);
        return "courses";
    }
}
