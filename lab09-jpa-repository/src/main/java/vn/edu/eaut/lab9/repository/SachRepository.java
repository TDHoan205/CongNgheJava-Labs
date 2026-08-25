package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import vn.edu.eaut.lab9.model.Sach;

import java.util.List;

public class SachRepository extends BaseRepository<Sach, Integer> {

    public SachRepository() {
        super(Sach.class);
    }

    @Override
    public Sach update(Sach entity) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Sach existing = em.find(Sach.class, entity.getId());
            if (existing != null) {
                existing.setMaSach(entity.getMaSach());
                existing.setTenSach(entity.getTenSach());
                existing.setTacGia(entity.getTacGia());
                existing.setGia(entity.getGia());
                existing.setSoLuong(entity.getSoLuong());
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

    public Sach findByMaSach(String maSach) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT s FROM Sach s WHERE s.maSach = :maSach";
            return em.createQuery(jpql, Sach.class)
                    .setParameter("maSach", maSach)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Sach> search(String keyword) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT s FROM Sach s WHERE LOWER(s.maSach) LIKE :kw OR LOWER(s.tenSach) LIKE :kw OR LOWER(s.tacGia) LIKE :kw ORDER BY s.id DESC";
            return em.createQuery(jpql, Sach.class)
                    .setParameter("kw", "%" + keyword.toLowerCase().trim() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
