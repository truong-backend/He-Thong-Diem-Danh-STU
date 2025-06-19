package vn.diemdanh.hethong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.tkb.NgayGiangDayDTO;
import vn.diemdanh.hethong.dto.tkb.TkbDto;
import vn.diemdanh.hethong.entity.LichGd;
import vn.diemdanh.hethong.entity.Tkb;
import vn.diemdanh.hethong.repository.LichGdRepository;
import vn.diemdanh.hethong.repository.TkbRepository;

import jakarta.validation.Valid;
import vn.diemdanh.hethong.service.TkbService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tkb")
public class TkbController {

    @Autowired
    private TkbRepository tkbRepository;

    @Autowired
    private LichGdRepository lichGdRepository;

    @Autowired
    private  TkbService tkbService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllTkbWithoutPaging() {
        try {
            List<Tkb> tkbs = tkbRepository.findAll(Sort.by("ngayHoc").ascending());

            List<TkbDto> dtos = tkbs.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách thời khóa biểu: " + e.getMessage());
        }
    }
    // CREATE - Thêm thời khóa biểu mới
    @PostMapping
    public ResponseEntity<?> createTkb(@Valid @RequestBody TkbDto request) {
        try {
            // Kiểm tra lịch giảng dạy có tồn tại không
            LichGd lichGd = lichGdRepository.findById(request.getMaGd())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch giảng dạy"));

            // Validate ngày học phải nằm trong khoảng thời gian của lịch giảng dạy
            if (request.getNgayHoc().isBefore(lichGd.getNgayBd()) || request.getNgayHoc().isAfter(lichGd.getNgayKt())) {
                return ResponseEntity.badRequest().body("Ngày học phải nằm trong khoảng thời gian của lịch giảng dạy");
            }

            // Validate tiết học phải nằm trong khoảng tiết của lịch giảng dạy
            if (request.getStBd() < lichGd.getStBd() || request.getStKt() > lichGd.getStKt()) {
                return ResponseEntity.badRequest().body("Tiết học phải nằm trong khoảng tiết của lịch giảng dạy");
            }

            // Validate tiết học
            if (request.getStKt() <= request.getStBd()) {
                return ResponseEntity.badRequest().body("Tiết kết thúc phải sau tiết bắt đầu");
            }

            // Tạo thời khóa biểu mới
            Tkb tkb = new Tkb();
            tkb.setMaGd(lichGd);
            tkb.setNgayHoc(request.getNgayHoc());
            tkb.setPhongHoc(request.getPhongHoc());
            tkb.setStBd(request.getStBd());
            tkb.setStKt(request.getStKt());
            tkb.setGhiChu(request.getGhiChu());

            // Lưu thời khóa biểu
            tkb = tkbRepository.save(tkb);

            return ResponseEntity.ok(convertToDto(tkb));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm thời khóa biểu: " + e.getMessage());
        }
    }

    // READ - Lấy danh sách thời khóa biểu có phân trang và sắp xếp
    @GetMapping
    public ResponseEntity<?> getTkbList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ngayHoc") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) Long maGd,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayHoc,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        try {
            // Kiểm tra tính hợp lệ của trường sắp xếp
            if (!isValidSortField(sortBy)) {
                return ResponseEntity.badRequest().body("Trường sắp xếp không hợp lệ");
            }

            // Tạo đối tượng Pageable
            Sort.Direction direction = Sort.Direction.fromString(sortDir);
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

            // Lấy danh sách thời khóa biểu theo các điều kiện lọc
            Page<Tkb> tkbs;
            if (maGd != null) {
                LichGd lichGd = lichGdRepository.findById(maGd)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch giảng dạy"));
                tkbs = tkbRepository.findByMaGd(lichGd, pageable);
            } else if (ngayHoc != null) {
                tkbs = tkbRepository.findByNgayHoc(ngayHoc, pageable);
            } else if (startDate != null && endDate != null) {
                if (endDate.isBefore(startDate)) {
                    return ResponseEntity.badRequest().body("Ngày kết thúc phải sau ngày bắt đầu");
                }
                tkbs = tkbRepository.findByNgayHocBetween(startDate, endDate, pageable);
            } else {
                tkbs = tkbRepository.findAll(pageable);
            }

