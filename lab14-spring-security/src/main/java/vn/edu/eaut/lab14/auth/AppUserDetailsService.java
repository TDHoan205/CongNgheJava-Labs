package vn.edu.eaut.lab14.auth;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.eaut.lab14.entity.AppUser;
import vn.edu.eaut.lab14.repository.AppUserRepository;

import java.util.Collections;

/**
 * Bài 10: AppUserDetailsService - Load user từ CSDL thay vì từ bộ nhớ.
 *
 * Spring Security dùng UserDetailsService để:
 *   1. Tìm user theo username khi đăng nhập.
 *   2. Lấy password đã mã hóa để so sánh với password user nhập.
 *   3. Lấy role (ADMIN/USER) để phân quyền.
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public AppUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Không tìm thấy tài khoản: " + username));

        // Role trong DB dạng "ADMIN" -> Spring Security yêu cầu "ROLE_ADMIN"
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + appUser.getRole());

        return User.withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .authorities(Collections.singletonList(authority))
                .disabled(!appUser.isEnabled())
                .build();
    }
}
