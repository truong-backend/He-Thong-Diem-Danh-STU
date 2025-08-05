package vn.diemdanh.hethong.controller.schedule;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.hocky.HocKyDTO;
import vn.diemdanh.hethong.dto.lichgd.LichGdDto;
import vn.diemdanh.hethong.service.LichGdService;

import java.util.List;

@RestController
@RequestMapping("/api/lichgd")
public class LichGdController {

    @Autowired
    private LichGdService lichGdService;

    // -------------------- CREATE --------------------
    @PostMapping
    public ResponseEntity<?> createLichGd(@Valid @RequestBody LichGdDto request) {
        try {
            LichGdDto result = lichGdService.createLichGd(request);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm lịch giảng dạy: " + e.getMessage());
        }
    }

    // -------------------- READ --------------------
    @GetMapping
    public ResponseEntity<?> getLichGdList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String maGv,
            @RequestParam(required = false) String maMh,
            @RequestParam(required = false) Integer hocKy) {
        try {
            Page<LichGdDto> result = lichGdService.getLichGdList(page, size, sortBy, sortDir, maGv, maMh, hocKy);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLichGd(@PathVariable Long id) {
        try {
            LichGdDto result = lichGdService.getLichGdById(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy thông tin: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllLichGd() {
        try {
            List<LichGdDto> result = lichGdService.getAllLichGd();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách: " + e.getMessage());
        }
    }

    // -------------------- UPDATE --------------------
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLichGd(@PathVariable Long id, @Valid @RequestBody LichGdDto request) {
        try {
            LichGdDto result = lichGdService.updateLichGd(id, request);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật: " + e.getMessage());
        }
    }

    // -------------------- DELETE --------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLichGd(@PathVariable Long id) {
        try {
            lichGdService.deleteLichGd(id);
            return ResponseEntity.ok("Xóa thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi xóa: " + e.getMessage());
        }
    }

    // -------------------- HỌC KỲ --------------------
    @PreAuthorize("hasAnyRole('teacher', 'admin', 'student')")
    @GetMapping("/hoc-ky/{maGv}")
    public ResponseEntity<List<HocKyDTO>> getAllHocKy(@PathVariable String maGv) {
        try {
            List<HocKyDTO> result = lichGdService.getAllSemesters(maGv);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasAnyRole('teacher', 'admin', 'student')")
    @GetMapping("/hoc-ky")
    public ResponseEntity<List<HocKyDTO>> getAllHocKy() {
        try {
            List<HocKyDTO> result = lichGdService.getAllHocKy();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // -------------------- MÃ GIẢNG DẠY --------------------
    @PreAuthorize("hasRole('teacher')")
    @GetMapping("/ma-gd")
    public ResponseEntity<Integer> getMaGd(
            @RequestParam int hocKy,
            @RequestParam String maMh,
            @RequestParam String maGv,
            @RequestParam int nhom) {
        try {
            Integer result = lichGdService.getMaGd(hocKy, maMh, maGv, nhom);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // -------------------- LẤY LỊCH GIẢNG DẠY THEO MÃ GIÁO VIÊN --------------------
    @PreAuthorize("hasRole('teacher')")
    @GetMapping("/giang-vien/{maGv}")
    public ResponseEntity<List<LichGdDto>> getLichByMaGv(@PathVariable String maGv) {
        try {
            List<LichGdDto> result = lichGdService.getLichGdByMaGv(maGv);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}