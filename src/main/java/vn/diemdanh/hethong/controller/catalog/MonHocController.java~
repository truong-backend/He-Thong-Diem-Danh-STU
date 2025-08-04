package vn.diemdanh.hethong.controller.catalog;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.diemdanh.MonHocSinhVienDto;
import vn.diemdanh.hethong.dto.lichhoc.LichHocTheoThuDto;
import vn.diemdanh.hethong.dto.monhoc.MonHocGiangVienDTO;
import vn.diemdanh.hethong.dto.monhoc.MonHocDto;
import vn.diemdanh.hethong.dto.monhoc.MonHocKetQuaDiemDanhDTO;
import vn.diemdanh.hethong.dto.monhoc.NhomMonHocDTO;
import vn.diemdanh.hethong.dto.tkb.ThoiKhoaBieuDTO;
import vn.diemdanh.hethong.entity.MonHoc;
import vn.diemdanh.hethong.repository.MonHocRepository;
import vn.diemdanh.hethong.service.MonHocService;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/monhoc")
public class MonHocController {

    @Autowired
    private MonHocRepository monHocRepository;

    @Autowired
    private MonHocService monHocService;

    // Lấy môn học cho kết quả điểm danh
    @GetMapping("/monHocKetQuaDiemDanh")
    public ResponseEntity<?> getMonHocForDiemDanh(){
        List<MonHocKetQuaDiemDanhDTO> result = monHocService.getMonHocKetQuaDiemDanh();
        return ResponseEntity.ok(result);
    }

    // ========== CREATE ==========

    @PostMapping
    public ResponseEntity<?> createMonHoc(@Valid @RequestBody MonHocDto request) {
        try {
            if (monHocRepository.findById(request.getMaMh()).isPresent()) {
                return ResponseEntity.badRequest().body("Mã môn học đã tồn tại");
            }
            if (request.getSoTiet() <= 0) {
                return ResponseEntity.badRequest().body("Số tiết phải lớn hơn 0");
            }

            MonHoc monHoc = new MonHoc();
            monHoc.setMaMh(request.getMaMh());
            monHoc.setTenMh(request.getTenMh());
            monHoc.setSoTiet(request.getSoTiet());

            monHoc = monHocRepository.save(monHoc);
            return ResponseEntity.ok(convertToDto(monHoc));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm môn học: " + e.getMessage());
        }
    }

    // ========== READ ==========

    @GetMapping
    public ResponseEntity<?> getMonHocList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "maMh") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        try {
            if (!isValidSortField(sortBy)) {
                return ResponseEntity.badRequest().body("Trường sắp xếp không hợp lệ");
            }
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
            Page<MonHoc> monHocs = monHocRepository.findAll(pageable);
            Page<MonHocDto> dtos = monHocs.map(this::convertToDto);
            return ResponseEntity.ok(dtos);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách môn học: " + e.getMessage());
        }
    }

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

    @GetMapping("/all")
    public ResponseEntity<?> getAllMonHocWithoutPaging() {
        try {
            List<MonHocDto> dtos = monHocRepository.findAll(Sort.by("maMh"))
                    .stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách môn học: " + e.getMessage());
        }
    }

    // ========== UPDATE ==========

    @PutMapping("/{maMh}")
    public ResponseEntity<?> updateMonHoc(@PathVariable String maMh, @Valid @RequestBody MonHocDto request) {
        try {
            MonHoc monHoc = monHocRepository.findById(maMh)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));

            if (request.getSoTiet() <= 0) {
                return ResponseEntity.badRequest().body("Số tiết phải lớn hơn 0");
            }

            monHoc.setTenMh(request.getTenMh());
            monHoc.setSoTiet(request.getSoTiet());

            monHoc = monHocRepository.save(monHoc);
            return ResponseEntity.ok(convertToDto(monHoc));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật môn học: " + e.getMessage());
        }
    }

    // ========== DELETE ==========

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

    // ========== CUSTOM READ ==========

    @GetMapping("/danh-sach-mon-hoc-theo-giao-vien")
    public ResponseEntity<List<MonHocGiangVienDTO>> getMonHocByGiaoVien(
            @RequestParam String maGv,
            @RequestParam Integer hocKy,
            @RequestParam Integer namHoc) {
        return ResponseEntity.ok(monHocService.getSubjectsByTeacher(maGv, hocKy, namHoc));
    }

    @GetMapping("/danh-sach-nhom-mon-hoc")
    public ResponseEntity<List<NhomMonHocDTO>> getSubjectGroups(
            @RequestParam String teacherId,
            @RequestParam String subjectId,
            @RequestParam Integer semester,
            @RequestParam Integer year) {
        return ResponseEntity.ok(monHocService.getSubjectGroups(teacherId, subjectId, semester, year));
    }

    @GetMapping("/danh-sach-mon-hoc-cua-sinh-vien")
    public ResponseEntity<List<MonHocSinhVienDto>> getMonHocCuaSinhVien(@RequestParam String maSv) {
        return ResponseEntity.ok(monHocService.getMonHocCuaSinhVien(maSv));
    }

    @GetMapping("/danh-sach-mon-hoc-theo-thu/{thu}")
    public ResponseEntity<List<LichHocTheoThuDto>> getLichHocTheoThu(@PathVariable int thu) {
        return ResponseEntity.ok(monHocService.getLichHocTheoThu(thu));
    }

    @GetMapping("/danh-sach-mon-hoc/{maSv}")
    public List<ThoiKhoaBieuDTO> getThoiKhoaBieu(@PathVariable String maSv) {
        return monHocService.getThoiKhoaBieuByMaSv(maSv);
    }

    @GetMapping("/mon-hoc-theo-hoc-ky-nam")
    public ResponseEntity<List<MonHocDto>> getMonHocByHocKyAndNam(
            @RequestParam Integer hocKy,
            @RequestParam Integer namHoc) {
        try {
            return ResponseEntity.ok(monHocService.getMonHocByHocKyAndNam(hocKy, namHoc));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ========== UTILS ==========

    private MonHocDto convertToDto(MonHoc monHoc) {
        MonHocDto dto = new MonHocDto();
        dto.setMaMh(monHoc.getMaMh());
        dto.setTenMh(monHoc.getTenMh());
        dto.setSoTiet(monHoc.getSoTiet());
        return dto;
    }

    private boolean isValidSortField(String field) {
        return Arrays.asList("maMh", "tenMh", "soTiet").contains(field);
    }
}
