package vn.edu.eaut.lab14.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab14.entity.Student;
import vn.edu.eaut.lab14.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("students", studentService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "students/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("pageTitle", "Thêm Sinh viên mới");
        return "students/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("student") Student student,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        // Unique StudentCode validation
        if (student.getStudentCode() != null && !student.getStudentCode().trim().isEmpty()) {
            if (studentService.isStudentCodeExists(student.getStudentCode(), student.getId())) {
                result.rejectValue("studentCode", "error.studentCode", "Mã sinh viên '" + student.getStudentCode() + "' đã tồn tại trong CSDL!");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("pageTitle", student.getId() == null ? "Thêm Sinh viên mới" : "Cập nhật Sinh viên");
            return "students/form";
        }

        boolean isNew = student.getId() == null;
        studentService.save(student);
        redirectAttributes.addFlashAttribute("successMessage", isNew ? "Thêm mới sinh viên '" + student.getFullName() + "' thành công vào CSDL!" : "Cập nhật thông tin sinh viên '" + student.getFullName() + "' thành công!");

        return "redirect:/students";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        try {
            Student student = studentService.findById(id);
            model.addAttribute("student", student);
            return "students/detail";
        } catch (Exception e) {
            return "redirect:/students";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        try {
            Student student = studentService.findById(id);
            model.addAttribute("student", student);
            model.addAttribute("pageTitle", "Cập nhật Sinh viên");
            return "students/form";
        } catch (Exception e) {
            return "redirect:/students";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Student student = studentService.findById(id);
            studentService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa sinh viên '" + student.getFullName() + "' khỏi CSDL!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa sinh viên với ID: " + id);
        }
        return "redirect:/students";
    }
}
