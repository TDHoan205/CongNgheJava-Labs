package vn.edu.eaut.lab10.model;

/**
 * Roles of Lab 10. Values match the authorization rules in docs/01_YEU_CAU.md.
 * Not wired to login, filter, or user CRUD in this task.
 */
public enum Role {
    ADMIN,
    STAFF,
    USER;

    public static boolean isDefined(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        try {
            Role.valueOf(name.trim().toUpperCase());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
