package vn.edu.eaut.lab9.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.edu.eaut.lab9.config.JPAUtil;
import vn.edu.eaut.lab9.model.Diem;
import vn.edu.eaut.lab9.model.LopHoc;
import vn.edu.eaut.lab9.model.MonHoc;
import vn.edu.eaut.lab9.model.SinhVien;
import vn.edu.eaut.lab9.repository.SinhVienRepository;

import java.util.List;

public class SinhVienService {

    private final SinhVienRepository svRepo = new SinhVienRepository();

    public void validateSinhVien(SinhVien sv, boolean isNew) throws IllegalArgumentException {
        if (sv.getMaSinhVien() == null || sv.getMaSinhVien().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã sinh viên không được để trống!");
        }
        if (sv.getHoTen() == null || sv.getHoTen().trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên sinh viên không được để trống!");
        }
        if (sv.getEmail() != null && !sv.getEmail().trim().isEmpty()) {
            if (!sv.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                throw new IllegalArgumentException("Định dạng Email không hợp lệ!");
            }
        }
        if (isNew) {
            SinhVien existing = svRepo.findByMaSinhVien(sv.getMaSinhVien().trim());
            if (existing != null) {
                throw new IllegalArgumentException("Mã sinh viên '" + sv.getMaSinhVien() + "' đã tồn tại trong CSDL!");
            }
        }
    }

    /**
     * Bài 11: Transaction nhiều thao tác.
     * Thêm sinh viên mới và khởi tạo điểm mặc định cho tất cả môn học trong CSDL.
     */
    public void saveWithDefaultGradesTransaction(SinhVien sv, Integer lopId) {
        validateSinhVien(sv, true);

        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            if (lopId != null) {
                LopHoc lop = em.find(LopHoc.class, lopId);
                sv.setLopHoc(lop);
            }

            em.persist(sv);

            List<MonHoc> dsMonHoc = em.createQuery("SELECT m FROM MonHoc m", MonHoc.class).getResultList();
            for (MonHoc mh : dsMonHoc) {
                Diem diem = new Diem(sv, mh, 0.0, 0.0);
                em.persist(diem);
            }

            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Lỗi Transaction: Không thể thêm sinh viên. Đã rollback! Chi tiết: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }

    /**
     * Cập nhật sinh viên bằng Managed Entity & Dirty Checking chuẩn JPA
     */
    public void updateSinhVien(SinhVien sv, Integer lopId) {
        validateSinhVien(sv, false);

        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            SinhVien existing = em.find(SinhVien.class, sv.getId());
            if (existing != null) {
                existing.setHoTen(sv.getHoTen());
                existing.setEmail(sv.getEmail());
                existing.setNgaySinh(sv.getNgaySinh());
                if (lopId != null) {
                    LopHoc lop = em.find(LopHoc.class, lopId);
                    existing.setLopHoc(lop);
                } else {
                    existing.setLopHoc(null);
                }
            }
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }
}
