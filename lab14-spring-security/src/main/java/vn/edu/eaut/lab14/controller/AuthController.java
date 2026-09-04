package vn.edu.eaut.lab14.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Bài 4: AuthController - Controller cho trang đăng nhập tùy chỉnh.
 */
@Controller
public class AuthController {

    /**
     * Trang đăng nhập.
     * Nếu user đã đăng nhập rồi thì redirect về /students.
     */
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng!");
        }
        if (logout != null) {
            model.addAttribute("successMessage", "Bạn đã đăng xuất thành công!");
        }
        return "auth/login";
    }
}
