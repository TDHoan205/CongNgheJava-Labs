package vn.edu.eaut.lab10.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void testRequiredRolesExist() {
        assertEquals(3, Role.values().length);
        assertNotNull(Role.ADMIN);
        assertNotNull(Role.STAFF);
        assertNotNull(Role.USER);
    }

    @Test
    void testValueOfMatchesRequirementNames() {
        assertEquals(Role.ADMIN, Role.valueOf("ADMIN"));
        assertEquals(Role.STAFF, Role.valueOf("STAFF"));
        assertEquals(Role.USER, Role.valueOf("USER"));
    }

    @Test
    void testIsDefined() {
        assertTrue(Role.isDefined("ADMIN"));
        assertTrue(Role.isDefined("staff"));
        assertTrue(Role.isDefined("User"));
        assertFalse(Role.isDefined("ROLE_ADMIN"));
        assertFalse(Role.isDefined("GUEST"));
        assertFalse(Role.isDefined(null));
        assertFalse(Role.isDefined(""));
    }
}
