package vn.edu.eaut.lab14;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.edu.eaut.lab14.entity.AppUser;
import vn.edu.eaut.lab14.entity.Course;
import vn.edu.eaut.lab14.entity.Student;
import vn.edu.eaut.lab14.repository.AppUserRepository;
import vn.edu.eaut.lab14.repository.CourseRepository;
import vn.edu.eaut.lab14.repository.StudentRepository;

@SpringBootApplication
public class Lab14Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab14Application.class, args);
        System.out.println("=================================================");
        System.out.println("🚀 Lab 14 Spring Security Started Successfully!");
        System.out.println("👉 Login at: http://localhost:8080/login");
        System.out.println("👉 admin / 123456  (ADMIN role)");
        System.out.println("👉 user  / 123456  (USER role)");
        System.out.println("=================================================");
    }

    @Bean
    public CommandLineRunner dataInitializer(StudentRepository studentRepository,
                                              CourseRepository courseRepository,
                                              AppUserRepository appUserRepository,
                                              PasswordEncoder passwordEncoder) {
        return args -> {
            // Seed Students (giữ nguyên từ Lab 13)
            if (studentRepository.count() == 0) {
                studentRepository.save(new Student("SV001", "Nguyễn Văn An", "an@eaut.edu.vn", "DCCNTT13.10.1"));
                studentRepository.save(new Student("SV002", "Trần Thị Bình", "binh@eaut.edu.vn", "DCCNTT13.10.2"));
                studentRepository.save(new Student("SV003", "Lê Văn Cường", "cuong@eaut.edu.vn", "DCCNTT13.10.3"));
                studentRepository.save(new Student("SV2030022", "Trần Đức Hoàn", "hoan.td@eaut.edu.vn", "DCCNTT14.10.1"));
                System.out.println("✅ Sample Student data seeded to Database!");
            }

            // Seed Courses (giữ nguyên từ Lab 13)
            if (courseRepository.count() == 0) {
                courseRepository.save(new Course("IT3242", "Công nghệ Java", 3));
                courseRepository.save(new Course("IT3110", "Cơ sở dữ liệu", 3));
                courseRepository.save(new Course("IT3200", "Lập trình Web & Frameworks", 3));
                courseRepository.save(new Course("IT3150", "Cấu trúc dữ liệu và giải thuật", 4));
                courseRepository.save(new Course("IT4100", "Phát triển ứng dụng với Spring Framework", 3));
                System.out.println("✅ Sample Course data seeded to Database!");
            }

            // Bài 3 + Bài 10: Seed user trong CSDL với mật khẩu BCrypt
            if (appUserRepository.count() == 0) {
                AppUser admin = new AppUser("admin", passwordEncoder.encode("123456"), "ADMIN");
                AppUser user  = new AppUser("user",  passwordEncoder.encode("123456"), "USER");
                appUserRepository.save(admin);
                appUserRepository.save(user);
                System.out.println("✅ Default users seeded: admin/123456 (ADMIN), user/123456 (USER)");
            }
        };
    }
}
