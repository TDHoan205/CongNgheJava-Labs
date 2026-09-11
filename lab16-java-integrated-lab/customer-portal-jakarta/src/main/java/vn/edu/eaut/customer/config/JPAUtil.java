package vn.edu.eaut.customer.config;

import jakarta.persistence.EntityManagerFactory;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton utility class for managing the EntityManagerFactory.
 */
public final class JPAUtil {

    private static final String PERSISTENCE_UNIT_NAME = "JavaIntegratedPU";
    private static final AtomicReference<EntityManagerFactory> EMF = new AtomicReference<>();

    private JPAUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Get the singleton EntityManagerFactory instance.
     * Creates it on first access.
     *
     * @return the EntityManagerFactory
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        EntityManagerFactory existingEmf = EMF.get();
        if (existingEmf != null && existingEmf.isOpen()) {
            return existingEmf;
        }

        synchronized (JPAUtil.class) {
            existingEmf = EMF.get();
            if (existingEmf != null && existingEmf.isOpen()) {
                return existingEmf;
            }

            try {
                Logger.getLogger("org.hibernate").setLevel(Level.WARNING);
                EntityManagerFactory newEmf = jakarta.persistence.Persistence
                        .createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
                EMF.set(newEmf);
                return newEmf;
            } catch (Exception e) {
                throw new RuntimeException("Failed to create EntityManagerFactory for " + PERSISTENCE_UNIT_NAME, e);
            }
        }
    }

    /**
     * Close the EntityManagerFactory if it's open.
     */
    public static void closeEntityManagerFactory() {
        EntityManagerFactory existingEmf = EMF.get();
        if (existingEmf != null && existingEmf.isOpen()) {
            existingEmf.close();
            EMF.set(null);
        }
    }
}
