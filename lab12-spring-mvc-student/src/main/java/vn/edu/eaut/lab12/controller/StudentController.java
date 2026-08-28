package vn.edu.eaut.lab12.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eaut.lab12.model.Student;
import vn.edu.eaut.lab12.service.StudentService;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/students";
    }

    @GetMapping("/students")
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("students", studentService.searchByName(keyword));
        model.addAttribute("keyword", keyword);
        return "students/list";
    }

    @GetMapping("/students/create")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("pageTitle", "Thêm Sinh viên mới");
        return "students/form";
    }

    @PostMapping("/students/save")
    public String save(@Valid @ModelAttribute("student") Student student,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        // Bài 10: Validation kiểm tra mã sinh viên trùng trong danh sách
        if (student.getStudentCode() != null && !student.getStudentCode().trim().isEmpty()) {
            if (studentService.isStudentCodeExists(student.getStudentCode(), student.getId())) {
                result.rejectValue("studentCode", "error.studentCode", "Mã sinh viên '" + student.getStudentCode() + "' đã tồn tại trong hệ thống!");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("pageTitle", student.getId() == null ? "Thêm Sinh viên mới" : "Cập nhật Sinh viên");
            return "students/form";
        }

        boolean isNew = student.getId() == null;
        studentService.save(student);
        redirectAttributes.addFlashAttribute("successMessage", isNew ? "Thêm mới sinh viên '" + student.getFullName() + "' thành công!" : "Cập nhật sinh viên '" + student.getFullName() + "' thành công!");

        return "redirect:/students";
    }

    @GetMapping("/students/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Student student = studentService.findById(id);
        if (student == null) {
            return "redirect:/students";
        }
        model.addAttribute("student", student);
        return "students/detail";
    }

    @GetMapping("/students/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Student student = studentService.findById(id);
        if (student == null) {
            return "redirect:/students";
        }
        model.addAttribute("student", student);
        model.addAttribute("pageTitle", "Cập nhật Sinh viên");
        return "students/form";
    }

    @GetMapping("/students/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Student student = studentService.findById(id);
        if (student != null) {
            studentService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa sinh viên '" + student.getFullName() + "' khỏi danh sách!");
        }
        return "redirect:/students";
    }
}
