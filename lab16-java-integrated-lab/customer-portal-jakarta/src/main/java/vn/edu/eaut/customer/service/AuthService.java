package vn.edu.eaut.customer.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.mindrot.jbcrypt.BCrypt;
import vn.edu.eaut.customer.config.JPAUtil;
import vn.edu.eaut.customer.model.User;

/**
 * Service class for authentication operations.
 */
public class AuthService {

    private static final String ROLE_CUSTOMER = "CUSTOMER";

    /**
     * Authenticate a user with username and password.
     *
     * @param username the username
     * @param password the plain-text password
     * @return the User if authentication successful and user is a CUSTOMER, null otherwise
     */
    public User authenticate(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return null;
        }

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            User user = em.createQuery(
                            "SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();

            if (user == null) {
                return null;
            }

            // Check if user is enabled
            if (!Boolean.TRUE.equals(user.getEnabled())) {
                return null;
            }

            // Check if user has CUSTOMER role
            if (!ROLE_CUSTOMER.equals(user.getRole())) {
                return null;
            }

            // Verify password using BCrypt
            if (BCrypt.checkpw(password, user.getPasswordHash())) {
                return user;
            }

            return null;
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Find a user by ID.
     *
     * @param userId the user ID
     * @return the User or null if not found
     */
    public User findById(Long userId) {
        if (userId == null) {
            return null;
        }

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            return em.find(User.class, userId);
        }
    }

    /**
     * Find a user by username.
     *
     * @param username the username
     * @return the User or null if not found
     */
    public User findByUsername(String username) {
        if (username == null || username.isBlank()) {
            return null;
        }

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            return em.createQuery(
                            "SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
