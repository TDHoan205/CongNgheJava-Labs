package vn.edu.eaut.lab11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Hệ thống quản lý sinh viên - Lab 11");
        model.addAttribute("message", "Chào mừng đến với ứng dụng Spring Boot & Thymeleaf!");
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("course", "Công nghệ Java (IT3242)");
        model.addAttribute("chapter", "Chương 4 - Phát triển ứng dụng với Spring Framework");
        model.addAttribute("description", "Bài thực hành Lab 11 giúp sinh viên làm quen với kiến trúc Spring MVC, Spring Boot Initializr và cơ chế render template động của Thymeleaf.");
        return "about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("faculty", "Khoa Công nghệ Thông tin");
        model.addAttribute("university", "Trường Đại học Công nghệ Đông Á");
        model.addAttribute("address", "Đường Trịnh Văn Bô, Phương Canh, Nam Từ Liêm, Hà Nội");
        model.addAttribute("email", "cntt@eaut.edu.vn");
        model.addAttribute("phone", "(+84) 24 6262 7796");
        return "contact";
    }
}
