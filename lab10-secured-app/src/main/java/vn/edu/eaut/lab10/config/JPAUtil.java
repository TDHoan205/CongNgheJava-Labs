package vn.edu.eaut.lab10.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Utility class to manage JPA EntityManagerFactory for Lab 10.
 * Owns the application EntityManagerFactory.
 */
public class JPAUtil {
    private static EntityManagerFactory emf;

    static {
        initEntityManagerFactory();
    }

    private static synchronized void initEntityManagerFactory() {
        if (emf == null || !emf.isOpen()) {
            try {
                emf = Persistence.createEntityManagerFactory("lab10PU");
                System.out.println("[JPAUtil] Primary PU 'lab10PU' initialized.");
            } catch (Throwable ex) {
                System.err.println("[JPAUtil] Failed to initialize primary PU 'lab10PU'.");
                ex.printStackTrace(System.err);
                throw new IllegalStateException("Cannot initialize persistence unit lab10PU", ex);
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
        }
    }
}
