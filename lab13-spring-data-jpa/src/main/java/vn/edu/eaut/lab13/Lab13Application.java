package vn.edu.eaut.lab13;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.eaut.lab13.entity.Course;
import vn.edu.eaut.lab13.entity.Student;
import vn.edu.eaut.lab13.repository.CourseRepository;
import vn.edu.eaut.lab13.repository.StudentRepository;

@SpringBootApplication
public class Lab13Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab13Application.class, args);
        System.out.println("=================================================");
        System.out.println("🚀 Lab 13 Spring Data JPA Started Successfully!");
        System.out.println("👉 Access Student App at: http://localhost:8080/students");
        System.out.println("👉 Access H2 Console at:  http://localhost:8080/h2-console");
        System.out.println("=================================================");
    }

    @Bean
    public CommandLineRunner dataInitializer(StudentRepository studentRepository, CourseRepository courseRepository) {
        return args -> {
            if (studentRepository.count() == 0) {
                studentRepository.save(new Student("SV001", "Nguyễn Văn An", "an@eaut.edu.vn", "DCCNTT13.10.1"));
                studentRepository.save(new Student("SV002", "Trần Thị Bình", "binh@eaut.edu.vn", "DCCNTT13.10.2"));
                studentRepository.save(new Student("SV003", "Lê Văn Cường", "cuong@eaut.edu.vn", "DCCNTT13.10.3"));
                studentRepository.save(new Student("SV2030022", "Trần Đức Hoàn", "hoan.td@eaut.edu.vn", "DCCNTT14.10.1"));
                System.out.println("✅ Sample Student data seeded to Database!");
            }

            if (courseRepository.count() == 0) {
                courseRepository.save(new Course("IT3242", "Công nghệ Java", 3));
                courseRepository.save(new Course("IT3110", "Cơ sở dữ liệu", 3));
                courseRepository.save(new Course("IT3200", "Lập trình Web & Frameworks", 3));
                courseRepository.save(new Course("IT3150", "Cấu trúc dữ liệu và giải thuật", 4));
                courseRepository.save(new Course("IT4100", "Phát triển ứng dụng với Spring Framework", 3));
                System.out.println("✅ Sample Course data seeded to Database!");
            }
        };
    }
}
