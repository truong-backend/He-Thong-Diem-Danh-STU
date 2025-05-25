package vn.diemdanh.hethong.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.dto.user_managerment.AdminDto;
import vn.diemdanh.hethong.entity.Admin;
import vn.diemdanh.hethong.repository.login.AdminRepository;
import vn.diemdanh.hethong.security.CustomAdminDetails;

import java.util.Optional;

@Service
public class AdminService implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        // Kiểm tra xem admin có tồn tại trong database không?
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);
        Admin admin = adminOptional.orElseThrow(() ->
                new UsernameNotFoundException("Không tìm thấy admin với email: " + email)
        );

        return new CustomAdminDetails(admin);
    }

    // Thêm các phương thức để lấy thông tin admin hiện tại
    public Admin getCurrentAdmin(String email) {
        return adminRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Không tìm thấy admin với email: " + email)
                );
    }

    // Phương thức mới để lấy admin theo ID (cần cho JwtAuthenticationFilter)
    public Admin getAdminById(Integer adminId) {
        return adminRepository.findById(adminId)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Không tìm thấy admin với ID: " + adminId)
                );
    }

    // Phương thức để load admin details theo ID
    public UserDetails loadAdminById(Integer adminId) {
        Admin admin = getAdminById(adminId);
        return new CustomAdminDetails(admin);
    }

    public Page<AdminDto> getAllAdmins(Pageable pageable) {
        Page<Admin> adminPage = adminRepository.findAll(pageable);
        return adminPage.map(admin -> new AdminDto(
                admin.getId(),
                admin.getUsername(),
                admin.getEmail(),
                admin.getFullName(),
                admin.getRole(),
                admin.getAvatar(),
                admin.getCreatedAt(),
                admin.getUpdatedAt()
        ));
    }
}