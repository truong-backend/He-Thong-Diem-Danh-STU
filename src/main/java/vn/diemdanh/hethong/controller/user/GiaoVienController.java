package vn.diemdanh.hethong.controller.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.diemdanh.DiemDanhQRSinhVienRequest;
import vn.diemdanh.hethong.dto.giaovien.*;
import vn.diemdanh.hethong.dto.user.UserDto;
import vn.diemdanh.hethong.entity.*;
import vn.diemdanh.hethong.repository.GiaoVienRepository;
import vn.diemdanh.hethong.repository.UserRepository;
import vn.diemdanh.hethong.service.DiemDanhService;
import vn.diemdanh.hethong.service.GiaoVienService;
import vn.diemdanh.hethong.service.UserService;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/giao-vien")
public class GiaoVienController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GiaoVienRepository giaoVienRepository;

    @Autowired
    private GiaoVienService giaoVienService;


    @Autowired
    private DiemDanhService diemDanhService;

    @Autowired
    private UserService userService;

    // Điểm danh quét mã QR sinh viên
    @PostMapping("/diemdanhnguoc")
    public ResponseEntity<?> diemdanhQuetMaQRSinhVien(@Valid @RequestBody DiemDanhQRSinhVienRequest request) {
        try {
            int result = diemDanhService.diemDanhMaQRSinhVien(request);
            if (result > 0) {
                return ResponseEntity.ok("Điểm danh thành công");
            }
            return ResponseEntity.ok("Lỗi điểm danh");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Điểm danh thất bại: " + e.getMessage());
        }
    }

    // Lấy profile giáo viên hiện tại
    @GetMapping("/profile")
    public ResponseEntity<?> getProfileTeacher(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        GiaoVienDTO_Profile profile = giaoVienService.getGiaoVienByEmail(email);
        return ResponseEntity.ok(profile);
    }

    // Cập nhật profile giáo viên hiện tại
    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfileTeacher(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody GiaoVienDTO_Profile profileRequest) {
        String email = userDetails.getUsername();
        GiaoVienDTO_Profile updatedProfile = giaoVienService.updateProfileGiaoVien(email, profileRequest);
        return ResponseEntity.ok(updatedProfile);
    }

    // Thêm giáo viên mới
    @PostMapping
    public ResponseEntity<?> createGiaoVien(@Valid @RequestBody CreateGiaoVienRequest request) {
        try {
            if (giaoVienRepository.existsById(request.getMaGv())) {
                return ResponseEntity.badRequest().body("Mã giáo viên đã tồn tại");
            }
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email đã tồn tại");
            }

            GiaoVien giaoVien = new GiaoVien();
            giaoVien.setMaGv(request.getMaGv());
            giaoVien.setTenGv(request.getTenGv());
            giaoVien.setNgaySinh(request.getNgaySinh());
            giaoVien.setPhai(request.getPhai());
            giaoVien.setDiaChi(request.getDiaChi());
            giaoVien.setSdt(request.getSdt());
            giaoVien.setEmail(request.getEmail());
            giaoVien = giaoVienRepository.save(giaoVien);
            UserDto userDto = new UserDto();
            userDto.setUsername(request.getMaGv());
            userDto.setEmail(request.getEmail());
            userDto.setPassword(request.getMaGv()); // Default password là mã GV
            userDto.setRole("teacher");

            User user = userService.createUser(userDto);
            user.setMaGv(giaoVien);
            userRepository.save(user);

            return ResponseEntity.ok("Thêm giáo viên thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm giáo viên: " + e.getMessage());
        }
    }

    // Lấy danh sách giáo viên có phân trang, sắp xếp và tìm kiếm
    @GetMapping
    public ResponseEntity<?> getAllGiaoVien(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "maGv,asc") String[] sort,
            @RequestParam(required = false) String search) {

        try {
            String sortField = sort[0];
            String sortDirection = sort.length > 1 ? sort[1] : "asc";

            if (!isValidSortField(sortField)) {
                return ResponseEntity.badRequest().body(
                        "Trường sắp xếp không hợp lệ. Các trường hợp lệ: maGv, tenGv, ngaySinh, email");
            }

            Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

            Page<GiaoVien> giaoViens;
            if (search != null && !search.trim().isEmpty()) {
                giaoViens = giaoVienRepository.findByMaGvContainingIgnoreCase(search.trim(), pageable);
            } else {
                giaoViens = giaoVienRepository.findAll(pageable);
            }

            Page<GiaoVienDto> dtos = giaoViens.map(this::convertToDto);

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách giáo viên: " + e.getMessage());
        }
    }

    // Lấy thông tin giáo viên theo mã
    @GetMapping("/{maGv}")
    public ResponseEntity<?> getGiaoVien(@PathVariable String maGv) {
        try {
            GiaoVien giaoVien = giaoVienRepository.findById(maGv)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy giáo viên"));

            GiaoVienDto dto = convertToDto(giaoVien);
            Optional<User> userOpt = userRepository.findByEmail(giaoVien.getEmail());
            dto.setHasAccount(userOpt.isPresent());

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy thông tin giáo viên: " + e.getMessage());
        }
    }

    // Cập nhật giáo viên theo mã
    @PutMapping("/{maGv}")
    public ResponseEntity<?> updateGiaoVien(
            @PathVariable String maGv,
            @Valid @RequestBody UpdateGiaoVienRequest request) {

        try {
            GiaoVien giaoVien = giaoVienRepository.findById(maGv)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy giáo viên"));

            giaoVien.setTenGv(request.getTenGv());
            giaoVien.setNgaySinh(request.getNgaySinh());
            giaoVien.setPhai(request.getPhai());
            giaoVien.setDiaChi(request.getDiaChi());
            giaoVien.setSdt(request.getSdt());
            giaoVien.setEmail(request.getEmail());

            giaoVienRepository.save(giaoVien);

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

    // Xóa giáo viên theo mã
    @DeleteMapping("/{maGv}")
    public ResponseEntity<?> deleteGiaoVien(@PathVariable String maGv) {
        try {
            GiaoVien giaoVien = giaoVienRepository.findById(maGv)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy giáo viên"));

            // Xóa tài khoản user nếu có
            userRepository.findByEmail(giaoVien.getEmail()).ifPresent(userRepository::delete);

            giaoVienRepository.delete(giaoVien);

            return ResponseEntity.ok("Xóa giáo viên thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi xóa giáo viên: " + e.getMessage());
        }
    }

    // Lấy danh sách giáo viên không phân trang
    @GetMapping("/all")
    public ResponseEntity<?> getAllGiaoVienWithoutPaging() {
        try {
            List<GiaoVien> giaoViens = giaoVienRepository.findAll(Sort.by("maGv").ascending());
            List<GiaoVienDto> dtos = giaoViens.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách giáo viên: " + e.getMessage());
        }
    }

    // Lấy danh sách giáo viên theo học kỳ, năm học và mã môn
    @GetMapping("/danh-sach-giao-vien")
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

    // Kiểm tra tính hợp lệ của trường sắp xếp
    private boolean isValidSortField(String field) {
        return Arrays.asList("maGv", "tenGv", "ngaySinh", "email").contains(field);
    }

    // Chuyển đổi entity GiaoVien sang DTO
    private GiaoVienDto convertToDto(GiaoVien giaoVien) {
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
    }
}
