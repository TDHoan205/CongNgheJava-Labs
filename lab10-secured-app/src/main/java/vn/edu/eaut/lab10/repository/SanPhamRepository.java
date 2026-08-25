package vn.edu.eaut.lab10.repository;

import jakarta.persistence.EntityManager;
import vn.edu.eaut.lab10.model.SanPham;
import java.util.List;

public class SanPhamRepository extends BaseRepository<SanPham, Integer> {

    public SanPhamRepository() {
        super(SanPham.class);
    }

    public List<SanPham> search(String keyword) {
        EntityManager em = getEntityManager();
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return em.createQuery("SELECT s FROM SanPham s ORDER BY s.id DESC", SanPham.class)
                        .getResultList();
            }
            String k = "%" + keyword.toLowerCase() + "%";
            return em.createQuery(
                    "SELECT s FROM SanPham s WHERE LOWER(s.ten) LIKE :k OR LOWER(s.ma) LIKE :k ORDER BY s.id DESC",
                    SanPham.class)
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
                    ? "SELECT COUNT(s) FROM SanPham s WHERE s.ma = :ma AND s.id <> :excludeId"
                    : "SELECT COUNT(s) FROM SanPham s WHERE s.ma = :ma";
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
