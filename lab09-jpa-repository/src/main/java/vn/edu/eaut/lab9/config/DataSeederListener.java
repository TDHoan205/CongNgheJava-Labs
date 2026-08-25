package vn.edu.eaut.lab9.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import vn.edu.eaut.lab9.model.*;

import java.time.LocalDate;

@WebListener
public class DataSeederListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("[DataSeederListener] Checking database seed status...");
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            long countLop = em.createQuery("SELECT COUNT(l) FROM LopHoc l", Long.class).getSingleResult();
            if (countLop == 0) {
                System.out.println("[DataSeederListener] Database is empty. Seeding initial data...");
                tx.begin();

                // 1. Seed LopHoc
                LopHoc lop1 = new LopHoc("CNTT14-01", "Công nghệ thông tin 1", "K14");
                LopHoc lop2 = new LopHoc("CNTT14-02", "Công nghệ thông tin 2", "K14");
                LopHoc lop3 = new LopHoc("KHMT14-01", "Khoa học máy tính 1", "K14");
                em.persist(lop1);
                em.persist(lop2);
                em.persist(lop3);

                // 2. Seed SinhVien
                SinhVien sv1 = new SinhVien("SV2030022", "Trần Đức Hoàn", "hoan.td@eaut.edu.vn", LocalDate.of(2002, 5, 15), lop1);
                SinhVien sv2 = new SinhVien("SV2030001", "Nguyễn Văn Anh", "anh.nv@eaut.edu.vn", LocalDate.of(2002, 1, 10), lop1);
                SinhVien sv3 = new SinhVien("SV2030002", "Lê Thị Bình", "binh.lt@eaut.edu.vn", LocalDate.of(2002, 3, 22), lop1);
                SinhVien sv4 = new SinhVien("SV2030003", "Phạm Minh Cường", "cuong.pm@eaut.edu.vn", LocalDate.of(2002, 7, 8), lop2);
                SinhVien sv5 = new SinhVien("SV2030004", "Hoàng Thu Duyên", "duyen.ht@eaut.edu.vn", LocalDate.of(2002, 11, 30), lop2);
                em.persist(sv1);
                em.persist(sv2);
                em.persist(sv3);
                em.persist(sv4);
                em.persist(sv5);

                // 3. Seed MonHoc
                MonHoc mh1 = new MonHoc("IT3242", "Công nghệ Java", 3);
                MonHoc mh2 = new MonHoc("IT3110", "Cơ sở dữ liệu", 3);
                MonHoc mh3 = new MonHoc("IT3200", "Lập trình Web", 3);
                em.persist(mh1);
                em.persist(mh2);
                em.persist(mh3);

                // 4. Seed Diem
                em.persist(new Diem(sv1, mh1, 9.0, 9.5));
                em.persist(new Diem(sv1, mh2, 8.5, 9.0));
                em.persist(new Diem(sv2, mh1, 7.0, 8.0));
                em.persist(new Diem(sv3, mh1, 8.0, 8.5));

                // 5. Seed Roles & Users cho Lab 10 Prep
                Role roleAdmin = new Role("ROLE_ADMIN", "Quản trị viên hệ thống");
                Role roleUser = new Role("ROLE_USER", "Người dùng hệ thống");
                em.persist(roleAdmin);
                em.persist(roleUser);

                User userAdmin = new User("admin", "admin123", "admin@eaut.edu.vn", "Quản trị viên System");
                userAdmin.getRoles().add(roleAdmin);
                userAdmin.getRoles().add(roleUser);

                User userHoan = new User("hoan2030022", "user123", "hoan.td@eaut.edu.vn", "Trần Đức Hoàn");
                userHoan.getRoles().add(roleUser);

                em.persist(userAdmin);
                em.persist(userHoan);

                // 6. Seed Sach (Bài 13)
                em.persist(new Sach("S001", "Lập Trình Java Căn Bản", "Nguyễn Văn Hùng", 120000.0, 50));
                em.persist(new Sach("S002", "Jakarta EE Web Development", "Trần Đức Hoàn", 185000.0, 30));
                em.persist(new Sach("S003", "Thiết Kế CSDL Với MySQL", "Lê Anh Tuấn", 95000.0, 45));

                // 7. Seed SanPham (Bài 13)
                em.persist(new SanPham("SP001", "Laptop Dell XPS 15", "Điện tử", 35000000.0, 10));
                em.persist(new SanPham("SP002", "Bàn phím cơ Keychron K2", "Phụ kiện", 1850000.0, 25));
                em.persist(new SanPham("SP003", "Chuột Logitech MX Master 3S", "Phụ kiện", 2200000.0, 15));

                tx.commit();
                System.out.println("[DataSeederListener] Seed data completed successfully!");
            } else {
                System.out.println("[DataSeederListener] Database already contains data. Skipping seeding.");
            }
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.err.println("[DataSeederListener] Error seeding data: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JPAUtil.close();
    }
}
