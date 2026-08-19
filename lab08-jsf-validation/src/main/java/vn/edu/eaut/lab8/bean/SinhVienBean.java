package vn.edu.eaut.lab8.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import vn.edu.eaut.lab8.model.SinhVien;
import vn.edu.eaut.lab8.repository.SinhVienRepository;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Named("sinhVienBean")
@SessionScoped
public class SinhVienBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private SinhVien sinhVien = new SinhVien();
    private final SinhVienRepository repo = new SinhVienRepository();
    private String keyword = "";
    private boolean isEdit = false;

    // List of classes for h:selectOneMenu (Bài 12)
    private final List<String> danhSachLop = Arrays.asList(
            "DCCNTT15.10.1",
            "DCCNTT15.10.2",
            "DCCNTT15.10.3",
            "DCCNTT16.10.1",
            "DCCNTT16.10.2"
    );

    public String save() {
        if (isEdit) {
            repo.update(sinhVien);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Đã cập nhật thông tin sinh viên"));
        } else {
            repo.add(sinhVien);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Đã lưu sinh viên mới"));
        }
        resetForm();
        return "sinhvien-list?faces-redirect=true";
    }

    public String prepareEdit(SinhVien sv) {
        // Clone properties for editing (Bài 9)
        this.sinhVien = new SinhVien(sv.getId(), sv.getMaSinhVien(), sv.getHoTen(), sv.getEmail(), sv.getLop());
        this.isEdit = true;
        return "sinhvien-form";
    }

    public void delete(int id) {
        repo.delete(id);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Đã xóa sinh viên"));
    }

    public void search() {
        // Keyword searching handled in getDsSinhVien() (Bài 10)
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Tìm kiếm", "Đã lọc danh sách theo từ khóa: " + keyword));
    }

    public void resetForm() {
        this.sinhVien = new SinhVien();
        this.isEdit = false;
    }

    public List<SinhVien> getDsSinhVien() {
        return repo.search(keyword);
    }

    public SinhVien getSinhVien() { return sinhVien; }
    public void setSinhVien(SinhVien sinhVien) { this.sinhVien = sinhVien; }

    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }

    public boolean isEdit() { return isEdit; }
    public void setEdit(boolean edit) { isEdit = edit; }

    public List<String> getDanhSachLop() { return danhSachLop; }
}
