package vn.diemdanh.hethong.controller.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import vn.diemdanh.hethong.dto.giaovien.*;
import vn.diemdanh.hethong.dto.thucong.DiemDanhRequest;
import vn.diemdanh.hethong.dto.user.UserDto;
import vn.diemdanh.hethong.service.DiemDanhService;
import vn.diemdanh.hethong.service.FaceRekognitionService;
import vn.diemdanh.hethong.service.GiaoVienService;
import vn.diemdanh.hethong.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/giao-vien")
public class GiaoVienController {

    @Autowired
    private GiaoVienService giaoVienService;

    @Autowired
    private DiemDanhService diemDanhService;

    @Autowired
    private UserService userService;

    @Autowired private FaceRekognitionService faceRekognitionService;

    @PreAuthorize("hasRole('teacher')")
    @PostMapping(value="/diemdanh-face",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> diemdanhFace(
            @RequestPart("file") MultipartFile file,
            @RequestParam("maTkb") Integer maTkb,
            @RequestParam("ngayHoc") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayHoc,
            @RequestParam("ghiChu") String ghiChu
    ) {
        try {
            Optional<String> maSv = faceRekognitionService.detectStudentFaceId(file);
            if(maSv.isPresent()){
                DiemDanhRequest request = new DiemDanhRequest();
                request.setMaTkb(maTkb);
                request.setMaSv(maSv.get());
                request.setNgayHoc(ngayHoc);
                request.setGhiChu(ghiChu);
                int result = diemDanhService.diemDanhSinhVien(request);
                if (result > 0) {
                    return ResponseEntity.ok("Điểm danh thành công");
                }
                return ResponseEntity.ok("Lỗi điểm danh khuôn mặt");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không nhận diện được sinh viên");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Điểm danh thất bại: " + e.getMessage());
        }
    }

    // Điểm danh quét mã QR sinh viên
    @PreAuthorize("hasRole('teacher')")
    @PostMapping("/diemdanhnguoc")
    public ResponseEntity<?> diemdanhQuetMaQRSinhVien(@Valid @RequestBody DiemDanhRequest request) {
        try {
            int result = diemDanhService.diemDanhSinhVien(request);
            if (result > 0) {
                return ResponseEntity.ok("Điểm danh thành công");
            }
            return ResponseEntity.ok("Lỗi điểm danh");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Điểm danh thất bại: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('teacher')")
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

    @PreAuthorize("hasRole('teacher')")
    // Thêm giáo viên mới
    @PostMapping
    public ResponseEntity<?> createGiaoVien(@Valid @RequestBody CreateGiaoVienRequest request) {
        try {
            giaoVienService.createGiaoVien(request);
            return ResponseEntity.ok("Thêm giáo viên thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm giáo viên: " + e.getMessage());
        }
    }

    // Lấy danh sách giáo viên có phân trang, sắp xếp và tìm kiếm
    @PreAuthorize("hasRole('admin')")
    @GetMapping
    public ResponseEntity<?> getAllGiaoVien(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "maGv,asc") String[] sort,
            @RequestParam(required = false) String search) {
        try {
            Page<GiaoVienDto> dtos = giaoVienService.getAllGiaoVien(page, size, sort, search);
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách giáo viên: " + e.getMessage());
        }
    }

    // Lấy thông tin giáo viên theo mã
    @GetMapping("/{maGv}")
    public ResponseEntity<?> getGiaoVien(@PathVariable String maGv) {
        try {
            GiaoVienDto dto = giaoVienService.getGiaoVienByMaGv(maGv);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy thông tin giáo viên: " + e.getMessage());
        }
    }

    // Cập nhật giáo viên theo mã
    @PreAuthorize("hasRole('admin')")
    @PutMapping("/{maGv}")
    public ResponseEntity<?> updateGiaoVien(
            @PathVariable String maGv,
            @Valid @RequestBody UpdateGiaoVienRequest request) {
        try {
            giaoVienService.updateGiaoVien(maGv, request);
            return ResponseEntity.ok("Cập nhật giáo viên thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật giáo viên: " + e.getMessage());
        }
    }

    // Xóa giáo viên theo mã
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/{maGv}")
    public ResponseEntity<?> deleteGiaoVien(@PathVariable String maGv) {
        try {
            giaoVienService.deleteGiaoVien(maGv);
            return ResponseEntity.ok("Xóa giáo viên thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi xóa giáo viên: " + e.getMessage());
        }
    }

    // Lấy danh sách giáo viên không phân trang
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllGiaoVienWithoutPaging() {
        try {
            List<GiaoVienDto> dtos = giaoVienService.getAllGiaoVienWithoutPaging();
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách giáo viên: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('admin')")
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
}