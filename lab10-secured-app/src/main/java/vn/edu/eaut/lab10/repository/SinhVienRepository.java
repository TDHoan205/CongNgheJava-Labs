package vn.edu.eaut.lab10.repository;

import jakarta.persistence.EntityManager;
import vn.edu.eaut.lab10.model.SinhVien;
import java.util.List;

public class SinhVienRepository extends BaseRepository<SinhVien, Integer> {

    public SinhVienRepository() {
        super(SinhVien.class);
    }

    public List<SinhVien> search(String keyword) {
        EntityManager em = getEntityManager();
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return em.createQuery("SELECT s FROM SinhVien s ORDER BY s.id DESC", SinhVien.class)
                        .getResultList();
            }
            String k = "%" + keyword.toLowerCase() + "%";
            return em.createQuery(
                    "SELECT s FROM SinhVien s WHERE LOWER(s.hoTen) LIKE :k OR LOWER(s.lop) LIKE :k OR LOWER(s.maSinhVien) LIKE :k ORDER BY s.id DESC",
                    SinhVien.class)
                    .setParameter("k", k)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public boolean existsByMa(String ma, Integer excludeId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = excludeId != null
                    ? "SELECT COUNT(s) FROM SinhVien s WHERE s.maSinhVien = :ma AND s.id <> :excludeId"
                    : "SELECT COUNT(s) FROM SinhVien s WHERE s.maSinhVien = :ma";
            var query = em.createQuery(jpql, Long.class).setParameter("ma", ma);
            if (excludeId != null) {
                query.setParameter("excludeId", excludeId);
            }
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }
}
