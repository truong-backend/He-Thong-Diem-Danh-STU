package vn.diemdanh.hethong.controller.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.sinhvien.*;
import vn.diemdanh.hethong.entity.*;
import vn.diemdanh.hethong.repository.LopRepository;
import vn.diemdanh.hethong.repository.SinhVienRepository;
import vn.diemdanh.hethong.repository.UserRepository;
import vn.diemdanh.hethong.service.SinhVienService;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sinh-vien")
public class SinhVienController {

    @Autowired private UserRepository userRepository;
    @Autowired private SinhVienRepository sinhVienRepository;
    @Autowired private LopRepository lopRepository;
    @Autowired private SinhVienService sinhVienService;

    // Lấy QR sinh viên hiện tại
    @GetMapping("/qr-sinhvien")
    public ResponseEntity<?> getQRSinhVienInfo(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            String email = userDetails.getUsername();
            QRSinhVienInfoDTO qrSV = sinhVienService.getQRSinhVien(email);
            return ResponseEntity.ok(qrSV);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Lỗi khi lấy QR sinh viên: " + e.getMessage());
        }
    }

    // Lấy profile sinh viên hiện tại
    @GetMapping("/profile")
    public ResponseEntity<?> getProfileSinhVien(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            String email = userDetails.getUsername();
            SinhVienDTOProfile profile = sinhVienService.getSinhVienProfile(email);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Lỗi khi lấy thông tin sinh viên: " + e.getMessage());
        }
    }

    // Cập nhật profile sinh viên hiện tại
    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfileSinhVien(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody SinhVienDTOProfile profileRequest) {
        String email = userDetails.getUsername();
        SinhVienDTOProfile updated = sinhVienService.updateProfileSinhVien(email, profileRequest);
        return ResponseEntity.ok(updated);
    }

    // Lấy danh sách sinh viên không phân trang
    @GetMapping("/all")
    public ResponseEntity<?> getAllSinhVienNoPagination() {
        try {
            List<SinhVien> sinhViens = sinhVienService.findAll();
            List<SinhVienDto> dtos = sinhViens.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Lỗi khi lấy danh sách sinh viên: " + e.getMessage());
        }
    }

    // Hàm chuyển đổi entity sang DTO
    private SinhVienDto convertToDto(SinhVien sv) {
        SinhVienDto dto = new SinhVienDto();
        dto.setMaSv(sv.getMaSv());
        dto.setTenSv(sv.getTenSv());
        dto.setNgaySinh(sv.getNgaySinh());
        dto.setPhai(sv.getPhai());
        dto.setDiaChi(sv.getDiaChi());
        dto.setSdt(sv.getSdt());
        dto.setEmail(sv.getEmail());
        dto.setMaLop(sv.getMaLop().getMaLop());
        dto.setTenLop(sv.getMaLop().getTenLop());
        dto.setTenKhoa(sv.getMaLop().getMaKhoa().getTenKhoa());
        dto.setAvatar(sv.getAvatar());
        dto.setHasAccount(userRepository.findByEmail(sv.getEmail()).isPresent());
        return dto;
    }

    // Thêm sinh viên mới
    @PostMapping
    public ResponseEntity<?> createSinhVien(@Valid @RequestBody CreateSinhVienRequest request) {
        try {
            sinhVienService.createSinhVien(request);
            return ResponseEntity.ok("Thêm sinh viên thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Lỗi khi thêm sinh viên: " + e.getMessage());
        }
    }

    /**
     * Lấy danh sách sinh viên có phân trang, sắp xếp và tìm kiếm
     * Các trường hợp lệ để sắp xếp: maSv, tenSv, ngaySinh, email, maLop
     */
    @GetMapping
    public ResponseEntity<?> getAllSinhVien(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "maSv,asc") String[] sort,
            @RequestParam(required = false) String search) {
        try {
            String sortField = sort[0];
            String sortDir = sort.length > 1 ? sort[1] : "asc";

            if (!isValidSortField(sortField)) {
                return ResponseEntity.badRequest().body(
                        "Trường sắp xếp không hợp lệ. Các trường hợp lệ: maSv, tenSv, ngaySinh, email, maLop");
            }

            Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

            Page<SinhVien> sinhViens;
            if (search != null && !search.trim().isEmpty()) {
                sinhViens = sinhVienRepository.findByMaSvContainingIgnoreCase(search.trim(), pageable);
            } else {
                sinhViens = sinhVienRepository.findAll(pageable);
            }

            Page<SinhVienDto> dtos = sinhViens.map(this::convertToDto);

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Lỗi khi lấy danh sách sinh viên: " + e.getMessage());
        }
    }

    // Kiểm tra trường sắp xếp hợp lệ
    private boolean isValidSortField(String field) {
        return Arrays.asList("maSv", "tenSv", "ngaySinh", "email", "maLop").contains(field);
    }

    // Lấy thông tin 1 sinh viên theo mã
    @GetMapping("/{maSv}")
    public ResponseEntity<?> getSinhVien(@PathVariable String maSv) {
        try {
            SinhVien sv = sinhVienRepository.findById(maSv)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));
            SinhVienDto dto = convertToDto(sv);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Lỗi khi lấy thông tin sinh viên: " + e.getMessage());
        }
    }

    // Cập nhật sinh viên
    @PutMapping("/{maSv}")
    public ResponseEntity<?> updateSinhVien(
            @PathVariable String maSv,
            @Valid @RequestBody UpdateSinhVienRequest request) {
        try {
            SinhVien sv = sinhVienRepository.findById(maSv)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));

            Lop lop = lopRepository.findById(request.getMaLop())
                    .orElseThrow(() -> new RuntimeException("Lớp không tồn tại"));

            sv.setTenSv(request.getTenSv());
            sv.setNgaySinh(request.getNgaySinh());
            sv.setPhai(request.getPhai());
            sv.setDiaChi(request.getDiaChi());
            sv.setSdt(request.getSdt());
            sv.setEmail(request.getEmail());
            sv.setMaLop(lop);

            sinhVienRepository.save(sv);

            userRepository.findByEmail(sv.getEmail()).ifPresent(user -> {
                user.setEmail(request.getEmail());
                user.setUpdatedAt(Instant.now());
                userRepository.save(user);
            });

            return ResponseEntity.ok("Cập nhật sinh viên thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Lỗi khi cập nhật sinh viên: " + e.getMessage());
        }
    }

    // Xóa sinh viên
    @DeleteMapping("/{maSv}")
    public ResponseEntity<?> deleteSinhVien(@PathVariable String maSv) {
        try {
            SinhVien sv = sinhVienRepository.findById(maSv)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));

            userRepository.findByEmail(sv.getEmail()).ifPresent(userRepository::delete);
            sinhVienRepository.delete(sv);

            return ResponseEntity.ok("Xóa sinh viên thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Lỗi khi xóa sinh viên: " + e.getMessage());
        }
    }

    // Lấy danh sách sinh viên cho điểm danh theo mã thời khóa biểu
    @GetMapping("/students/{maTkb}")
    public ResponseEntity<List<SinhVienDiemDanhDTO>> getStudentsForAttendance(@PathVariable Integer maTkb) {
        List<SinhVienDiemDanhDTO> students = sinhVienService.getStudentsForAttendance(maTkb);
        return ResponseEntity.ok(students);
    }
}
