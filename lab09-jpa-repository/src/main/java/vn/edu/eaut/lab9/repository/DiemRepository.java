package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import vn.edu.eaut.lab9.model.Diem;

import java.util.List;

public class DiemRepository extends BaseRepository<Diem, Integer> {

    public DiemRepository() {
        super(Diem.class);
    }

    @Override
    public Diem update(Diem entity) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Diem existing = em.find(Diem.class, entity.getId());
            if (existing != null) {
                existing.setDiemQuaTrinh(entity.getDiemQuaTrinh());
                existing.setDiemThi(entity.getDiemThi());
                existing.tinhDiem();
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

    public List<Diem> findBySinhVienId(Integer sinhVienId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT d FROM Diem d WHERE d.sinhVien.id = :svId ORDER BY d.monHoc.tenMon ASC";
            return em.createQuery(jpql, Diem.class)
                    .setParameter("svId", sinhVienId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Diem findBySinhVienAndMonHoc(Integer sinhVienId, Integer monHocId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT d FROM Diem d WHERE d.sinhVien.id = :svId AND d.monHoc.id = :mhId";
            return em.createQuery(jpql, Diem.class)
                    .setParameter("svId", sinhVienId)
                    .setParameter("mhId", monHocId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Diem> search(String keyword) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT d FROM Diem d WHERE LOWER(d.sinhVien.hoTen) LIKE :kw " +
                          "OR LOWER(d.sinhVien.maSinhVien) LIKE :kw " +
                          "OR LOWER(d.monHoc.tenMon) LIKE :kw " +
                          "OR LOWER(d.xepLoai) LIKE :kw ORDER BY d.id DESC";
            return em.createQuery(jpql, Diem.class)
                    .setParameter("kw", "%" + keyword.toLowerCase().trim() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
