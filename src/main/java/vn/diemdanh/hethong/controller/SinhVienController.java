package vn.diemdanh.hethong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.user_managerment.*;
import vn.diemdanh.hethong.entity.*;
import vn.diemdanh.hethong.repository.user_man_and_login.*;

import jakarta.validation.Valid;
import java.time.Instant;
import java.util.Optional;
import java.util.Arrays;

@RestController
@RequestMapping("/api/sinh-vien")
public class SinhVienController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private LopRepository lopRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // CREATE - Thêm sinh viên mới
    @PostMapping
    public ResponseEntity<?> createSinhVien(@Valid @RequestBody CreateSinhVienRequest request) {
        try {
            // Kiểm tra mã sinh viên đã tồn tại chưa
            if (sinhVienRepository.findById(request.getMaSv()).isPresent()) {
                return ResponseEntity.badRequest().body("Mã sinh viên đã tồn tại");
            }

            // Kiểm tra email đã tồn tại chưa
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email đã tồn tại");
            }

            // Kiểm tra lớp có tồn tại không
            Lop lop = lopRepository.findById(request.getMaLop())
                    .orElseThrow(() -> new RuntimeException("Lớp không tồn tại"));

            // Tạo sinh viên mới
            SinhVien sinhVien = new SinhVien();
            sinhVien.setMaSv(request.getMaSv());
            sinhVien.setTenSv(request.getTenSv());
            sinhVien.setNgaySinh(request.getNgaySinh());
            sinhVien.setPhai(request.getPhai());
            sinhVien.setDiaChi(request.getDiaChi());
            sinhVien.setSdt(request.getSdt());
            sinhVien.setEmail(request.getEmail());
            sinhVien.setMaLop(lop);

            // Lưu sinh viên
            sinhVien = sinhVienRepository.save(sinhVien);

            // Nếu yêu cầu tạo tài khoản
            if (request.isCreateAccount()) {
                User user = new User();
                user.setUsername(request.getMaSv());
                user.setEmail(request.getEmail());
                user.setPassword(passwordEncoder.encode(request.getMaSv()));
                user.setRole("SINH_VIEN");
                user.setMaSv(sinhVien);
                user.setCreatedAt(Instant.now());
                user.setUpdatedAt(Instant.now());

                userRepository.save(user);
            }

            return ResponseEntity.ok("Thêm sinh viên thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm sinh viên: " + e.getMessage());
        }
    }

    /**
     * Lấy danh sách sinh viên với phân trang và sắp xếp
     * Các thuộc tính sắp xếp hợp lệ:
     * - maSv: Mã sinh viên
     * - tenSv: Tên sinh viên
     * - ngaySinh: Ngày sinh
     * - email: Email
     * - maLop: Mã lớp
     */
    @GetMapping
    public ResponseEntity<?> getAllSinhVien(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "maSv,asc") String[] sort,
            @RequestParam(required = false) String search) {
        try {
            // Validate sort field
            String sortField = sort[0];
            String sortDirection = sort.length > 1 ? sort[1] : "asc";

            if (!isValidSortField(sortField)) {
                return ResponseEntity.badRequest()
                        .body("Trường sắp xếp không hợp lệ. Các trường hợp lệ: maSv, tenSv, ngaySinh, email, maLop");
            }

            // Create pageable with sort
            Pageable pageable = PageRequest.of(
                    page,
                    size,
                    sortDirection.equalsIgnoreCase("desc") ?
                            Sort.by(sortField).descending() :
                            Sort.by(sortField).ascending()
            );

            // Get students with search
            Page<SinhVien> sinhViens;
            if (search != null && !search.trim().isEmpty()) {
                sinhViens = sinhVienRepository.findByMaSvContainingIgnoreCase(search.trim(), pageable);
            } else {
                sinhViens = sinhVienRepository.findAll(pageable);
            }

            // Map to DTOs
            Page<SinhVienDto> dtos = sinhViens.map(sinhVien -> {
                SinhVienDto dto = new SinhVienDto();
                dto.setMaSv(sinhVien.getMaSv());
                dto.setTenSv(sinhVien.getTenSv());
                dto.setNgaySinh(sinhVien.getNgaySinh());
                dto.setPhai(sinhVien.getPhai());
                dto.setDiaChi(sinhVien.getDiaChi());
                dto.setSdt(sinhVien.getSdt());
                dto.setEmail(sinhVien.getEmail());
                dto.setMaLop(sinhVien.getMaLop().getMaLop());
                dto.setTenLop(sinhVien.getMaLop().getTenLop());
                dto.setTenKhoa(sinhVien.getMaLop().getMaKhoa().getTenKhoa());
                dto.setAvatar(sinhVien.getAvatar());
                dto.setHasAccount(userRepository.findByEmail(sinhVien.getEmail()).isPresent());
                return dto;
            });

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách sinh viên: " + e.getMessage());
        }
    }

    /**
     * Kiểm tra tính hợp lệ của trường sắp xếp cho sinh viên
     */
    private boolean isValidSortField(String field) {
        return Arrays.asList("maSv", "tenSv", "ngaySinh", "email", "maLop")
                .contains(field);
    }

    // READ - Lấy thông tin một sinh viên
    @GetMapping("/{maSv}")
    public ResponseEntity<?> getSinhVien(@PathVariable String maSv) {
        try {
            SinhVien sinhVien = sinhVienRepository.findById(maSv)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));

            SinhVienDto dto = new SinhVienDto();
            dto.setMaSv(sinhVien.getMaSv());
            dto.setTenSv(sinhVien.getTenSv());
            dto.setNgaySinh(sinhVien.getNgaySinh());
            dto.setPhai(sinhVien.getPhai());
            dto.setDiaChi(sinhVien.getDiaChi());
            dto.setSdt(sinhVien.getSdt());
            dto.setEmail(sinhVien.getEmail());
            dto.setMaLop(sinhVien.getMaLop().getMaLop());
            dto.setTenLop(sinhVien.getMaLop().getTenLop());
            dto.setTenKhoa(sinhVien.getMaLop().getMaKhoa().getTenKhoa());
            dto.setAvatar(sinhVien.getAvatar());
            
            Optional<User> userOpt = userRepository.findByEmail(sinhVien.getEmail());
            dto.setHasAccount(userOpt.isPresent());

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy thông tin sinh viên: " + e.getMessage());
        }
    }

    // UPDATE - Cập nhật thông tin sinh viên
    @PutMapping("/{maSv}")
    public ResponseEntity<?> updateSinhVien(@PathVariable String maSv, @Valid @RequestBody UpdateSinhVienRequest request) {
        try {
            SinhVien sinhVien = sinhVienRepository.findById(maSv)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));

            // Kiểm tra lớp mới có tồn tại không
            Lop lop = lopRepository.findById(request.getMaLop())
                    .orElseThrow(() -> new RuntimeException("Lớp không tồn tại"));

            // Cập nhật thông tin sinh viên
            sinhVien.setTenSv(request.getTenSv());
            sinhVien.setNgaySinh(request.getNgaySinh());
            sinhVien.setPhai(request.getPhai());
            sinhVien.setDiaChi(request.getDiaChi());
            sinhVien.setSdt(request.getSdt());
            sinhVien.setEmail(request.getEmail());
            sinhVien.setMaLop(lop);

            sinhVienRepository.save(sinhVien);

            // Cập nhật email trong user nếu có tài khoản
            Optional<User> userOpt = userRepository.findByEmail(sinhVien.getEmail());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setEmail(request.getEmail());
                user.setUpdatedAt(Instant.now());
                userRepository.save(user);
            }

            return ResponseEntity.ok("Cập nhật sinh viên thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật sinh viên: " + e.getMessage());
        }
    }

    // DELETE - Xóa sinh viên
    @DeleteMapping("/{maSv}")
    public ResponseEntity<?> deleteSinhVien(@PathVariable String maSv) {
        try {
            SinhVien sinhVien = sinhVienRepository.findById(maSv)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));

            // Xóa tài khoản user nếu có
            Optional<User> userOpt = userRepository.findByEmail(sinhVien.getEmail());
            userOpt.ifPresent(user -> userRepository.delete(user));

            // Xóa sinh viên
            sinhVienRepository.delete(sinhVien);

            return ResponseEntity.ok("Xóa sinh viên thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi xóa sinh viên: " + e.getMessage());
        }
    }

} 