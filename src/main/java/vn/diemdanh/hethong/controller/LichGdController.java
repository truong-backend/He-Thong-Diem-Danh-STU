package vn.diemdanh.hethong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.hocky.HocKyDTO;
import vn.diemdanh.hethong.dto.lichgd.LichGdDto;
import vn.diemdanh.hethong.entity.GiaoVien;
import vn.diemdanh.hethong.entity.LichGd;
import vn.diemdanh.hethong.entity.MonHoc;
import vn.diemdanh.hethong.repository.GiaoVienRepository;
import vn.diemdanh.hethong.repository.LichGdRepository;
import vn.diemdanh.hethong.repository.MonHocRepository;

import jakarta.validation.Valid;
import vn.diemdanh.hethong.service.DiemDanhService;
import vn.diemdanh.hethong.service.LichGdService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lichgd")
public class LichGdController {

    @Autowired
    private LichGdRepository lichGdRepository;

    @Autowired
    private GiaoVienRepository giaoVienRepository;

    @Autowired
    private MonHocRepository monHocRepository;



    // CREATE - Thêm lịch giảng dạy mới
    @PostMapping
    public ResponseEntity<?> createLichGd(@Valid @RequestBody LichGdDto request) {
        try {
            // Kiểm tra giáo viên có tồn tại không
            GiaoVien giaoVien = giaoVienRepository.findById(request.getMaGv())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy giáo viên"));

            // Kiểm tra môn học có tồn tại không
            MonHoc monHoc = monHocRepository.findById(request.getMaMh())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));

            // Validate thời gian
            if (request.getNgayKt().isBefore(request.getNgayBd())) {
                return ResponseEntity.badRequest().body("Ngày kết thúc phải sau ngày bắt đầu");
            }

            // Validate tiết học
            if (request.getStKt() <= request.getStBd()) {
                return ResponseEntity.badRequest().body("Tiết kết thúc phải sau tiết bắt đầu");
            }

            // Tạo lịch giảng dạy mới
            LichGd lichGd = new LichGd();
            lichGd.setMaGv(giaoVien);
            lichGd.setMaMh(monHoc);
            lichGd.setNmh(request.getNmh());
            lichGd.setPhongHoc(request.getPhongHoc());
            lichGd.setNgayBd(request.getNgayBd());
            lichGd.setNgayKt(request.getNgayKt());
            lichGd.setStBd(request.getStBd());
            lichGd.setStKt(request.getStKt());
            lichGd.setHocKy(request.getHocKy());

            // Lưu lịch giảng dạy
            lichGd = lichGdRepository.save(lichGd);

