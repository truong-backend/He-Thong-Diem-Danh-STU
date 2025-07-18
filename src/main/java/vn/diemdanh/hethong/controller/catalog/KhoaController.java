package vn.diemdanh.hethong.controller.catalog;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.khoa.KhoaDto;
import vn.diemdanh.hethong.entity.Khoa;
import vn.diemdanh.hethong.repository.KhoaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/khoa")
public class KhoaController {

    @Autowired
    private KhoaRepository khoaRepository;

    // ============================== CREATE ==============================

    @PostMapping
    public ResponseEntity<?> createKhoa(@Valid @RequestBody KhoaDto request) {
        try {
            if (khoaRepository.existsById(request.getMaKhoa())) {
                return ResponseEntity.badRequest().body("Mã khoa đã tồn tại");
            }

            Khoa khoa = new Khoa();
            khoa.setMaKhoa(request.getMaKhoa());
            khoa.setTenKhoa(request.getTenKhoa());

            Khoa saved = khoaRepository.save(khoa);
            return ResponseEntity.ok(toDto(saved));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm khoa: " + e.getMessage());
        }
    }

    // ============================== READ ==============================

    // Lấy danh sách khoa có phân trang + sắp xếp
    @GetMapping
    public ResponseEntity<?> getKhoaList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "maKhoa") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        try {
            if (!isValidSortField(sortBy)) {
                return ResponseEntity.badRequest().body("Trường sắp xếp không hợp lệ");
            }

            Sort.Direction direction = Sort.Direction.fromString(sortDir);
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

            Page<Khoa> result = khoaRepository.findAll(pageable);
            Page<KhoaDto> dtos = result.map(this::toDto);

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách khoa: " + e.getMessage());
        }
    }

    // Lấy danh sách tất cả khoa (không phân trang)
    @GetMapping("/all")
    public ResponseEntity<?> getAllKhoa() {
        try {
            List<Khoa> khoas = khoaRepository.findAll();
            List<KhoaDto> dtos = khoas.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách khoa: " + e.getMessage());
        }
    }

    // Lấy thông tin chi tiết một khoa
    @GetMapping("/{maKhoa}")
    public ResponseEntity<?> getKhoaById(@PathVariable String maKhoa) {
        try {
            Khoa khoa = khoaRepository.findById(maKhoa)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa"));

            return ResponseEntity.ok(toDto(khoa));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy thông tin khoa: " + e.getMessage());
        }
    }

    // ============================== UPDATE ==============================

    @PutMapping("/{maKhoa}")
    public ResponseEntity<?> updateKhoa(
            @PathVariable String maKhoa,
            @Valid @RequestBody KhoaDto request
    ) {
        try {
            Khoa khoa = khoaRepository.findById(maKhoa)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa"));

            khoa.setTenKhoa(request.getTenKhoa());

            Khoa updated = khoaRepository.save(khoa);
            return ResponseEntity.ok(toDto(updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật khoa: " + e.getMessage());
        }
    }

    // ============================== DELETE ==============================

    @DeleteMapping("/{maKhoa}")
    public ResponseEntity<?> deleteKhoa(@PathVariable String maKhoa) {
        try {
            Khoa khoa = khoaRepository.findById(maKhoa)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa"));

            khoaRepository.delete(khoa);
            return ResponseEntity.ok("Xóa khoa thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi xóa khoa: " + e.getMessage());
        }
    }

    // ============================== PRIVATE METHODS ==============================

    private KhoaDto toDto(Khoa khoa) {
        KhoaDto dto = new KhoaDto();
        dto.setMaKhoa(khoa.getMaKhoa());
        dto.setTenKhoa(khoa.getTenKhoa());
        return dto;
    }

    private boolean isValidSortField(String field) {
        return Arrays.asList("maKhoa", "tenKhoa").contains(field);
    }
}
