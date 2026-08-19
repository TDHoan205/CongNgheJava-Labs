package vn.edu.eaut.lab8.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import vn.edu.eaut.lab8.model.Sach;
import vn.edu.eaut.lab8.repository.SachRepository;

import java.io.Serializable;
import java.util.List;

@Named("sachBean")
@SessionScoped
public class SachBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Sach sach = new Sach();
    private final SachRepository repo = new SachRepository();
    private boolean isEdit = false;

    public String save() {
        if (isEdit) {
            repo.update(sach);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Đã cập nhật thông tin sách"));
        } else {
            repo.add(sach);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Đã thêm sách mới thành công"));
        }
        resetForm();
        return "sach-list?faces-redirect=true";
    }

    public String prepareEdit(Sach s) {
        this.sach = new Sach(s.getId(), s.getMaSach(), s.getTenSach(), s.getTacGia(), s.getNamXuatBan(), s.getGia());
        this.isEdit = true;
        return "sach-form";
    }

    public void delete(int id) {
        repo.delete(id);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Đã xóa sách"));
    }

    public void resetForm() {
        this.sach = new Sach();
        this.isEdit = false;
    }

    public List<Sach> getDsSach() {
        return repo.findAll();
    }

    public Sach getSach() { return sach; }
    public void setSach(Sach sach) { this.sach = sach; }

    public boolean isEdit() { return isEdit; }
    public void setEdit(boolean edit) { isEdit = edit; }
}
