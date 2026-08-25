package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import vn.edu.eaut.lab9.model.Role;

public class RoleRepository extends BaseRepository<Role, Integer> {

    public RoleRepository() {
        super(Role.class);
    }

    public Role findByName(String name) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT r FROM Role r WHERE r.name = :name";
            return em.createQuery(jpql, Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
