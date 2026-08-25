package vn.edu.eaut.lab9.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Utility class to manage JPA EntityManagerFactory.
 * Handles graceful fallback to test in-memory PU if primary DB connection fails.
 */
public class JPAUtil {
    private static EntityManagerFactory emf;

    static {
        initEntityManagerFactory();
    }

    private static synchronized void initEntityManagerFactory() {
        if (emf == null || !emf.isOpen()) {
            try {
                emf = Persistence.createEntityManagerFactory("lab09PU");
                System.out.println("[JPAUtil] Successfully initialized primary Persistence Unit: lab09PU");
            } catch (Throwable ex) {
                System.err.println("[JPAUtil] Warning: Failed to initialize 'lab09PU'. Falling back to 'lab09TestPU' (H2 in-memory). Cause: " + ex.getMessage());
                try {
                    emf = Persistence.createEntityManagerFactory("lab09TestPU");
                    System.out.println("[JPAUtil] Successfully initialized fallback Persistence Unit: lab09TestPU");
                } catch (Throwable ex2) {
                    System.err.println("[JPAUtil] Error: Failed to initialize any Persistence Unit: " + ex2.getMessage());
                    throw new ExceptionInInitializerError(ex2);
                }
            }
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null || !emf.isOpen()) {
            initEntityManagerFactory();
        }
        return emf;
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("[JPAUtil] EntityManagerFactory closed.");
        }
    }
}
