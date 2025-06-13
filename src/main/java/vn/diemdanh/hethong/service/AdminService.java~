package vn.diemdanh.hethong.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.diemdanh.hethong.dto.admin.AdminDto;
import vn.diemdanh.hethong.dto.admin.CreateAdminRequest;
import vn.diemdanh.hethong.dto.admin.UpdateAdminRequest;
import vn.diemdanh.hethong.dto.sinhvien.CreateSinhVienRequest;
import vn.diemdanh.hethong.dto.sinhvien.SinhVienExcelDto;
import vn.diemdanh.hethong.entity.Admin;
import vn.diemdanh.hethong.exception.forgot_password.ResourceNotFoundException;
import vn.diemdanh.hethong.repository.user_man_and_login.AdminRepository;
import vn.diemdanh.hethong.repository.user_man_and_login.LopRepository;
import vn.diemdanh.hethong.repository.user_man_and_login.SinhVienRepository;
import vn.diemdanh.hethong.security.CustomAdminDetails;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    LopRepository lopRepository;
    @Autowired
    SinhVienRepository sinhVienRepository;

    @Autowired
    private SinhVienService sinhVienService;

    @Transactional
    public void saveImportData(List<SinhVienExcelDto> sinhVienExcelDtos) {
        for (SinhVienExcelDto dto : sinhVienExcelDtos) {
            try {
                CreateSinhVienRequest request = convertToCreateRequest(dto);
                sinhVienService.createSinhVien(request);
            } catch (Exception e) {
                // Log lỗi nhưng không rollback toàn bộ
                System.err.println("Lỗi khi thêm sinh viên mã: " + dto.getMaSv() + " -> " + e.getMessage());
            }
        }
    }


    public CreateSinhVienRequest convertToCreateRequest(SinhVienExcelDto excelDto) {
        CreateSinhVienRequest request = new CreateSinhVienRequest();
        request.setMaSv(excelDto.getMaSv());
        request.setTenSv(excelDto.getTenSv());
        request.setNgaySinh(excelDto.getNgaySinh());
        request.setPhai(excelDto.getPhai());
        request.setDiaChi(excelDto.getDiaChi());
        request.setSdt(excelDto.getSdt());
        request.setEmail(excelDto.getEmail());
        request.setMaLop(excelDto.getMaLop());
        request.setCreateAccount(true); // hoặc false, tùy nhu cầu
        return request;
    }
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
    public Optional<AdminDto> getAdminDtoById(Integer id) {
        return adminRepository.findById(id)
                .map(admin -> new AdminDto(
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

    public AdminDto createAdmin(@Valid CreateAdminRequest request) {
        // Check if email already exists
        if (adminRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã tồn tại trong hệ thống");
        }

        // Check if username already exists
        if (adminRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username đã tồn tại trong hệ thống");
        }

        Admin admin = new Admin();
;
        admin.setUsername(request.getUsername());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setEmail(request.getEmail());
        admin.setFullName(request.getFullName());
        admin.setRole(request.getRole());
        admin.setCreatedAt(Instant.now());
        admin.setUpdatedAt(Instant.now());

        Admin savedAdmin = adminRepository.save(admin);

        return new AdminDto(
                savedAdmin.getId(),
                savedAdmin.getUsername(),
                savedAdmin.getEmail(),
                savedAdmin.getFullName(),
                savedAdmin.getRole(),
                savedAdmin.getAvatar(),
                savedAdmin.getCreatedAt(),
                savedAdmin.getUpdatedAt()
        );
    }

    public AdminDto updateAdmin(Integer id, @Valid UpdateAdminRequest request) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));

        admin.setUsername(request.getUsername());
        admin.setRole(request.getRole());
        admin.setUpdatedAt(Instant.now());

        if (request.getEmail() != null) {
            admin.setEmail(request.getEmail());
        }
        if (request.getFullName() != null) {
            admin.setFullName(request.getFullName());
        }
        if (request.getPassword() != null) {
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        Admin updatedAdmin = adminRepository.save(admin);
        return updatedAdmin != null ? new AdminDto(
                updatedAdmin.getId(),
                updatedAdmin.getUsername(),
                updatedAdmin.getEmail(),
                updatedAdmin.getFullName(),
                updatedAdmin.getRole(),
                updatedAdmin.getAvatar(),
                updatedAdmin.getCreatedAt(),
                updatedAdmin.getUpdatedAt()
        ) : null;
    }

    public void deleteAdmin(Integer id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));

        adminRepository.delete(admin);
    }
}