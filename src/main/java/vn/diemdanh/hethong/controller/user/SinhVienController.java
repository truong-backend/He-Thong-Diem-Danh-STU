package vn.diemdanh.hethong.controller.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.diemdanh.hethong.dto.sinhvien.*;
import vn.diemdanh.hethong.service.SinhVienService;

import java.util.List;

@RestController
@RequestMapping("/api/sinh-vien")
public class SinhVienController {

    @Autowired
    private SinhVienService sinhVienService;

    @PreAuthorize("hasRole('student')")
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
    @PreAuthorize("hasRole('student')")
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

    @PreAuthorize("hasRole('student')")
    // Cập nhật profile sinh viên hiện tại
    @PutMapping(value="/update-profile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfileSinhVien(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestPart("profile") SinhVienUpdateRequest profileRequest,
            @RequestPart(value = "avatarFile", required = false) MultipartFile avatarFile) {
        String email = userDetails.getUsername();
        SinhVienDTOProfile updated = sinhVienService.updateProfileSinhVien(email, profileRequest,avatarFile);
        return ResponseEntity.ok(updated);
    }
    @PreAuthorize("hasRole('admin')")
    // Lấy danh sách sinh viên không phân trang
    @GetMapping("/all")
    public ResponseEntity<?> getAllSinhVienNoPagination() {
        try {
            List<SinhVienDto> dtos = sinhVienService.getAllSinhVienNoPagination();
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Lỗi khi lấy danh sách sinh viên: " + e.getMessage());
        }
    }
    @PreAuthorize("hasRole('admin')")
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
    @PreAuthorize("hasRole('admin')")
    @GetMapping
    public ResponseEntity<?> getAllSinhVien(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "maSv,asc") String[] sort,
            @RequestParam(required = false) String search) {
        try {
            Page<SinhVienDto> dtos = sinhVienService.getAllSinhVienWithPagination(page, size, sort, search);
            return ResponseEntity.ok(dtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Lỗi khi lấy danh sách sinh viên: " + e.getMessage());
        }
    }
    @PreAuthorize("hasRole('admin')")
    // Lấy thông tin 1 sinh viên theo mã
    @GetMapping("/{maSv}")
    public ResponseEntity<?> getSinhVien(@PathVariable String maSv) {
        try {
            SinhVienDto dto = sinhVienService.getSinhVienByMaSv(maSv);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Lỗi khi lấy thông tin sinh viên: " + e.getMessage());
        }
    }
    @PreAuthorize("hasRole('admin')")
    // Cập nhật sinh viên
    @PutMapping("/{maSv}")
    public ResponseEntity<?> updateSinhVien(
            @PathVariable String maSv,
            @Valid @RequestBody UpdateSinhVienRequest request) {
        try {
            sinhVienService.updateSinhVien(maSv, request);
            return ResponseEntity.ok("Cập nhật sinh viên thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Lỗi khi cập nhật sinh viên: " + e.getMessage());
        }
    }
    @PreAuthorize("hasRole('admin')")
    // Xóa sinh viên
    @DeleteMapping("/{maSv}")
    public ResponseEntity<?> deleteSinhVien(@PathVariable String maSv) {
        try {
            sinhVienService.deleteSinhVien(maSv);
            return ResponseEntity.ok("Xóa sinh viên thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Lỗi khi xóa sinh viên: " + e.getMessage());
        }
    }
    @PreAuthorize("hasRole('teacher')")
    // Lấy danh sách sinh viên cho điểm danh theo mã thời khóa biểu
    @GetMapping("/students/{maTkb}")
    public ResponseEntity<List<SinhVienDiemDanhDTO>> getStudentsForAttendance(@PathVariable Integer maTkb) {
        List<SinhVienDiemDanhDTO> students = sinhVienService.getStudentsForAttendance(maTkb);
        return ResponseEntity.ok(students);
    }
}