package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import vn.edu.eaut.lab9.model.LopHoc;

import java.util.List;

public class LopHocRepository extends BaseRepository<LopHoc, Integer> {

    public LopHocRepository() {
        super(LopHoc.class);
    }

    @Override
    public List<LopHoc> findAll() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT DISTINCT l FROM LopHoc l LEFT JOIN FETCH l.dsSinhVien ORDER BY l.id DESC";
            return em.createQuery(jpql, LopHoc.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public LopHoc update(LopHoc entity) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            LopHoc existing = em.find(LopHoc.class, entity.getId());
            if (existing != null) {
                existing.setMaLop(entity.getMaLop());
                existing.setTenLop(entity.getTenLop());
                existing.setKhoaHoc(entity.getKhoaHoc());
            }
            tx.commit();
            return existing;
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public LopHoc findByMaLop(String maLop) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT DISTINCT l FROM LopHoc l LEFT JOIN FETCH l.dsSinhVien WHERE l.maLop = :maLop";
            return em.createQuery(jpql, LopHoc.class)
                    .setParameter("maLop", maLop)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<LopHoc> search(String keyword) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT DISTINCT l FROM LopHoc l LEFT JOIN FETCH l.dsSinhVien WHERE LOWER(l.maLop) LIKE :kw OR LOWER(l.tenLop) LIKE :kw OR LOWER(l.khoaHoc) LIKE :kw ORDER BY l.id DESC";
            return em.createQuery(jpql, LopHoc.class)
                    .setParameter("kw", "%" + keyword.toLowerCase().trim() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
