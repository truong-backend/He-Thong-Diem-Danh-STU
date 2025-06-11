package vn.diemdanh.hethong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.khoa.KhoaDto;
import vn.diemdanh.hethong.entity.Khoa;
import vn.diemdanh.hethong.repository.KhoaRepository;

import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/khoa")
public class KhoaController {

    @Autowired
    private KhoaRepository khoaRepository;

    // CREATE - Thêm khoa mới
    @PostMapping
    public ResponseEntity<?> createKhoa(@Valid @RequestBody KhoaDto request) {
        try {
            // Kiểm tra mã khoa đã tồn tại chưa
            if (khoaRepository.findById(request.getMaKhoa()).isPresent()) {
                return ResponseEntity.badRequest().body("Mã khoa đã tồn tại");
            }

            // Tạo khoa mới
            Khoa khoa = new Khoa();
            khoa.setMaKhoa(request.getMaKhoa());
            khoa.setTenKhoa(request.getTenKhoa());

            // Lưu khoa
            khoa = khoaRepository.save(khoa);

            return ResponseEntity.ok(convertToDto(khoa));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm khoa: " + e.getMessage());
        }
    }

    // READ - Lấy danh sách khoa có phân trang và sắp xếp
    @GetMapping
    public ResponseEntity<?> getKhoaList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "maKhoa") String sortBy,
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

            // Lấy danh sách khoa
            Page<Khoa> khoas = khoaRepository.findAll(pageable);

            // Chuyển đổi sang DTO
            Page<KhoaDto> dtos = khoas.map(this::convertToDto);

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách khoa: " + e.getMessage());
        }
    }
    // ✅ API lấy danh sách không phân trang
    @GetMapping("/all")
    public ResponseEntity<?> getAllKhoa() {
        try {
            List<Khoa> khoas = khoaRepository.findAll();
            List<KhoaDto> dtos = khoas.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách khoa: " + e.getMessage());
        }
    }
    // READ - Lấy thông tin một khoa
    @GetMapping("/{maKhoa}")
    public ResponseEntity<?> getKhoa(@PathVariable String maKhoa) {
        try {
            Khoa khoa = khoaRepository.findById(maKhoa)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa"));

            return ResponseEntity.ok(convertToDto(khoa));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy thông tin khoa: " + e.getMessage());
        }
    }

    // UPDATE - Cập nhật thông tin khoa
    @PutMapping("/{maKhoa}")
    public ResponseEntity<?> updateKhoa(
            @PathVariable String maKhoa,
            @Valid @RequestBody KhoaDto request
    ) {
        try {
            Khoa khoa = khoaRepository.findById(maKhoa)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa"));

            // Cập nhật thông tin
            khoa.setTenKhoa(request.getTenKhoa());

            // Lưu thay đổi
            khoa = khoaRepository.save(khoa);

            return ResponseEntity.ok(convertToDto(khoa));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật khoa: " + e.getMessage());
        }
    }

    // DELETE - Xóa khoa
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

    // Helper method to convert Khoa to KhoaDto
    private KhoaDto convertToDto(Khoa khoa) {
        KhoaDto dto = new KhoaDto();
        dto.setMaKhoa(khoa.getMaKhoa());
        dto.setTenKhoa(khoa.getTenKhoa());
        return dto;
    }

    // Helper method to validate sort fields
    private boolean isValidSortField(String field) {
        return Arrays.asList("maKhoa", "tenKhoa").contains(field);
    }
}