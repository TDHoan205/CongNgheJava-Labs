package vn.edu.eaut.lab15.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.eaut.lab15.entity.Student;
import vn.edu.eaut.lab15.repository.StudentRepository;
import java.util.List;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với ID: " + id));
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Student> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return studentRepository.findAll();
        }
        String kw = keyword.trim();
        return studentRepository.findByFullNameContainingIgnoreCaseOrStudentCodeContainingIgnoreCaseOrClassNameContainingIgnoreCase(kw, kw, kw);
    }

    @Transactional(readOnly = true)
    public boolean isStudentCodeExists(String studentCode, Long excludeId) {
        if (studentCode == null || studentCode.trim().isEmpty()) return false;
        String code = studentCode.trim();
        if (excludeId == null) {
            return studentRepository.existsByStudentCode(code);
        } else {
            return studentRepository.existsByStudentCodeAndIdNot(code, excludeId);
        }
    }

    @Transactional(readOnly = true)
    public long count() {
        return studentRepository.count();
    }
}
