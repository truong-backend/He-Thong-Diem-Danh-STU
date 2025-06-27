package vn.diemdanh.hethong.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.diemdanh.hethong.entity.*;

import java.util.*;

public class CustomUserDetails implements UserDetails {
    private final User user;
    public CustomUserDetails(User user) {
        this.user = user; // Khởi tạo đối tượng User
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Chuyển đổi chuỗi vai trò thành GrantedAuthority của Spring Security
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // Trả về mật khẩu của người dùng
    }

    @Override
    public String getUsername() {
        // Sử dụng email để xác thực, như đã thấy trong LoginRequest và UserRepository
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Tài khoản không hết hạn
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Tài khoản không bị khóa
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Thông tin xác thực không hết hạn
    }

    @Override
    public boolean isEnabled() {
        // Luôn trả về true nếu tài khoản tồn tại, hoặc kiểm tra theo điều kiện cụ thể
        return true;
    }

    // Trả về thực thể User gốc
    public User getUser() {
        return user;
    }

    // Lấy ID của người dùng
    public Long getId() {
        return user.getId();
    }

    // Lấy tên người dùng gốc (không phải email)
    public String getUserRealUsername() {
        return user.getUsername();
    }

    // Lấy vai trò của người dùng
    public String getRole() {
        return user.getRole();
    }
}