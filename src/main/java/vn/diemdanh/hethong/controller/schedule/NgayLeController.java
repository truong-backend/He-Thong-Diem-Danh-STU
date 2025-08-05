package vn.diemdanh.hethong.controller.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.entity.NgayLe;
import vn.diemdanh.hethong.service.NgayLeService;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ngay-le")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class NgayLeController {

    private final NgayLeService ngayLeService;

    /**
     * Lấy danh sách tất cả ngày lễ
     */
    @PreAuthorize("hasRole('admin')")
    @GetMapping
    public ResponseEntity<List<NgayLe>> getAllNgayLe() {
        try {
            List<NgayLe> ngayLeList = ngayLeService.getAllNgayLe();
            return ResponseEntity.ok(ngayLeList);
        } catch (Exception e) {
            log.error("Lỗi khi lấy danh sách ngày lễ: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Lấy ngày lễ theo ngày cụ thể
     */
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/{ngay}")
    public ResponseEntity<NgayLe> getNgayLeByNgay(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngay) {
        try {
            Optional<NgayLe> ngayLe = ngayLeService.findByNgay(ngay);
            if (ngayLe.isPresent()) {
                return ResponseEntity.ok(ngayLe.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Lỗi khi lấy ngày lễ theo ngày {}: ", ngay, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Kiểm tra ngày có phải ngày lễ không
     */
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/check/{ngay}")
    public ResponseEntity<Boolean> checkHoliday(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngay) {
        try {
            boolean isHoliday = ngayLeService.isHoliday(ngay);
            return ResponseEntity.ok(isHoliday);
        } catch (Exception e) {
            log.error("Lỗi khi kiểm tra ngày lễ {}: ", ngay, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Thêm ngày lễ mới
     */
    @PreAuthorize("hasRole('admin')")
    @PostMapping
    public ResponseEntity<NgayLe> createNgayLe(@RequestBody @Valid NgayLeRequest request) {
        try {
            NgayLe ngayLe = ngayLeService.createNgayLe(request.getNgay(), request.getSoNgayNghi());
            return ResponseEntity.status(HttpStatus.CREATED).body(ngayLe);
        } catch (IllegalArgumentException e) {
            log.warn("Lỗi khi tạo ngày lễ: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Lỗi khi tạo ngày lễ: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Cập nhật ngày lễ
     */
    @PreAuthorize("hasRole('admin')")
    @PutMapping("/{ngay}")
    public ResponseEntity<NgayLe> updateNgayLe(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngay,
            @RequestBody @Valid NgayLeUpdateRequest request) {
        try {
            NgayLe updatedNgayLe = ngayLeService.updateNgayLe(ngay, request.getSoNgayNghi());
            return ResponseEntity.ok(updatedNgayLe);
        } catch (IllegalArgumentException e) {
            log.warn("Lỗi khi cập nhật ngày lễ: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Lỗi khi cập nhật ngày lễ {}: ", ngay, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Xóa ngày lễ
     */
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/{ngay}")
    public ResponseEntity<Void> deleteNgayLe(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngay) {
        try {
            boolean deleted = ngayLeService.deleteNgayLe(ngay);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            log.warn("Lỗi khi xóa ngày lễ: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Lỗi khi xóa ngày lễ {}: ", ngay, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Lấy danh sách ngày lễ theo năm
     */
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/year/{year}")
    public ResponseEntity<List<NgayLe>> getNgayLeByYear(@PathVariable int year) {
        try {
            List<NgayLe> ngayLeList = ngayLeService.getNgayLeByYear(year);
            return ResponseEntity.ok(ngayLeList);
        } catch (Exception e) {
            log.error("Lỗi khi lấy ngày lễ theo năm {}: ", year, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Lấy danh sách ngày lễ theo tháng và năm
     */
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/year/{year}/month/{month}")
    public ResponseEntity<List<NgayLe>> getNgayLeByYearAndMonth(
            @PathVariable int year, @PathVariable int month) {
        try {
            List<NgayLe> ngayLeList = ngayLeService.getNgayLeByYearAndMonth(year, month);
            return ResponseEntity.ok(ngayLeList);
        } catch (Exception e) {
            log.error("Lỗi khi lấy ngày lễ theo tháng {}/{}: ", month, year, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Lấy danh sách ngày lễ trong khoảng thời gian
     */
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/between")
    public ResponseEntity<List<NgayLe>> getNgayLeBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<NgayLe> ngayLeList = ngayLeService.getNgayLeBetween(startDate, endDate);
            return ResponseEntity.ok(ngayLeList);
        } catch (Exception e) {
            log.error("Lỗi khi lấy ngày lễ từ {} đến {}: ", startDate, endDate, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Tìm ngày lễ tiếp theo
     */
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/next")
    public ResponseEntity<NgayLe> getNextHoliday() {
        try {
            Optional<NgayLe> nextHoliday = ngayLeService.getNextHoliday();
            if (nextHoliday.isPresent()) {
                return ResponseEntity.ok(nextHoliday.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Lỗi khi tìm ngày lễ tiếp theo: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Đếm số ngày lễ trong năm
     */
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/count/year/{year}")
    public ResponseEntity<Long> countHolidaysByYear(@PathVariable int year) {
        try {
            long count = ngayLeService.countHolidaysByYear(year);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            log.error("Lỗi khi đếm ngày lễ năm {}: ", year, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Tạo hoặc cập nhật ngày lễ
     */
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/save-or-update")
    public ResponseEntity<NgayLe> saveOrUpdateNgayLe(@RequestBody @Valid NgayLeRequest request) {
        try {
            NgayLe ngayLe = ngayLeService.saveOrUpdateNgayLe(request.getNgay(), request.getSoNgayNghi());
            return ResponseEntity.ok(ngayLe);
        } catch (Exception e) {
            log.error("Lỗi khi tạo/cập nhật ngày lễ: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Xóa nhiều ngày lễ trong khoảng thời gian
     */
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/between")
    public ResponseEntity<Integer> deleteNgayLeBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            int deletedCount = ngayLeService.deleteNgayLeBetween(startDate, endDate);
            return ResponseEntity.ok(deletedCount);
        } catch (Exception e) {
            log.error("Lỗi khi xóa ngày lễ từ {} đến {}: ", startDate, endDate, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DTO classes for request/response
    public static class NgayLeRequest {
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate ngay;
        private Integer soNgayNghi;

        // Getters and setters
        public LocalDate getNgay() { return ngay; }
        public void setNgay(LocalDate ngay) { this.ngay = ngay; }
        public Integer getSoNgayNghi() { return soNgayNghi; }
        public void setSoNgayNghi(Integer soNgayNghi) { this.soNgayNghi = soNgayNghi; }
    }

    public static class NgayLeUpdateRequest {
        private Integer soNgayNghi;

        // Getters and setters
        public Integer getSoNgayNghi() { return soNgayNghi; }
        public void setSoNgayNghi(Integer soNgayNghi) { this.soNgayNghi = soNgayNghi; }
    }
}