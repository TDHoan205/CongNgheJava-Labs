package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import vn.edu.eaut.lab9.model.MonHoc;

import java.util.List;

public class MonHocRepository extends BaseRepository<MonHoc, Integer> {

    public MonHocRepository() {
        super(MonHoc.class);
    }

    public MonHoc findByMaMon(String maMon) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT m FROM MonHoc m WHERE m.maMon = :maMon";
            return em.createQuery(jpql, MonHoc.class)
                    .setParameter("maMon", maMon)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<MonHoc> search(String keyword) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT m FROM MonHoc m WHERE LOWER(m.maMon) LIKE :kw OR LOWER(m.tenMon) LIKE :kw ORDER BY m.id DESC";
            return em.createQuery(jpql, MonHoc.class)
                    .setParameter("kw", "%" + keyword.toLowerCase().trim() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
