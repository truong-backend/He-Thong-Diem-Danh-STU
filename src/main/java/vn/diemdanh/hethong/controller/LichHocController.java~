package vn.diemdanh.hethong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.lichhoc.LichHocDto;
import vn.diemdanh.hethong.entity.LichGd;
import vn.diemdanh.hethong.entity.LichHoc;
import vn.diemdanh.hethong.entity.LichHocId;
import vn.diemdanh.hethong.entity.SinhVien;
import vn.diemdanh.hethong.repository.LichGdRepository;
import vn.diemdanh.hethong.repository.LichHocRepository;
import vn.diemdanh.hethong.repository.SinhVienRepository;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.Arrays;

@RestController
@RequestMapping("/api/lichhoc")
public class LichHocController {

    @Autowired
    private LichHocRepository lichHocRepository;

    @Autowired
    private LichGdRepository lichGdRepository;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    // CREATE - Đăng ký lịch học cho sinh viên
    @PostMapping
    public ResponseEntity<?> createLichHoc(@Valid @RequestBody LichHocDto request) {
        try {
            // Kiểm tra sinh viên có tồn tại không
            SinhVien sinhVien = sinhVienRepository.findById(request.getMaSv())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));

            // Kiểm tra lịch giảng dạy có tồn tại không
            LichGd lichGd = lichGdRepository.findById(request.getMaGd())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch giảng dạy"));

            // Tạo ID cho lịch học
            LichHocId lichHocId = new LichHocId();
            lichHocId.setMaSv(sinhVien.getMaSv());
            lichHocId.setMaGd(lichGd.getId());

            // Kiểm tra xem đã tồn tại lịch học này chưa
            if (lichHocRepository.findById(lichHocId).isPresent()) {
                return ResponseEntity.badRequest().body("Sinh viên đã đăng ký lịch học này");
            }

            // Tạo lịch học mới
            LichHoc lichHoc = new LichHoc();
            lichHoc.setId(lichHocId);
            lichHoc.setMaSv(sinhVien);
            lichHoc.setMaGd(lichGd);

            // Lưu lịch học
            lichHoc = lichHocRepository.save(lichHoc);

