package vn.edu.eaut.lab12.service;

import org.springframework.stereotype.Service;
import vn.edu.eaut.lab12.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final List<Student> students = new ArrayList<>();
    private long nextId = 1;

    public StudentService() {
        // Sample seed data
        save(new Student(null, "SV001", "Nguyễn Văn An", "an@eaut.edu.vn", "DCCNTT13.10.1"));
        save(new Student(null, "SV002", "Trần Thị Bình", "binh@eaut.edu.vn", "DCCNTT13.10.2"));
        save(new Student(null, "SV003", "Lê Văn Cường", "cuong@eaut.edu.vn", "DCCNTT13.10.3"));
        save(new Student(null, "SV2030022", "Trần Đức Hoàn", "hoan.td@eaut.edu.vn", "DCCNTT14.10.1"));
    }

    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    public Student findById(Long id) {
        if (id == null) return null;
        return students.stream()
                .filter(s -> id.equals(s.getId()))
                .findFirst()
                .orElse(null);
    }

    public Student findByStudentCode(String studentCode) {
        if (studentCode == null || studentCode.trim().isEmpty()) return null;
        return students.stream()
                .filter(s -> studentCode.trim().equalsIgnoreCase(s.getStudentCode()))
                .findFirst()
                .orElse(null);
    }

    public boolean isStudentCodeExists(String studentCode, Long excludeId) {
        if (studentCode == null || studentCode.trim().isEmpty()) return false;
        return students.stream()
                .anyMatch(s -> studentCode.trim().equalsIgnoreCase(s.getStudentCode()) 
                            && (excludeId == null || !excludeId.equals(s.getId())));
    }

    public void save(Student student) {
        if (student.getId() == null) {
            student.setId(nextId++);
            students.add(student);
        } else {
            Student existing = findById(student.getId());
            if (existing != null) {
                existing.setStudentCode(student.getStudentCode());
                existing.setFullName(student.getFullName());
                existing.setEmail(student.getEmail());
                existing.setClassName(student.getClassName());
            }
        }
    }

    public void deleteById(Long id) {
        if (id == null) return;
        students.removeIf(s -> id.equals(s.getId()));
    }

    public List<Student> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        String kw = keyword.toLowerCase().trim();
        return students.stream()
                .filter(s -> s.getFullName().toLowerCase().contains(kw) 
                          || s.getStudentCode().toLowerCase().contains(kw)
                          || s.getClassName().toLowerCase().contains(kw))
                .collect(Collectors.toList());
    }
}
