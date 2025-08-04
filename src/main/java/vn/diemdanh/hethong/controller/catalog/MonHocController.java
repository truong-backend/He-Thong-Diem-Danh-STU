package vn.diemdanh.hethong.controller.catalog;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.diemdanh.MonHocSinhVienDto;
import vn.diemdanh.hethong.dto.lichhoc.LichHocTheoThuDto;
import vn.diemdanh.hethong.dto.monhoc.MonHocGiangVienDTO;
import vn.diemdanh.hethong.dto.monhoc.MonHocDto;
import vn.diemdanh.hethong.dto.monhoc.MonHocKetQuaDiemDanhDTO;
import vn.diemdanh.hethong.dto.monhoc.NhomMonHocDTO;
import vn.diemdanh.hethong.dto.tkb.ThoiKhoaBieuDTO;
import vn.diemdanh.hethong.service.MonHocService;

import java.util.List;

@RestController
@RequestMapping("/api/monhoc")
public class MonHocController {

    @Autowired
    private MonHocService monHocService;

    // ========== BASIC CRUD OPERATIONS ==========

    /**
     * Tạo môn học mới
     */
    @PostMapping
    public ResponseEntity<?> createMonHoc(@Valid @RequestBody MonHocDto request) {
        try {
            MonHocDto result = monHocService.createMonHoc(request);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm môn học: " + e.getMessage());
        }
    }

    /**
     * Lấy danh sách môn học có phân trang
     */
    @GetMapping
    public ResponseEntity<?> getMonHocList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "maMh") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        try {
            Page<MonHocDto> result = monHocService.getMonHocList(page, size, sortBy, sortDir);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách môn học: " + e.getMessage());
        }
    }

    /**
     * Lấy thông tin môn học theo mã
     */
    @GetMapping("/{maMh}")
    public ResponseEntity<?> getMonHoc(@PathVariable String maMh) {
        try {
            MonHocDto result = monHocService.getMonHocById(maMh);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy thông tin môn học: " + e.getMessage());
        }
    }

    /**
     * Lấy tất cả môn học không phân trang
     */
    @PreAuthorize("hasAnyRole('GIANG_VIEN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllMonHocWithoutPaging() {
        try {
            List<MonHocDto> result = monHocService.getAllMonHocWithoutPaging();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách môn học: " + e.getMessage());
        }
    }

    /**
     * Cập nhật thông tin môn học
     */
    @PutMapping("/{maMh}")
    public ResponseEntity<?> updateMonHoc(@PathVariable String maMh, @Valid @RequestBody MonHocDto request) {
        try {
            MonHocDto result = monHocService.updateMonHoc(maMh, request);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật môn học: " + e.getMessage());
        }
    }

    /**
     * Xóa môn học
     */
    @DeleteMapping("/{maMh}")
    public ResponseEntity<?> deleteMonHoc(@PathVariable String maMh) {
        try {
            monHocService.deleteMonHoc(maMh);
            return ResponseEntity.ok("Xóa môn học thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi xóa môn học: " + e.getMessage());
        }
    }

    // ========== SPECIALIZED ENDPOINTS ==========

    /**
     * Lấy môn học cho kết quả điểm danh
     */
    @GetMapping("/monHocKetQuaDiemDanh")
    public ResponseEntity<List<MonHocKetQuaDiemDanhDTO>> getMonHocForDiemDanh() {
        return ResponseEntity.ok(monHocService.getMonHocKetQuaDiemDanh());
    }

    /**
     * Lấy danh sách môn học theo giáo viên
     */
    @GetMapping("/danh-sach-mon-hoc-theo-giao-vien")
    public ResponseEntity<List<MonHocGiangVienDTO>> getMonHocByGiaoVien(
            @RequestParam String maGv,
            @RequestParam Integer hocKy,
            @RequestParam Integer namHoc) {
        return ResponseEntity.ok(monHocService.getSubjectsByTeacher(maGv, hocKy, namHoc));
    }

    /**
     * Lấy danh sách nhóm môn học
     */
    @GetMapping("/danh-sach-nhom-mon-hoc")
    public ResponseEntity<List<NhomMonHocDTO>> getSubjectGroups(
            @RequestParam String teacherId,
            @RequestParam String subjectId,
            @RequestParam Integer semester,
            @RequestParam Integer year) {
        return ResponseEntity.ok(monHocService.getSubjectGroups(teacherId, subjectId, semester, year));
    }

    /**
     * Lấy danh sách môn học của sinh viên
     */
    @GetMapping("/danh-sach-mon-hoc-cua-sinh-vien")
    public ResponseEntity<List<MonHocSinhVienDto>> getMonHocCuaSinhVien(@RequestParam String maSv) {
        return ResponseEntity.ok(monHocService.getMonHocCuaSinhVien(maSv));
    }

    /**
     * Lấy lịch học theo thứ
     */
    @GetMapping("/danh-sach-mon-hoc-theo-thu/{thu}")
    public ResponseEntity<List<LichHocTheoThuDto>> getLichHocTheoThu(@PathVariable int thu) {
        return ResponseEntity.ok(monHocService.getLichHocTheoThu(thu));
    }

    /**
     * Lấy thời khóa biểu của sinh viên
     */
    @GetMapping("/danh-sach-mon-hoc/{maSv}")
    public ResponseEntity<List<ThoiKhoaBieuDTO>> getThoiKhoaBieu(@PathVariable String maSv) {
        return ResponseEntity.ok(monHocService.getThoiKhoaBieuByMaSv(maSv));
    }

    /**
     * Lấy môn học theo học kỳ và năm
     */
    @GetMapping("/mon-hoc-theo-hoc-ky-nam")
    public ResponseEntity<List<MonHocDto>> getMonHocByHocKyAndNam(
            @RequestParam Integer hocKy,
            @RequestParam Integer namHoc) {
        try {
            List<MonHocDto> result = monHocService.getMonHocByHocKyAndNam(hocKy, namHoc);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}