package vn.diemdanh.hethong.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.diemdanh.hethong.entity.Admin;

import java.util.*;

public class CustomAdminDetails implements UserDetails {
    private final Admin admin;

    public CustomAdminDetails(Admin admin) {
        this.admin = admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Chuyển đổi chuỗi vai trò thành GrantedAuthority của Spring Security
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + admin.getRole()));
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        // Sử dụng email để xác thực, tương tự như User
        return admin.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Trả về thực thể Admin gốc
    public Admin getAdmin() {
        return admin;
    }

    // Lấy ID của admin
    public Integer getId() {
        return admin.getId();
    }

    // Lấy tên admin gốc (không phải email)
    public String getUserRealUsername() {
        return admin.getUsername();
    }

    // Lấy vai trò của admin
    public String getRole() {
        return admin.getRole();
    }

    // Lấy full name của admin
    public String getFullName() {
        return admin.getFullName();
    }
}