package vn.edu.eaut.lab10.repository;

import jakarta.persistence.EntityManager;
import vn.edu.eaut.lab10.model.Sach;
import java.util.List;

public class SachRepository extends BaseRepository<Sach, Integer> {

    public SachRepository() {
        super(Sach.class);
    }

    public List<Sach> search(String keyword) {
        EntityManager em = getEntityManager();
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return em.createQuery("SELECT s FROM Sach s ORDER BY s.id DESC", Sach.class)
                        .getResultList();
            }
            String k = "%" + keyword.toLowerCase() + "%";
            return em.createQuery(
                    "SELECT s FROM Sach s WHERE LOWER(s.tenSach) LIKE :k OR LOWER(s.tacGia) LIKE :k OR LOWER(s.maSach) LIKE :k ORDER BY s.id DESC",
                    Sach.class)
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
                    ? "SELECT COUNT(s) FROM Sach s WHERE s.maSach = :ma AND s.id <> :excludeId"
                    : "SELECT COUNT(s) FROM Sach s WHERE s.maSach = :ma";
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
