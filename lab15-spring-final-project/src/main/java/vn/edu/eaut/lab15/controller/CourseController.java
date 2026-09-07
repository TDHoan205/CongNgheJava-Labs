package vn.edu.eaut.lab15.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab15.entity.Course;
import vn.edu.eaut.lab15.service.CourseService;
import vn.edu.eaut.lab15.service.EnrollmentService;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public CourseController(CourseService courseService, EnrollmentService enrollmentService) {
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("courses", courseService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "courses/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("pageTitle", "Thêm Môn học mới");
        return "courses/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("course") Course course,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (course.getCourseCode() != null && !course.getCourseCode().trim().isEmpty()) {
            if (courseService.isCourseCodeExists(course.getCourseCode(), course.getId())) {
                result.rejectValue("courseCode", "error.courseCode", "Mã môn học '" + course.getCourseCode() + "' đã tồn tại!");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("pageTitle", course.getId() == null ? "Thêm Môn học mới" : "Cập nhật Môn học");
            return "courses/form";
        }

        boolean isNew = course.getId() == null;
        courseService.save(course);
        redirectAttributes.addFlashAttribute("successMessage", isNew ? "Thêm mới môn học '" + course.getCourseName() + "' thành công!" : "Cập nhật môn học '" + course.getCourseName() + "' thành công!");

        return "redirect:/courses";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        try {
            Course course = courseService.findById(id);
            model.addAttribute("course", course);
            model.addAttribute("enrollments", enrollmentService.findByCourseId(id));
            return "courses/detail";
        } catch (Exception e) {
            return "redirect:/courses";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        try {
            Course course = courseService.findById(id);
            model.addAttribute("course", course);
            model.addAttribute("pageTitle", "Cập nhật Môn học");
            return "courses/form";
        } catch (Exception e) {
            return "redirect:/courses";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Course course = courseService.findById(id);
            courseService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa môn học '" + course.getCourseName() + "' thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa môn học với ID: " + id);
        }
        return "redirect:/courses";
    }
}
