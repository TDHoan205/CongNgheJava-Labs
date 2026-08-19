package vn.edu.eaut.lab8.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private boolean loggedIn = false;
    private String currentUser;

    public String login() {
        if (username != null && !username.trim().isEmpty() && password != null && password.equals("123456")) {
            this.loggedIn = true;
            this.currentUser = username;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Đăng nhập thành công", "Chào mừng " + username + "!"));
            return "index?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lỗi đăng nhập", "Tài khoản hoặc mật khẩu không chính xác! (Gợi ý: pass = 123456)"));
            return null;
        }
    }

    public String logout() {
        this.loggedIn = false;
        this.currentUser = null;
        this.username = null;
        this.password = null;
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Thông báo", "Bạn đã đăng xuất khỏi hệ thống."));
        return "login?faces-redirect=true";
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isLoggedIn() { return loggedIn; }
    public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }

    public String getCurrentUser() { return currentUser; }
    public void setCurrentUser(String currentUser) { this.currentUser = currentUser; }
}
