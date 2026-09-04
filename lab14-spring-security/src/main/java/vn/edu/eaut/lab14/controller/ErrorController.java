package vn.edu.eaut.lab14.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Bài 7: ErrorController - Trang báo lỗi 403 khi truy cập trái phép.
 */
@Controller
public class ErrorController {

    @GetMapping("/error/403")
    public String accessDenied() {
        return "error/403";
    }
}
