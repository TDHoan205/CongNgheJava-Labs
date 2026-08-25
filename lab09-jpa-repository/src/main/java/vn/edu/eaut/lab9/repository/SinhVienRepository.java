package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import vn.edu.eaut.lab9.model.SinhVien;

import java.util.List;

public class SinhVienRepository extends BaseRepository<SinhVien, Integer> {

    public SinhVienRepository() {
        super(SinhVien.class);
    }

    public SinhVien findByMaSinhVien(String maSinhVien) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT s FROM SinhVien s WHERE s.maSinhVien = :maSV";
            return em.createQuery(jpql, SinhVien.class)
                    .setParameter("maSV", maSinhVien)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<SinhVien> findByLopId(Integer lopId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT s FROM SinhVien s WHERE s.lopHoc.id = :lopId ORDER BY s.maSinhVien ASC";
            return em.createQuery(jpql, SinhVien.class)
                    .setParameter("lopId", lopId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<SinhVien> search(String keyword) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT s FROM SinhVien s LEFT JOIN s.lopHoc l " +
                          "WHERE LOWER(s.hoTen) LIKE :kw OR LOWER(s.maSinhVien) LIKE :kw " +
                          "OR LOWER(s.email) LIKE :kw OR LOWER(l.tenLop) LIKE :kw " +
                          "ORDER BY s.id DESC";
            return em.createQuery(jpql, SinhVien.class)
                    .setParameter("kw", "%" + keyword.toLowerCase().trim() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<SinhVien> searchPaginated(String keyword, int page, int pageSize) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT s FROM SinhVien s LEFT JOIN s.lopHoc l " +
                          "WHERE LOWER(s.hoTen) LIKE :kw OR LOWER(s.maSinhVien) LIKE :kw " +
                          "OR LOWER(s.email) LIKE :kw OR LOWER(l.tenLop) LIKE :kw " +
                          "ORDER BY s.id DESC";
            TypedQuery<SinhVien> query = em.createQuery(jpql, SinhVien.class);
            query.setParameter("kw", "%" + keyword.toLowerCase().trim() + "%");
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public long countSearch(String keyword) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(s) FROM SinhVien s LEFT JOIN s.lopHoc l " +
                          "WHERE LOWER(s.hoTen) LIKE :kw OR LOWER(s.maSinhVien) LIKE :kw " +
                          "OR LOWER(s.email) LIKE :kw OR LOWER(l.tenLop) LIKE :kw";
            return em.createQuery(jpql, Long.class)
                    .setParameter("kw", "%" + keyword.toLowerCase().trim() + "%")
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}