            return ResponseEntity.ok(convertToDto(lichGd));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm lịch giảng dạy: " + e.getMessage());
        }
    }

    // READ - Lấy danh sách lịch giảng dạy có phân trang và sắp xếp
    @GetMapping
    public ResponseEntity<?> getLichGdList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String maGv,
            @RequestParam(required = false) String maMh,
            @RequestParam(required = false) Integer hocKy
    ) {
        try {
            // Kiểm tra tính hợp lệ của trường sắp xếp
            if (!isValidSortField(sortBy)) {
                return ResponseEntity.badRequest().body("Trường sắp xếp không hợp lệ");
            }

            // Tạo đối tượng Pageable
            Sort.Direction direction = Sort.Direction.fromString(sortDir);
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

            // Lấy danh sách lịch giảng dạy theo các điều kiện lọc
            Page<LichGd> lichGds;
            if (maGv != null && !maGv.isEmpty()) {
                GiaoVien giaoVien = giaoVienRepository.findById(maGv)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy giáo viên"));
                lichGds = lichGdRepository.findByMaGv(giaoVien, pageable);
            } else if (maMh != null && !maMh.isEmpty()) {
                MonHoc monHoc = monHocRepository.findById(maMh)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));
                lichGds = lichGdRepository.findByMaMh(monHoc, pageable);
            } else if (hocKy != null) {
                lichGds = lichGdRepository.findByHocKy(hocKy, pageable);
            } else {
                lichGds = lichGdRepository.findAll(pageable);
            }

            // Chuyển đổi sang DTO
            Page<LichGdDto> dtos = lichGds.map(this::convertToDto);

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách lịch giảng dạy: " + e.getMessage());
        }
    }

    // READ - Lấy thông tin một lịch giảng dạy
    @GetMapping("/{id}")
    public ResponseEntity<?> getLichGd(@PathVariable Long id) {
        try {
            LichGd lichGd = lichGdRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch giảng dạy"));

            return ResponseEntity.ok(convertToDto(lichGd));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy thông tin lịch giảng dạy: " + e.getMessage());
        }
    }

    // UPDATE - Cập nhật thông tin lịch giảng dạy
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLichGd(
            @PathVariable Long id,
            @Valid @RequestBody LichGdDto request
    ) {
        try {
            LichGd lichGd = lichGdRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch giảng dạy"));

            // Kiểm tra và cập nhật giáo viên nếu có thay đổi
            if (!lichGd.getMaGv().getMaGv().equals(request.getMaGv())) {
                GiaoVien giaoVien = giaoVienRepository.findById(request.getMaGv())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy giáo viên"));
                lichGd.setMaGv(giaoVien);
            }

            // Kiểm tra và cập nhật môn học nếu có thay đổi
            if (!lichGd.getMaMh().getMaMh().equals(request.getMaMh())) {
                MonHoc monHoc = monHocRepository.findById(request.getMaMh())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));
                lichGd.setMaMh(monHoc);
            }

            // Validate thời gian
            if (request.getNgayKt().isBefore(request.getNgayBd())) {
                return ResponseEntity.badRequest().body("Ngày kết thúc phải sau ngày bắt đầu");
            }

            // Validate tiết học
            if (request.getStKt() <= request.getStBd()) {
                return ResponseEntity.badRequest().body("Tiết kết thúc phải sau tiết bắt đầu");
            }

            // Cập nhật thông tin
            lichGd.setNmh(request.getNmh());
            lichGd.setPhongHoc(request.getPhongHoc());
            lichGd.setNgayBd(request.getNgayBd());
            lichGd.setNgayKt(request.getNgayKt());
            lichGd.setStBd(request.getStBd());
            lichGd.setStKt(request.getStKt());
            lichGd.setHocKy(request.getHocKy());

            // Lưu thay đổi
            lichGd = lichGdRepository.save(lichGd);

            return ResponseEntity.ok(convertToDto(lichGd));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật lịch giảng dạy: " + e.getMessage());
        }
    }

    // DELETE - Xóa lịch giảng dạy
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLichGd(@PathVariable Long id) {
        try {
            LichGd lichGd = lichGdRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch giảng dạy"));

            lichGdRepository.delete(lichGd);
            return ResponseEntity.ok("Xóa lịch giảng dạy thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi xóa lịch giảng dạy: " + e.getMessage());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllLichGd() {
        try {
            List<LichGd> lichGds = lichGdRepository.findAll();
            List<LichGdDto> dtos = lichGds.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách lịch giảng dạy: " + e.getMessage());
        }
    }

    // Helper method to convert LichGd to LichGdDto
    private LichGdDto convertToDto(LichGd lichGd) {
        LichGdDto dto = new LichGdDto();
        dto.setId(lichGd.getId());
        dto.setMaGv(lichGd.getMaGv().getMaGv());
        dto.setTenGv(lichGd.getMaGv().getTenGv());
        dto.setMaMh(lichGd.getMaMh().getMaMh());
        dto.setTenMh(lichGd.getMaMh().getTenMh());
        dto.setNmh(lichGd.getNmh());
        dto.setPhongHoc(lichGd.getPhongHoc());
        dto.setNgayBd(lichGd.getNgayBd());
        dto.setNgayKt(lichGd.getNgayKt());
        dto.setStBd(lichGd.getStBd());
        dto.setStKt(lichGd.getStKt());
        dto.setHocKy(lichGd.getHocKy());
        return dto;
    }

    // Helper method to validate sort fields
    private boolean isValidSortField(String field) {
        return Arrays.asList("id", "nmh", "phongHoc", "ngayBd", "ngayKt", "stBd", "stKt", "hocKy")
                .contains(field);
    }

    // 1. LẤY DANH SÁCH HỌC KỲ
    @Autowired
    private LichGdService lichGdService;
    @GetMapping("/hoc-ky/{maGv}")
    public ResponseEntity<List<HocKyDTO>> getAllHocKy(@PathVariable String maGv) {
        List<HocKyDTO> hocKyList = lichGdService.getAllSemesters(maGv);
        return ResponseEntity.ok(hocKyList);
    }
    ////lấy tất danh sách học kỳ cho admin
    @GetMapping("/hoc-ky")
    public ResponseEntity<List<HocKyDTO>> getAllHocKy() {
        try {
            List<HocKyDTO> hocKyList = lichGdService.getAllHocKy();
            return ResponseEntity.ok(hocKyList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 