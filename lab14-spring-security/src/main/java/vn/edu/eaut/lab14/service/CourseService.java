package vn.edu.eaut.lab14.service;

import org.springframework.stereotype.Service;
import vn.edu.eaut.lab14.entity.Course;
import vn.edu.eaut.lab14.repository.CourseRepository;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học với ID: " + id));
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    public List<Course> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return courseRepository.findAll();
        }
        String kw = keyword.trim();
        return courseRepository.findByCourseNameContainingIgnoreCaseOrCourseCodeContainingIgnoreCase(kw, kw);
    }

    public boolean isCourseCodeExists(String courseCode, Long excludeId) {
        if (courseCode == null || courseCode.trim().isEmpty()) return false;
        String code = courseCode.trim();
        if (excludeId == null) {
            return courseRepository.existsByCourseCode(code);
        } else {
            return courseRepository.existsByCourseCodeAndIdNot(code, excludeId);
        }
    }
}