            return ResponseEntity.ok(convertToDto(lichHoc));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi đăng ký lịch học: " + e.getMessage());
        }
    }

    // READ - Lấy danh sách lịch học của sinh viên có phân trang và sắp xếp
    @GetMapping
    public ResponseEntity<?> getLichHocList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "maGd.ngayBd") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam String maSv,
            @RequestParam(required = false) Integer hocKy,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        try {
            // Kiểm tra tính hợp lệ của trường sắp xếp
            if (!isValidSortField(sortBy)) {
                return ResponseEntity.badRequest().body("Trường sắp xếp không hợp lệ");
            }

            // Kiểm tra sinh viên có tồn tại không
            SinhVien sinhVien = sinhVienRepository.findById(maSv)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));

            // Tạo đối tượng Pageable
            Sort.Direction direction = Sort.Direction.fromString(sortDir);
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

            // Lấy danh sách lịch học theo các điều kiện lọc
            Page<LichHoc> lichHocs;
            if (hocKy != null) {
                lichHocs = lichHocRepository.findByMaSvAndMaGd_HocKy(sinhVien, hocKy, pageable);
            } else if (startDate != null && endDate != null) {
                if (endDate.isBefore(startDate)) {
                    return ResponseEntity.badRequest().body("Ngày kết thúc phải sau ngày bắt đầu");
                }
                lichHocs = lichHocRepository.findByMaSvAndMaGd_NgayBdBetweenOrMaGd_NgayKtBetween(
                        sinhVien, startDate, endDate, startDate, endDate, pageable);
            } else {
                lichHocs = lichHocRepository.findByMaSv(sinhVien, pageable);
            }

            // Chuyển đổi sang DTO
            Page<LichHocDto> dtos = lichHocs.map(this::convertToDto);

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách lịch học: " + e.getMessage());
        }
    }

    // DELETE - Hủy đăng ký lịch học
    @DeleteMapping("/{maSv}/{maGd}")
    public ResponseEntity<?> deleteLichHoc(
            @PathVariable String maSv,
            @PathVariable Long maGd
    ) {
        try {
            // Tạo ID cho lịch học
            LichHocId lichHocId = new LichHocId();
            lichHocId.setMaSv(maSv);
            lichHocId.setMaGd(maGd);

            // Kiểm tra lịch học có tồn tại không
            LichHoc lichHoc = lichHocRepository.findById(lichHocId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch học"));

            lichHocRepository.delete(lichHoc);
            return ResponseEntity.ok("Hủy đăng ký lịch học thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi hủy đăng ký lịch học: " + e.getMessage());
        }
    }

    // Helper method to convert LichHoc to LichHocDto
    private LichHocDto convertToDto(LichHoc lichHoc) {
        LichHocDto dto = new LichHocDto();
        dto.setMaSv(lichHoc.getMaSv().getMaSv());
        dto.setTenSv(lichHoc.getMaSv().getTenSv());
        dto.setMaGd(lichHoc.getMaGd().getId());
        dto.setMaGv(lichHoc.getMaGd().getMaGv().getMaGv());
        dto.setTenGv(lichHoc.getMaGd().getMaGv().getTenGv());
        dto.setMaMh(lichHoc.getMaGd().getMaMh().getMaMh());
        dto.setTenMh(lichHoc.getMaGd().getMaMh().getTenMh());
        dto.setNmh(lichHoc.getMaGd().getNmh());
        dto.setPhongHoc(lichHoc.getMaGd().getPhongHoc());
        dto.setNgayBd(lichHoc.getMaGd().getNgayBd());
        dto.setNgayKt(lichHoc.getMaGd().getNgayKt());
        dto.setStBd(lichHoc.getMaGd().getStBd());
        dto.setStKt(lichHoc.getMaGd().getStKt());
        dto.setHocKy(lichHoc.getMaGd().getHocKy());
        return dto;
    }
    // UPDATE - Cập nhật lịch học (chuyển sang lịch giảng dạy khác)
    @PutMapping("/{maSv}/{maGd}")
    public ResponseEntity<?> updateLichHoc(
            @PathVariable String maSv,
            @PathVariable Long maGd,
            @Valid @RequestBody LichHocDto request
    ) {
        try {
            // Tạo ID cho lịch học hiện tại
            LichHocId currentLichHocId = new LichHocId();
            currentLichHocId.setMaSv(maSv);
            currentLichHocId.setMaGd(maGd);

            // Kiểm tra lịch học hiện tại có tồn tại không
            LichHoc currentLichHoc = lichHocRepository.findById(currentLichHocId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch học"));

            // Kiểm tra sinh viên có tồn tại không
            SinhVien sinhVien = sinhVienRepository.findById(request.getMaSv())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));

            // Kiểm tra lịch giảng dạy mới có tồn tại không
            LichGd newLichGd = lichGdRepository.findById(request.getMaGd())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch giảng dạy mới"));

            // Kiểm tra sinh viên trong request phải khớp với path variable
            if (!sinhVien.getMaSv().equals(maSv)) {
                return ResponseEntity.badRequest().body("Mã sinh viên trong request không khớp");
            }

            // Nếu chuyển sang lịch giảng dạy khác
            if (!newLichGd.getId().equals(maGd)) {
                // Tạo ID cho lịch học mới
                LichHocId newLichHocId = new LichHocId();
                newLichHocId.setMaSv(sinhVien.getMaSv());
                newLichHocId.setMaGd(newLichGd.getId());

                // Kiểm tra lịch học mới đã tồn tại chưa
                if (lichHocRepository.findById(newLichHocId).isPresent()) {
                    return ResponseEntity.badRequest().body("Sinh viên đã đăng ký lịch giảng dạy mới này");
                }

                // Xóa lịch học cũ
                lichHocRepository.delete(currentLichHoc);

                // Tạo lịch học mới
                LichHoc newLichHoc = new LichHoc();
                newLichHoc.setId(newLichHocId);
                newLichHoc.setMaSv(sinhVien);
                newLichHoc.setMaGd(newLichGd);

                // Lưu lịch học mới
                newLichHoc = lichHocRepository.save(newLichHoc);

                return ResponseEntity.ok(convertToDto(newLichHoc));
            } else {
                // Nếu không thay đổi lịch giảng dạy, trả về lịch học hiện tại
                return ResponseEntity.ok(convertToDto(currentLichHoc));
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật lịch học: " + e.getMessage());
        }
    }
    // Helper method to validate sort fields
    private boolean isValidSortField(String field) {
        return Arrays.asList(
                "maGd.ngayBd", "maGd.ngayKt", "maGd.hocKy",
                "maGd.maMh.maMh", "maGd.maGv.maGv"
        ).contains(field);
    }
} 