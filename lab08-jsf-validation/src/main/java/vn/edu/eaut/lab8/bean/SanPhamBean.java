package vn.edu.eaut.lab8.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import vn.edu.eaut.lab8.model.SanPham;
import vn.edu.eaut.lab8.repository.SanPhamRepository;

import java.io.Serializable;
import java.util.List;

@Named("sanPhamBean")
@SessionScoped
public class SanPhamBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private SanPham sanPham = new SanPham();
    private final SanPhamRepository repo = new SanPhamRepository();
    private boolean isEdit = false;

    public String save() {
        if (isEdit) {
            repo.update(sanPham);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Đã cập nhật thông tin sản phẩm"));
        } else {
            repo.add(sanPham);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Đã thêm sản phẩm mới"));
        }
        resetForm();
        return "product-list?faces-redirect=true";
    }

    public String prepareEdit(SanPham sp) {
        this.sanPham = new SanPham(sp.getId(), sp.getMaSp(), sp.getTenSp(), sp.getGia(), sp.getSoLuong());
        this.isEdit = true;
        return "product-form";
    }

    public void delete(int id) {
        repo.delete(id);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Đã xóa sản phẩm"));
    }

    public void resetForm() {
        this.sanPham = new SanPham();
        this.isEdit = false;
    }

    public List<SanPham> getDsSanPham() {
        return repo.findAll();
    }

    public SanPham getSanPham() { return sanPham; }
    public void setSanPham(SanPham sanPham) { this.sanPham = sanPham; }

    public boolean isEdit() { return isEdit; }
    public void setEdit(boolean edit) { isEdit = edit; }
}
