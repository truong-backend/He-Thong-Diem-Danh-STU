package vn.diemdanh.hethong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.lop.LopDto;
import vn.diemdanh.hethong.entity.Khoa;
import vn.diemdanh.hethong.entity.Lop;
import vn.diemdanh.hethong.repository.user_man_and_login.KhoaRepository;
import vn.diemdanh.hethong.repository.user_man_and_login.LopRepository;

import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lop")
public class LopController {

    @Autowired
    private LopRepository lopRepository;

    @Autowired
    private KhoaRepository khoaRepository;

    // CREATE - Thêm lớp mới
    @PostMapping
    public ResponseEntity<?> createLop(@Valid @RequestBody LopDto request) {
        try {
            // Kiểm tra mã lớp đã tồn tại chưa
            if (lopRepository.findById(request.getMaLop()).isPresent()) {
                return ResponseEntity.badRequest().body("Mã lớp đã tồn tại");
            }

            // Kiểm tra khoa có tồn tại không
            Khoa khoa = khoaRepository.findById(request.getMaKhoa())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa"));

            // Tạo lớp mới
            Lop lop = new Lop();
            lop.setMaLop(request.getMaLop());
            lop.setTenLop(request.getTenLop());
            lop.setMaKhoa(khoa);
            lop.setGvcn(request.getGvcn());
            lop.setSdtGvcn(request.getSdtGvcn());

            // Lưu lớp
            lop = lopRepository.save(lop);

            return ResponseEntity.ok(convertToDto(lop));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm lớp: " + e.getMessage());
        }
    }

    // READ - Lấy danh sách lớp có phân trang và sắp xếp
    @GetMapping
    public ResponseEntity<?> getLopList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "maLop") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String maKhoa
    ) {
        try {
            // Kiểm tra tính hợp lệ của trường sắp xếp
            if (!isValidSortField(sortBy)) {
                return ResponseEntity.badRequest().body("Trường sắp xếp không hợp lệ");
            }

            // Tạo đối tượng Pageable
            Sort.Direction direction = Sort.Direction.fromString(sortDir);
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

            // Lấy danh sách lớp
            Page<Lop> lops;
            if (maKhoa != null && !maKhoa.isEmpty()) {
                // Kiểm tra khoa có tồn tại không
                Khoa khoa = khoaRepository.findById(maKhoa)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa"));
                // Lấy danh sách lớp theo khoa
                lops = lopRepository.findByMaKhoa(khoa, pageable);
            } else {
                lops = lopRepository.findAll(pageable);
            }

            // Chuyển đổi sang DTO
            Page<LopDto> dtos = lops.map(this::convertToDto);

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách lớp: " + e.getMessage());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAlls() {
        try {
            List<Lop> lops = lopRepository.findAll(Sort.by("maLop").ascending());

            List<LopDto> dtos = lops.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách lớp: " + e.getMessage());
        }
    }
    // READ - Lấy thông tin một lớp
    @GetMapping("/{maLop}")
    public ResponseEntity<?> getLop(@PathVariable String maLop) {
        try {
            Lop lop = lopRepository.findById(maLop)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp"));

            return ResponseEntity.ok(convertToDto(lop));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy thông tin lớp: " + e.getMessage());
        }
    }

    // UPDATE - Cập nhật thông tin lớp
    @PutMapping("/{maLop}")
    public ResponseEntity<?> updateLop(
            @PathVariable String maLop,
            @Valid @RequestBody LopDto request
    ) {
        try {
            Lop lop = lopRepository.findById(maLop)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp"));

            // Kiểm tra khoa có tồn tại không nếu có thay đổi mã khoa
            if (!lop.getMaKhoa().getMaKhoa().equals(request.getMaKhoa())) {
                Khoa khoa = khoaRepository.findById(request.getMaKhoa())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa"));
                lop.setMaKhoa(khoa);
            }

            // Cập nhật thông tin
            lop.setTenLop(request.getTenLop());
            lop.setGvcn(request.getGvcn());
            lop.setSdtGvcn(request.getSdtGvcn());

            // Lưu thay đổi
            lop = lopRepository.save(lop);

            return ResponseEntity.ok(convertToDto(lop));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật lớp: " + e.getMessage());
        }
    }

    // DELETE - Xóa lớp
    @DeleteMapping("/{maLop}")
    public ResponseEntity<?> deleteLop(@PathVariable String maLop) {
        try {
            Lop lop = lopRepository.findById(maLop)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp"));

            lopRepository.delete(lop);
            return ResponseEntity.ok("Xóa lớp thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi xóa lớp: " + e.getMessage());
        }
    }

    // Helper method to convert Lop to LopDto
    private LopDto convertToDto(Lop lop) {
        LopDto dto = new LopDto();
        dto.setMaLop(lop.getMaLop());
        dto.setTenLop(lop.getTenLop());
        dto.setMaKhoa(lop.getMaKhoa().getMaKhoa());
        dto.setTenKhoa(lop.getMaKhoa().getTenKhoa());
        dto.setGvcn(lop.getGvcn());
        dto.setSdtGvcn(lop.getSdtGvcn());
        return dto;
    }

    // Helper method to validate sort fields
    private boolean isValidSortField(String field) {
        return Arrays.asList("maLop", "tenLop", "gvcn", "sdtGvcn").contains(field);
    }
}