package vn.diemdanh.hethong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import vn.diemdanh.hethong.dto.diemdanh.DiemDanhQRSinhVienRequest;
import vn.diemdanh.hethong.dto.giaovien.*;
import vn.diemdanh.hethong.entity.*;
import vn.diemdanh.hethong.repository.GiaoVienRepository;
import vn.diemdanh.hethong.repository.UserRepository;

import jakarta.validation.Valid;
import vn.diemdanh.hethong.service.DiemDanhService;
import vn.diemdanh.hethong.service.GiaoVienService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/giao-vien")
public class GiaoVienController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GiaoVienRepository giaoVienRepository;

    @Autowired GiaoVienService GiaoVienService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    DiemDanhService diemDanhService;
    @Autowired
    private GiaoVienService giaoVienService;

    @PostMapping("/diemdanhnguoc")
    public ResponseEntity<?> diemdanhQuetMaQRSinhVien(@Valid @RequestBody DiemDanhQRSinhVienRequest request) {
        try {
            int result = diemDanhService.diemDanhMaQRSinhVien(request);
            if (result > 0) {
                return ResponseEntity.ok("Điểm danh thành công");
            } else {
                return ResponseEntity.ok("Lỗi điểm danh");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Điểm danh thất bại: " + e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfileTeacher(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        GiaoVienDTO_Profile giaoVien = GiaoVienService.getGiaoVienByEmail(email);
        return ResponseEntity.ok(giaoVien);
    }
    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfileTeacher(@AuthenticationPrincipal UserDetails userDetails,@RequestBody @Valid GiaoVienDTO_Profile giaoVienDTO_Profile) {
        String email = userDetails.getUsername();
        return ResponseEntity.ok(GiaoVienService.updateProfileGiaoVien(email,giaoVienDTO_Profile));
    }

    // CREATE - Thêm giáo viên mới
    @PostMapping
    public ResponseEntity<?> createGiaoVien(@Valid @RequestBody CreateGiaoVienRequest request) {
        try {
            // Kiểm tra mã giáo viên đã tồn tại chưa
            if (giaoVienRepository.findById(request.getMaGv()).isPresent()) {
                return ResponseEntity.badRequest().body("Mã giáo viên đã tồn tại");
            }

            // Kiểm tra email đã tồn tại chưa
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email đã tồn tại");
            }

            // Tạo giáo viên mới
            GiaoVien giaoVien = new GiaoVien();
            giaoVien.setMaGv(request.getMaGv());
            giaoVien.setTenGv(request.getTenGv());
            giaoVien.setNgaySinh(request.getNgaySinh());
            giaoVien.setPhai(request.getPhai());
            giaoVien.setDiaChi(request.getDiaChi());
            giaoVien.setSdt(request.getSdt());
            giaoVien.setEmail(request.getEmail());

            // Lưu giáo viên
            giaoVien = giaoVienRepository.save(giaoVien);

            // Nếu yêu cầu tạo tài khoản
            if (request.isCreateAccount()) {
                User user = new User();
                user.setUsername(request.getMaGv());
                user.setEmail(request.getEmail());
                user.setPassword(passwordEncoder.encode(request.getMaGv()));
                user.setRole("GIAO_VIEN");
                user.setMaGv(giaoVien);
                user.setCreatedAt(Instant.now());
                user.setUpdatedAt(Instant.now());

                userRepository.save(user);
            }

            return ResponseEntity.ok("Thêm giáo viên thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm giáo viên: " + e.getMessage());
        }
    }

    /**
     * Lấy danh sách giáo viên với phân trang và sắp xếp
     * Các thuộc tính sắp xếp hợp lệ:
     * - maGv: Mã giáo viên
     * - tenGv: Tên giáo viên
     * - ngaySinh: Ngày sinh
     * - email: Email
     */
   @GetMapping
    public ResponseEntity<?> getAllGiaoVien(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "maGv,asc") String[] sort,
            @RequestParam(required = false) String search) {
        try {
            // Validate sort field
            String sortField = sort[0];
            String sortDirection = sort.length > 1 ? sort[1] : "asc";

            if (!isValidSortField(sortField)) {
                return ResponseEntity.badRequest()
                    .body("Trường sắp xếp không hợp lệ. Các trường hợp lệ: maGv, tenGv, ngaySinh, email");
            }

            // Create pageable with sort
            Pageable pageable = PageRequest.of(
                page,
                size,
                sortDirection.equalsIgnoreCase("desc") ?
                    Sort.by(sortField).descending() :
                    Sort.by(sortField).ascending()
            );

            // Get teachers with search
            Page<GiaoVien> giaoViens;
            if (search != null && !search.trim().isEmpty()) {
                giaoViens = giaoVienRepository.findByMaGvContainingIgnoreCase(search.trim(), pageable);
            } else {
                giaoViens = giaoVienRepository.findAll(pageable);
            }

            // Map to DTOs
            Page<GiaoVienDto> dtos = giaoViens.map(giaoVien -> {
                GiaoVienDto dto = new GiaoVienDto();
                dto.setMaGv(giaoVien.getMaGv());
                dto.setTenGv(giaoVien.getTenGv());
                dto.setNgaySinh(giaoVien.getNgaySinh());
                dto.setPhai(giaoVien.getPhai());
                dto.setDiaChi(giaoVien.getDiaChi());
                dto.setSdt(giaoVien.getSdt());
                dto.setEmail(giaoVien.getEmail());
                dto.setAvatar(giaoVien.getAvatar());
                dto.setHasAccount(userRepository.findByEmail(giaoVien.getEmail()).isPresent());
                return dto;
            });

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách giáo viên: " + e.getMessage());
        }
    }

    /**
     * Kiểm tra tính hợp lệ của trường sắp xếp cho giáo viên
     */
    private boolean isValidSortField(String field) {
        return Arrays.asList("maGv", "tenGv", "ngaySinh", "email")
                .contains(field);
    }

    // READ - Lấy thông tin một giáo viên
    @GetMapping("/{maGv}")
    public ResponseEntity<?> getGiaoVien(@PathVariable String maGv) {
        try {
            GiaoVien giaoVien = giaoVienRepository.findById(maGv)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy giáo viên"));

            GiaoVienDto dto = new GiaoVienDto();
            dto.setMaGv(giaoVien.getMaGv());
            dto.setTenGv(giaoVien.getTenGv());
            dto.setNgaySinh(giaoVien.getNgaySinh());
            dto.setPhai(giaoVien.getPhai());
            dto.setDiaChi(giaoVien.getDiaChi());
            dto.setSdt(giaoVien.getSdt());
            dto.setEmail(giaoVien.getEmail());
            dto.setAvatar(giaoVien.getAvatar());

            Optional<User> userOpt = userRepository.findByEmail(giaoVien.getEmail());
            dto.setHasAccount(userOpt.isPresent());

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy thông tin giáo viên: " + e.getMessage());
        }
    }

    // UPDATE - Cập nhật thông tin giáo viên
    @PutMapping("/{maGv}")
    public ResponseEntity<?> updateGiaoVien(@PathVariable String maGv, @Valid @RequestBody UpdateGiaoVienRequest request) {
        try {
            GiaoVien giaoVien = giaoVienRepository.findById(maGv)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy giáo viên"));

            // Cập nhật thông tin giáo viên
            giaoVien.setTenGv(request.getTenGv());
            giaoVien.setNgaySinh(request.getNgaySinh());
            giaoVien.setPhai(request.getPhai());
            giaoVien.setDiaChi(request.getDiaChi());
            giaoVien.setSdt(request.getSdt());
            giaoVien.setEmail(request.getEmail());

            giaoVienRepository.save(giaoVien);

            // Cập nhật email trong user nếu có tài khoản
            Optional<User> userOpt = userRepository.findByEmail(giaoVien.getEmail());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setEmail(request.getEmail());
                user.setUpdatedAt(Instant.now());
                userRepository.save(user);
            }

            return ResponseEntity.ok("Cập nhật giáo viên thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật giáo viên: " + e.getMessage());
        }
    }

    // DELETE - Xóa giáo viên
    @DeleteMapping("/{maGv}")
    public ResponseEntity<?> deleteGiaoVien(@PathVariable String maGv) {
        try {
            GiaoVien giaoVien = giaoVienRepository.findById(maGv)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy giáo viên"));

            // Xóa tài khoản user nếu có
            Optional<User> userOpt = userRepository.findByEmail(giaoVien.getEmail());
            userOpt.ifPresent(user -> userRepository.delete(user));

            // Xóa giáo viên
            giaoVienRepository.delete(giaoVien);

            return ResponseEntity.ok("Xóa giáo viên thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi xóa giáo viên: " + e.getMessage());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllGiaoVienWithoutPaging() {
        try {
            List<GiaoVien> giaoViens = giaoVienRepository.findAll(Sort.by("maGv").ascending());

            List<GiaoVienDto> dtos = giaoViens.stream()
                .map(giaoVien -> {
                    GiaoVienDto dto = new GiaoVienDto();
                    dto.setMaGv(giaoVien.getMaGv());
                    dto.setTenGv(giaoVien.getTenGv());
                    dto.setNgaySinh(giaoVien.getNgaySinh());
                    dto.setPhai(giaoVien.getPhai());
                    dto.setDiaChi(giaoVien.getDiaChi());
                    dto.setSdt(giaoVien.getSdt());
                    dto.setEmail(giaoVien.getEmail());
                    dto.setAvatar(giaoVien.getAvatar());
                    dto.setHasAccount(userRepository.findByEmail(giaoVien.getEmail()).isPresent());
                    return dto;
                })
                .collect(Collectors.toList());

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách giáo viên: " + e.getMessage());
        }
    }
    @GetMapping("/giao-vien")
    public ResponseEntity<List<GiaoVienInfo>> getGiaoVienByMonHoc(
            @RequestParam Integer hocKy,
            @RequestParam Integer namHoc,
            @RequestParam String maMh) {
        try {
            List<GiaoVienInfo> giaoVienList = giaoVienService.getGiaoVienByHocKyNamAndMonHoc(hocKy, namHoc, maMh);
            return ResponseEntity.ok(giaoVienList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 