package vn.edu.eaut.lab10.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import vn.edu.eaut.lab10.model.User;

import java.util.List;

/**
 * Repository for User entity.
 * Handles all database access for users.
 * Minimum implementation for Task 2.4: findByEmail.
 */
public class UserRepository extends BaseRepository<User, Integer> {

    public UserRepository() {
        super(User.class);
    }

    /**
     * Finds a user by email address.
     *
     * @param email the email to search for
     * @return the User with the given email, or null if not found
     */
    public User findByEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.email = :email";
            return em.createQuery(jpql, User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Searches users by email (LIKE).
     *
     * @param keyword the email keyword to search
     * @return list of matching users
     */
    public List<User> searchByEmail(String keyword) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE LOWER(u.email) LIKE LOWER(:keyword) ORDER BY u.id DESC";
            return em.createQuery(jpql, User.class)
                    .setParameter("keyword", "%" + keyword + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Checks if email already exists.
     *
     * @param email the email to check
     * @return true if exists
     */
    public boolean existsByEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(u) FROM User u WHERE u.email = :email";
            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    /**
     * Checks if email exists, excluding a specific user ID (for update).
     *
     * @param email the email to check
     * @param excludeId the user ID to exclude
     * @return true if exists
     */
    public boolean existsByEmailExcludingId(String email, Integer excludeId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(u) FROM User u WHERE u.email = :email AND u.id <> :excludeId";
            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("email", email)
                    .setParameter("excludeId", excludeId)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
}
