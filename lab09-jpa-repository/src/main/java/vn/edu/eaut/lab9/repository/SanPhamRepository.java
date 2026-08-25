package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import vn.edu.eaut.lab9.model.SanPham;

import java.util.List;

public class SanPhamRepository extends BaseRepository<SanPham, Integer> {

    public SanPhamRepository() {
        super(SanPham.class);
    }

    @Override
    public SanPham update(SanPham entity) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            SanPham existing = em.find(SanPham.class, entity.getId());
            if (existing != null) {
                existing.setMaSp(entity.getMaSp());
                existing.setTenSp(entity.getTenSp());
                existing.setLoaiSp(entity.getLoaiSp());
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

    public SanPham findByMaSp(String maSp) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT sp FROM SanPham sp WHERE sp.maSp = :maSp";
            return em.createQuery(jpql, SanPham.class)
                    .setParameter("maSp", maSp)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<SanPham> search(String keyword) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT sp FROM SanPham sp WHERE LOWER(sp.maSp) LIKE :kw OR LOWER(sp.tenSp) LIKE :kw OR LOWER(sp.loaiSp) LIKE :kw ORDER BY sp.id DESC";
            return em.createQuery(jpql, SanPham.class)
                    .setParameter("kw", "%" + keyword.toLowerCase().trim() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
