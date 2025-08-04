package vn.diemdanh.hethong.controller.catalog;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import vn.diemdanh.hethong.dto.lop.LopDto;
import vn.diemdanh.hethong.service.LopService;

import java.util.List;

@RestController
@RequestMapping("/api/lop")
public class LopController {

    @Autowired
    private LopService lopService;

    /**
     * Tạo lớp mới.
     */
    @PreAuthorize("hasRole('admin')")
    @PostMapping
    public ResponseEntity<?> createLop(@Valid @RequestBody LopDto request) {
        try {
            LopDto dto = lopService.createLop(request);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm lớp: " + e.getMessage());
        }
    }

    /**
     * Lấy danh sách lớp có phân trang và sắp xếp.
     */
    @PreAuthorize("hasRole('admin')")
    @GetMapping
    public ResponseEntity<?> getLopList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "maLop") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String maKhoa
    ) {
        try {
            Page<LopDto> dtoPage = lopService.getLopList(page, size, sortBy, sortDir, maKhoa);
            return ResponseEntity.ok(dtoPage);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách lớp: " + e.getMessage());
        }
    }

    /**
     * Lấy danh sách tất cả lớp (không phân trang).
     */
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllLops() {
        try {
            List<LopDto> dtos = lopService.getAllLops();
            return ResponseEntity.ok(dtos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách lớp: " + e.getMessage());
        }
    }

    /**
     * Lấy thông tin chi tiết một lớp theo mã lớp.
     */
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/{maLop}")
    public ResponseEntity<?> getLop(@PathVariable String maLop) {
        try {
            LopDto dto = lopService.getLopById(maLop);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy thông tin lớp: " + e.getMessage());
        }
    }

    /**
     * Cập nhật thông tin lớp.
     */
    @PreAuthorize("hasRole('admin')")
    @PutMapping("/{maLop}")
    public ResponseEntity<?> updateLop(
            @PathVariable String maLop,
            @Valid @RequestBody LopDto request
    ) {
        try {
            LopDto dto = lopService.updateLop(maLop, request);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật lớp: " + e.getMessage());
        }
    }

    /**
     * Xóa lớp theo mã lớp.
     */
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/{maLop}")
    public ResponseEntity<?> deleteLop(@PathVariable String maLop) {
        try {
            lopService.deleteLop(maLop);
            return ResponseEntity.ok("Xóa lớp thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Lỗi khi xóa lớp: " + e.getMessage());
        }
    }
}
