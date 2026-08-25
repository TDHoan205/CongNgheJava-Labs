package vn.edu.eaut.lab10.model;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testRequiredFieldsExist() throws Exception {
        List<String> names = Arrays.stream(User.class.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toList());
        assertTrue(names.contains("id"));
        assertTrue(names.contains("email"));
        assertTrue(names.contains("password"));
        assertTrue(names.contains("fullName"));
        assertTrue(names.contains("role"));
        assertTrue(names.contains("active"));
        assertEquals(6, names.size());
    }

    @Test
    void testFieldTypes() throws Exception {
        assertEquals(Integer.class, User.class.getDeclaredField("id").getType());
        assertEquals(String.class, User.class.getDeclaredField("email").getType());
        assertEquals(String.class, User.class.getDeclaredField("password").getType());
        assertEquals(String.class, User.class.getDeclaredField("fullName").getType());
        assertEquals(Role.class, User.class.getDeclaredField("role").getType());
        assertEquals(boolean.class, User.class.getDeclaredField("active").getType());
    }

    @Test
    void testRoleMappingUsesLab10Enum() {
        User user = new User("admin@eaut.edu.vn", "admin123", "Quan tri", Role.ADMIN, true);
        assertEquals(Role.ADMIN, user.getRole());
        user.setRole(Role.STAFF);
        assertEquals(Role.STAFF, user.getRole());
        user.setRole(Role.USER);
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    void testDefaultActiveAndAccessors() {
        User user = new User();
        assertTrue(user.isActive());
        user.setId(1);
        user.setEmail("staff@eaut.edu.vn");
        user.setPassword("staff123");
        user.setFullName("Nhan vien");
        user.setRole(Role.STAFF);
        user.setActive(false);
        assertEquals(Integer.valueOf(1), user.getId());
        assertEquals("staff@eaut.edu.vn", user.getEmail());
        assertEquals("staff123", user.getPassword());
        assertEquals("Nhan vien", user.getFullName());
        assertEquals(Role.STAFF, user.getRole());
        assertFalse(user.isActive());
    }
}
