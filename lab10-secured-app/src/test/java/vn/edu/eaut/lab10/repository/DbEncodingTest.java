package vn.edu.eaut.lab10.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;
import vn.edu.eaut.lab10.model.User;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DbEncodingTest {

    @Test
    void testVietnameseFromDatabase() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab10PU");
        EntityManager em = emf.createEntityManager();
        try {
            List<User> users = em.createQuery("SELECT u FROM User u ORDER BY u.id", User.class).getResultList();
            System.out.println("===== USERS FROM DB =====");
            for (User u : users) {
                String fullName = u.getFullName();
                System.out.println("id=" + u.getId() + " email=" + u.getEmail()
                        + " fullName=" + fullName
                        + " [bytes=" + bytesHex(fullName) + "]");
            }
            System.out.println("=========================");

            User admin = users.stream()
                    .filter(u -> "admin@test.com".equals(u.getEmail()))
                    .findFirst().orElseThrow();
            assertEquals("Quản trị viên", admin.getFullName(), "Vietnamese fullName from DB must equal expected UTF-8 string");

            User staff = users.stream()
                    .filter(u -> "staff@test.com".equals(u.getEmail()))
                    .findFirst().orElseThrow();
            assertEquals("Nhân viên", staff.getFullName(), "Vietnamese fullName from DB must equal expected UTF-8 string");
        } finally {
            em.close();
            emf.close();
        }
    }

    private String bytesHex(String s) {
        byte[] b = s.getBytes(StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        for (byte x : b) sb.append(String.format("%02X ", x));
        return sb.toString().trim();
    }
}
