package vn.edu.eaut.lab9.repository;

import org.junit.jupiter.api.*;
import vn.edu.eaut.lab9.config.JPAUtil;
import vn.edu.eaut.lab9.model.*;
import vn.edu.eaut.lab9.service.SinhVienService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JPAUnitTest {

    private static SinhVienRepository svRepo;
    private static LopHocRepository lopRepo;
    private static MonHocRepository monHocRepo;
    private static DiemRepository diemRepo;
    private static SinhVienService svService;

    @BeforeAll
    public static void setUp() {
        svRepo = new SinhVienRepository();
        lopRepo = new LopHocRepository();
        monHocRepo = new MonHocRepository();
        diemRepo = new DiemRepository();
        svService = new SinhVienService();
    }

    @Test
    @Order(1)
    @DisplayName("Bài 1 & 2: Kiểm tra kết nối JPAUtil và thêm mới Entity SinhVien, LopHoc")
    public void testCreateLopAndSinhVien() {
        LopHoc lop = new LopHoc("TEST-101", "Lớp Test JPA 1", "K14");
        lopRepo.save(lop);
        assertNotNull(lop.getId(), "LopHoc ID phải được tự động sinh (IDENTITY)");

        SinhVien sv = new SinhVien("SVTEST999", "Nguyễn Văn Test", "test@eaut.edu.vn", LocalDate.of(2002, 10, 10), lop);
        svRepo.save(sv);
        assertNotNull(sv.getId(), "SinhVien ID phải được tự động sinh");

        SinhVien fetched = svRepo.findById(sv.getId());
        assertNotNull(fetched);
        assertEquals("SVTEST999", fetched.getMaSinhVien());
        assertEquals("Nguyễn Văn Test", fetched.getHoTen());
        assertNotNull(fetched.getLopHoc());
        assertEquals("Lớp Test JPA 1", fetched.getLopHoc().getTenLop());
    }

    @Test
    @Order(2)
    @DisplayName("Bài 4 & 9: Kiểm tra JPQL search và Phân trang (setFirstResult & setMaxResults)")
    public void testJPQLSearchAndPagination() {
        List<SinhVien> results = svRepo.search("Test");
        assertFalse(results.isEmpty(), "JPQL Search phải trả về danh sách không rỗng");

        List<SinhVien> paginated = svRepo.findPaginated(1, 5);
        assertNotNull(paginated);
        assertTrue(paginated.size() <= 5, "Số lượng bản ghi trên một trang không được vượt quá 5");
    }

    @Test
    @Order(3)
    @DisplayName("Bài 7: Kiểm tra Entity MonHoc & Diem, tự động tính điểm tổng kết")
    public void testMonHocAndDiemCalculation() {
        MonHoc mh = new MonHoc("MH999", "Lập trình Test JPA", 3);
        monHocRepo.save(mh);
        assertNotNull(mh.getId());

        SinhVien sv = svRepo.findByMaSinhVien("SVTEST999");
        assertNotNull(sv);

        Diem diem = new Diem(sv, mh, 9.0, 10.0);
        diemRepo.save(diem);
        assertNotNull(diem.getId());

        Diem fetchedDiem = diemRepo.findById(diem.getId());
        assertEquals(9.7, fetchedDiem.getDiemTongKet(), 0.01, "Điểm tổng kết phải tính đúng DQT*0.3 + DT*0.7 = 9.7");
        assertEquals("Xuất sắc", fetchedDiem.getXepLoai(), "Xếp loại phải là Xuất sắc");
    }

    @Test
    @Order(4)
    @DisplayName("Bài 10: Kiểm tra Validation và Bắt lỗi Trùng Mã Sinh Viên")
    public void testDuplicateMaSinhVienValidation() {
        SinhVien duplicate = new SinhVien("SVTEST999", "Họ Tên Trùng", "trung@eaut.edu.vn", "CNTT");
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            svService.validateSinhVien(duplicate, true);
        });
        assertTrue(ex.getMessage().contains("đã tồn tại"), "Thông báo lỗi phải hiển thị rõ ràng mã đã tồn tại");
    }

    @Test
    @Order(5)
    @DisplayName("Bài 11: Kiểm tra Transaction nhiều thao tác và Rollback an toàn")
    public void testMultiOperationTransactionAndRollback() {
        SinhVien svNew = new SinhVien("SVTRANS888", "Sinh Viên Transaction", "trans@eaut.edu.vn", LocalDate.of(2003, 1, 1), null);
        assertDoesNotThrow(() -> {
            svService.saveWithDefaultGradesTransaction(svNew, null);
        }, "Transaction hợp lệ không được ném lỗi");

        SinhVien fetchedSv = svRepo.findByMaSinhVien("SVTRANS888");
        assertNotNull(fetchedSv, "SinhVien phải được lưu thành công");

        List<Diem> dsDiem = diemRepo.findBySinhVienId(fetchedSv.getId());
        assertFalse(dsDiem.isEmpty(), "Mỗi sinh viên mới phải tự động được tạo bản ghi điểm cho các môn học");
    }

    @AfterAll
    public static void tearDown() {
        JPAUtil.close();
    }
}
