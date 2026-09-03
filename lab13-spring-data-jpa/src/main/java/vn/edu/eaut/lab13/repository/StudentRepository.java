package vn.edu.eaut.lab13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.eaut.lab13.entity.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByFullNameContainingIgnoreCase(String keyword);

    List<Student> findByFullNameContainingIgnoreCaseOrStudentCodeContainingIgnoreCaseOrClassNameContainingIgnoreCase(
            String fullName, String studentCode, String className);

    boolean existsByStudentCode(String studentCode);

    boolean existsByStudentCodeAndIdNot(String studentCode, Long id);
}
