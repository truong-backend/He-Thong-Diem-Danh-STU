package vn.diemdanh.hethong.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.diemdanh.ListDiemDanhMonHoc.MonHocSinhVienDto;
import vn.diemdanh.hethong.dto.monhoc.MonHocGiangVienDTO;
import vn.diemdanh.hethong.dto.monhoc.MonHocDto;
import vn.diemdanh.hethong.dto.monhoc.NhomMonHocDTO;
import vn.diemdanh.hethong.entity.MonHoc;
import vn.diemdanh.hethong.repository.MonHocRepository;

import jakarta.validation.Valid;
import vn.diemdanh.hethong.service.MonHocService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/monhoc")
public class MonHocController {
    @Autowired
    private MonHocRepository monHocRepository;

    @Autowired
    private MonHocService monHocService;

    // CREATE - Thêm môn học mới
    @PostMapping
    public ResponseEntity<?> createMonHoc(@Valid @RequestBody MonHocDto request) {
        try {
            // Kiểm tra mã môn học đã tồn tại chưa
            if (monHocRepository.findById(request.getMaMh()).isPresent()) {
                return ResponseEntity.badRequest().body("Mã môn học đã tồn tại");
            }

            // Kiểm tra số tiết phải lớn hơn 0
            if (request.getSoTiet() <= 0) {
                return ResponseEntity.badRequest().body("Số tiết phải lớn hơn 0");
            }

            // Tạo môn học mới
            MonHoc monHoc = new MonHoc();
            monHoc.setMaMh(request.getMaMh());
            monHoc.setTenMh(request.getTenMh());
            monHoc.setSoTiet(request.getSoTiet());

            // Lưu môn học
            monHoc = monHocRepository.save(monHoc);

            return ResponseEntity.ok(convertToDto(monHoc));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm môn học: " + e.getMessage());
        }
    }

    // READ - Lấy danh sách môn học có phân trang và sắp xếp
    @GetMapping
    public ResponseEntity<?> getMonHocList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "maMh") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        try {
            // Kiểm tra tính hợp lệ của trường sắp xếp
            if (!isValidSortField(sortBy)) {
                return ResponseEntity.badRequest().body("Trường sắp xếp không hợp lệ");
            }

            // Tạo đối tượng Pageable
            Sort.Direction direction = Sort.Direction.fromString(sortDir);
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

            // Lấy danh sách môn học
            Page<MonHoc> monHocs = monHocRepository.findAll(pageable);

            // Chuyển đổi sang DTO
            Page<MonHocDto> dtos = monHocs.map(this::convertToDto);

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách môn học: " + e.getMessage());
        }
    }

    // READ - Lấy thông tin một môn học
    @GetMapping("/{maMh}")
    public ResponseEntity<?> getMonHoc(@PathVariable String maMh) {
        try {
            MonHoc monHoc = monHocRepository.findById(maMh)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));

            return ResponseEntity.ok(convertToDto(monHoc));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy thông tin môn học: " + e.getMessage());
        }
    }

    // UPDATE - Cập nhật thông tin môn học
    @PutMapping("/{maMh}")
    public ResponseEntity<?> updateMonHoc(
            @PathVariable String maMh,
            @Valid @RequestBody MonHocDto request
    ) {
        try {
            MonHoc monHoc = monHocRepository.findById(maMh)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));

            // Kiểm tra số tiết phải lớn hơn 0
            if (request.getSoTiet() <= 0) {
                return ResponseEntity.badRequest().body("Số tiết phải lớn hơn 0");
            }

            // Cập nhật thông tin
            monHoc.setTenMh(request.getTenMh());
            monHoc.setSoTiet(request.getSoTiet());

            // Lưu thay đổi
            monHoc = monHocRepository.save(monHoc);

            return ResponseEntity.ok(convertToDto(monHoc));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật môn học: " + e.getMessage());
        }
    }

    // DELETE - Xóa môn học
    @DeleteMapping("/{maMh}")
    public ResponseEntity<?> deleteMonHoc(@PathVariable String maMh) {
        try {
            MonHoc monHoc = monHocRepository.findById(maMh)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));

            monHocRepository.delete(monHoc);
            return ResponseEntity.ok("Xóa môn học thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi xóa môn học: " + e.getMessage());
        }
    }

    // Helper method to convert MonHoc to MonHocDto
    private MonHocDto convertToDto(MonHoc monHoc) {
        MonHocDto dto = new MonHocDto();
        dto.setMaMh(monHoc.getMaMh());
        dto.setTenMh(monHoc.getTenMh());
        dto.setSoTiet(monHoc.getSoTiet());
        return dto;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllMonHocWithoutPaging() {
        try {
            List<MonHoc> monHocs = monHocRepository.findAll(Sort.by("maMh").ascending());

            List<MonHocDto> dtos = monHocs.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách môn học: " + e.getMessage());
        }
    }

    // Helper method to validate sort fields
    private boolean isValidSortField(String field) {
        return Arrays.asList("maMh", "tenMh", "soTiet").contains(field);
    }


    // 2. LẤY DANH SÁCH MÔN HỌC CỦA GIẢNG VIÊN
    @GetMapping("/danh-sach-mon-hoc-theo-giao-vien")
    public ResponseEntity<List<MonHocGiangVienDTO>> getMonHocByGiaoVien(
            @RequestParam String maGv,
            @RequestParam Integer hocKy,
            @RequestParam Integer namHoc
    ) {
        List<MonHocGiangVienDTO> dsMonHoc = monHocService.getSubjectsByTeacher(maGv, hocKy, namHoc);
        return ResponseEntity.ok(dsMonHoc);
    }

    // 3. LẤY DANH SÁCH NHÓM MÔN HỌC
    @GetMapping("/danh-sach-nhom-mon-hoc")
    public ResponseEntity<List<NhomMonHocDTO>> getSubjectGroups(
            @RequestParam String teacherId,
            @RequestParam String subjectId,
            @RequestParam Integer semester,
            @RequestParam Integer year) {
        List<NhomMonHocDTO> groups = monHocService.getSubjectGroups(teacherId, subjectId, semester, year);
        return ResponseEntity.ok(groups);
    }

    // 4. LẤY DANH SÁCH MÔN HỌC CỦA SINH VIÊN
    @GetMapping("/danh-sach-mon-hoc-cua-sinh-vien")
    public ResponseEntity<List<MonHocSinhVienDto>> getMonHocCuaSinhVien(@RequestParam String maSv) {
        List<MonHocSinhVienDto> result = monHocService.getMonHocCuaSinhVien(maSv);
        return ResponseEntity.ok(result);
    }
}