package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import vn.edu.eaut.lab9.model.Role;
import vn.edu.eaut.lab9.model.User;

public class UserRepository extends BaseRepository<User, Integer> {

    public UserRepository() {
        super(User.class);
    }

    public User findByUsername(String username) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.username = :uname";
            return em.createQuery(jpql, User.class)
                    .setParameter("uname", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