            // Chuyển đổi sang DTO
            Page<TkbDto> dtos = tkbs.map(this::convertToDto);

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách thời khóa biểu: " + e.getMessage());
        }
    }

    // READ - Lấy thông tin một thời khóa biểu
    @GetMapping("/{id}")
    public ResponseEntity<?> getTkb(@PathVariable Long id) {
        try {
            Tkb tkb = tkbRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thời khóa biểu"));

            return ResponseEntity.ok(convertToDto(tkb));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy thông tin thời khóa biểu: " + e.getMessage());
        }
    }

    // UPDATE - Cập nhật thông tin thời khóa biểu
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTkb(
            @PathVariable Long id,
            @Valid @RequestBody TkbDto request
    ) {
        try {
            Tkb tkb = tkbRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thời khóa biểu"));

            // Kiểm tra và cập nhật lịch giảng dạy nếu có thay đổi
            if (!tkb.getMaGd().getId().equals(request.getMaGd())) {
                LichGd lichGd = lichGdRepository.findById(request.getMaGd())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch giảng dạy"));

                // Validate ngày học phải nằm trong khoảng thời gian của lịch giảng dạy mới
                if (request.getNgayHoc().isBefore(lichGd.getNgayBd()) || request.getNgayHoc().isAfter(lichGd.getNgayKt())) {
                    return ResponseEntity.badRequest().body("Ngày học phải nằm trong khoảng thời gian của lịch giảng dạy");
                }

                // Validate tiết học phải nằm trong khoảng tiết của lịch giảng dạy mới
                if (request.getStBd() < lichGd.getStBd() || request.getStKt() > lichGd.getStKt()) {
                    return ResponseEntity.badRequest().body("Tiết học phải nằm trong khoảng tiết của lịch giảng dạy");
                }

                tkb.setMaGd(lichGd);
            } else {
                // Validate ngày học phải nằm trong khoảng thời gian của lịch giảng dạy hiện tại
                if (request.getNgayHoc().isBefore(tkb.getMaGd().getNgayBd()) || request.getNgayHoc().isAfter(tkb.getMaGd().getNgayKt())) {
                    return ResponseEntity.badRequest().body("Ngày học phải nằm trong khoảng thời gian của lịch giảng dạy");
                }

                // Validate tiết học phải nằm trong khoảng tiết của lịch giảng dạy hiện tại
                if (request.getStBd() < tkb.getMaGd().getStBd() || request.getStKt() > tkb.getMaGd().getStKt()) {
                    return ResponseEntity.badRequest().body("Tiết học phải nằm trong khoảng tiết của lịch giảng dạy");
                }
            }

            // Validate tiết học
            if (request.getStKt() <= request.getStBd()) {
                return ResponseEntity.badRequest().body("Tiết kết thúc phải sau tiết bắt đầu");
            }

            // Cập nhật thông tin
            tkb.setNgayHoc(request.getNgayHoc());
            tkb.setPhongHoc(request.getPhongHoc());
            tkb.setStBd(request.getStBd());
            tkb.setStKt(request.getStKt());
            tkb.setGhiChu(request.getGhiChu());

            // Lưu thay đổi
            tkb = tkbRepository.save(tkb);

            return ResponseEntity.ok(convertToDto(tkb));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật thời khóa biểu: " + e.getMessage());
        }
    }

    // DELETE - Xóa thời khóa biểu
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTkb(@PathVariable Long id) {
        try {
            Tkb tkb = tkbRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thời khóa biểu"));

            tkbRepository.delete(tkb);
            return ResponseEntity.ok("Xóa thời khóa biểu thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi xóa thời khóa biểu: " + e.getMessage());
        }
    }

    // Helper method to convert Tkb to TkbDto
    private TkbDto convertToDto(Tkb tkb) {
        TkbDto dto = new TkbDto();
        dto.setId(tkb.getId());
        dto.setMaGd(tkb.getMaGd().getId());
        dto.setMaGv(tkb.getMaGd().getMaGv().getMaGv());
        dto.setTenGv(tkb.getMaGd().getMaGv().getTenGv());
        dto.setMaMh(tkb.getMaGd().getMaMh().getMaMh());
        dto.setTenMh(tkb.getMaGd().getMaMh().getTenMh());
        dto.setNgayHoc(tkb.getNgayHoc());
        dto.setPhongHoc(tkb.getPhongHoc());
        dto.setStBd(tkb.getStBd());
        dto.setStKt(tkb.getStKt());
        dto.setGhiChu(tkb.getGhiChu());
        return dto;
    }

    // Helper method to validate sort fields
    private boolean isValidSortField(String field) {
        return Arrays.asList("id", "ngayHoc", "phongHoc", "stBd", "stKt")
                .contains(field);
    }


    // 4. LẤY DANH SÁCH NGÀY GIẢNG DẠY

    @GetMapping("/danh-sach-ngay-giang-day")
    public ResponseEntity<List<NgayGiangDayDTO>> getClassDates(@RequestParam Integer maGd) {
        List<NgayGiangDayDTO> classDates = tkbService.getClassDates(maGd);
        return ResponseEntity.ok(classDates);
    }
}