package vn.edu.eaut.lab6.store;

import vn.edu.eaut.lab6.model.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class StudentStore {
    private static final List<Student> students = new CopyOnWriteArrayList<>();

    static {
        // Initial sample data per lab guide (can also be augmented by DataInitListener)
        students.add(new Student("SV001", "Nguyen Van An", "DCCNTT12", "an@example.com"));
        students.add(new Student("SV002", "Tran Thi Binh", "DCCNTT12", "binh@example.com"));
    }

    public static List<Student> findAll() {
        return new ArrayList<>(students);
    }

    public static Student findById(String id) {
        if (id == null) return null;
        return students.stream()
                .filter(s -> id.trim().equalsIgnoreCase(s.getId().trim()))
                .findFirst()
                .orElse(null);
    }

    public static List<Student> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        String lowerKeyword = keyword.trim().toLowerCase();
        return students.stream()
                .filter(s -> s.getName() != null && s.getName().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    public static void add(Student student) {
        if (student != null && student.getId() != null) {
            // Remove existing if any to avoid duplicate ID
            delete(student.getId());
            students.add(student);
        }
    }

    public static boolean update(Student student) {
        if (student == null || student.getId() == null) return false;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equalsIgnoreCase(student.getId())) {
                students.set(i, student);
                return true;
            }
        }
        return false;
    }

    public static boolean delete(String id) {
        if (id == null) return false;
        return students.removeIf(s -> s.getId().equalsIgnoreCase(id.trim()));
    }

    public static int getTotalCount() {
        return students.size();
    }

    public static Map<String, Long> getCountByClass() {
        return students.stream()
                .filter(s -> s.getClassName() != null && !s.getClassName().trim().isEmpty())
                .collect(Collectors.groupingBy(Student::getClassName, Collectors.counting()));
    }

    public static void clear() {
        students.clear();
    }
}
