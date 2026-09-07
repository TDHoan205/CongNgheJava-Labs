package vn.edu.eaut.lab15;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.eaut.lab15.entity.Course;
import vn.edu.eaut.lab15.entity.Enrollment;
import vn.edu.eaut.lab15.entity.Student;
import vn.edu.eaut.lab15.repository.CourseRepository;
import vn.edu.eaut.lab15.repository.EnrollmentRepository;
import vn.edu.eaut.lab15.repository.StudentRepository;
import java.time.LocalDate;

@SpringBootApplication
public class Lab15Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab15Application.class, args);
        System.out.println("=================================================");
        System.out.println("🚀 Lab 15 Spring Boot Final Application Started!");
        System.out.println("👉 Access Web App at: http://localhost:8080");
        System.out.println("👉 Login with: admin / admin123  or  user / user123");
        System.out.println("=================================================");
    }

    @Bean
    public CommandLineRunner initDatabase(StudentRepository studentRepository,
                                          CourseRepository courseRepository,
                                          EnrollmentRepository enrollmentRepository) {
        return args -> {
            if (studentRepository.count() == 0) {
                Student sv1 = studentRepository.save(new Student("SV001", "Nguyễn Văn An", "an@eaut.edu.vn", "DCCNTT13.10.1"));
                Student sv2 = studentRepository.save(new Student("SV002", "Trần Thị Bình", "binh@eaut.edu.vn", "DCCNTT13.10.2"));
                Student sv3 = studentRepository.save(new Student("SV003", "Lê Văn Cường", "cuong@eaut.edu.vn", "DCCNTT13.10.3"));
                Student sv4 = studentRepository.save(new Student("SV2030022", "Trần Đức Hoàn", "hoan.td@eaut.edu.vn", "DCCNTT14.10.1"));

                Course c1 = courseRepository.save(new Course("IT3242", "Công nghệ Java", 3));
                Course c2 = courseRepository.save(new Course("IT3110", "Cơ sở dữ liệu", 3));
                Course c3 = courseRepository.save(new Course("IT3200", "Lập trình Web & Frameworks", 3));
                Course c4 = courseRepository.save(new Course("IT3150", "Cấu trúc dữ liệu và giải thuật", 4));
                Course c5 = courseRepository.save(new Course("IT4100", "Phát triển ứng dụng với Spring Framework", 3));

                enrollmentRepository.save(new Enrollment(1L, sv4, c1, LocalDate.now().minusDays(3)));
                enrollmentRepository.save(new Enrollment(2L, sv4, c5, LocalDate.now().minusDays(2)));
                enrollmentRepository.save(new Enrollment(3L, sv1, c1, LocalDate.now().minusDays(1)));
                enrollmentRepository.save(new Enrollment(4L, sv2, c2, LocalDate.now()));

                System.out.println("✅ Sample Students, Courses, and Enrollments successfully seeded!");
            }
        };
    }
}
