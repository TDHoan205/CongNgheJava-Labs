package vn.edu.eaut.lab14.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Bài 2 + Bài 6: Cấu hình bảo mật Spring Security.
 *
 * Quy tắc phân quyền URL:
 *   - URL công khai (không cần đăng nhập): "/", "/login", "/css/**", "/h2-console/**"
 *   - URL chỉ ADMIN:  "/courses/**"
 *   - URL cần đăng nhập (ADMIN hoặc USER): "/students/**", "/logout"
 *   - Mọi URL còn lại: phải đăng nhập
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Tắt CSRF cho H2 console (chỉ dùng khi dev)
            .csrf(csrf -> csrf
                .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
            )
            // Cho phép frame H2 console
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            )
            // Bài 2 + Bài 6: Phân quyền URL
            .authorizeHttpRequests(auth -> auth
                // Công khai
                .requestMatchers("/", "/login", "/error", "/css/**", "/js/**", "/images/**", "/h2-console/**").permitAll()
                // Bài 6: /courses/** chỉ ADMIN
                .requestMatchers("/courses/**").hasRole("ADMIN")
                // Thêm/sửa/xóa sinh viên chỉ ADMIN
                .requestMatchers("/students/create", "/students/save", "/students/edit/**", "/students/delete/**").hasRole("ADMIN")
                // Xem danh sách / chi tiết sinh viên - cả ADMIN và USER
                .requestMatchers("/students", "/students/detail/**").hasAnyRole("ADMIN", "USER")
                // Logout cần đăng nhập
                .requestMatchers("/logout").authenticated()
                // Mặc định: phải đăng nhập
                .anyRequest().authenticated()
            )
            // Bài 4: Form login tùy chỉnh
            .formLogin(form -> form
                .loginPage("/login")                          // Trang login custom
                .loginProcessingUrl("/login")                 // URL submit form
                .usernameParameter("username")                // Tên field username
                .passwordParameter("password")                // Tên field password
                .defaultSuccessUrl("/students", true)         // Login thành công -> /students
                .failureUrl("/login?error=true")              // Login sai -> quay lại /login?error
                .permitAll()
            )
            // Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            // Bài 7: Trang 403 tùy chỉnh
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/error/403")
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.sendRedirect(request.getContextPath() + "/error/403");
                })
            );

        return http.build();
    }
}
