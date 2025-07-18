package vn.diemdanh.hethong.controller.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.diemdanh.hethong.dto.admin.*;
import vn.diemdanh.hethong.dto.sinhvien.SinhVienExcelDto;
import vn.diemdanh.hethong.entity.Admin;
import vn.diemdanh.hethong.helper.ExcelExporter;
import vn.diemdanh.hethong.helper.csvImport;
import vn.diemdanh.hethong.helper.excel_Import;
import vn.diemdanh.hethong.service.AdminService;

import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Import dữ liệu sinh viên từ file Excel hoặc CSV
    @PostMapping(value = "/importExcel-SinhVien", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importExcel(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            List<SinhVienExcelDto> sinhvienList = new ArrayList<>();
            if (fileName != null) {
                if (fileName.endsWith(".csv")) {
                    sinhvienList = csvImport.csvImportFile(file.getInputStream());
                } else if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
                    sinhvienList = excel_Import.excelImportFile(file.getInputStream());
                } else {
                    return ResponseEntity.badRequest().body("Định dạng file không hỗ trợ");
                }
            } else {
                return ResponseEntity.badRequest().body("File không hợp lệ");
            }

            adminService.saveImportData(sinhvienList);
            return ResponseEntity.ok("Import thành công");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Lấy danh sách admin có phân trang và sắp xếp
    @GetMapping
    public ResponseEntity<Page<AdminDto>> getAllAdmins(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AdminDto> admins = adminService.getAllAdmins(pageable);
        return ResponseEntity.ok(admins);
    }

    // Lấy admin theo id
    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getAdminById(@PathVariable Integer id) {
        return adminService.getAdminDtoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Tạo mới admin
    @PostMapping
    public ResponseEntity<?> createAdmin(@Valid @RequestBody CreateAdminRequest request) {
        try {
            AdminDto createdAdmin = adminService.createAdmin(request);
            return ResponseEntity.ok(createdAdmin);
        } catch (Exception e) {
            return createErrorResponse(e.getMessage());
        }
    }

    // Cập nhật admin
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable Integer id,
                                         @Valid @RequestBody UpdateAdminRequest request) {
        try {
            AdminDto updatedAdmin = adminService.updateAdmin(id, request);
            return ResponseEntity.ok(updatedAdmin);
        } catch (Exception e) {
            return createErrorResponse(e.getMessage());
        }
    }

    // Xóa admin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Integer id) {
        try {
            adminService.deleteAdmin(id);
            Map<String, String> response = Collections.singletonMap("message", "Admin đã được xóa thành công");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return createErrorResponse(e.getMessage());
        }
    }

    // Xuất file Excel danh sách sinh viên
    @GetMapping("/export-sinhvien")
    public ResponseEntity<InputStreamResource> exportSinhVien() {
        try {
            List<SinhVienExcelDto> sinhViens = adminService.getAllSinhVienForExcel();
            ByteArrayInputStream in = ExcelExporter.sinhVienToExcel(sinhViens);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=sinh_vien.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(new InputStreamResource(in));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Lấy tất cả admin không phân trang
    @GetMapping("/all")
    public ResponseEntity<?> getAllSinhVienNoPagination() {
        try {
            List<Admin> admins = adminService.findAll();
            List<AdminDto> dtos = admins.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách sinh viên: " + e.getMessage());
        }
    }

    // Chuyển đổi entity Admin sang AdminDto
    private AdminDto convertToDto(Admin admin) {
        if (admin == null) {
            return null; // Hoặc có thể ném ngoại lệ tùy yêu cầu
        }
        AdminDto dto = new AdminDto();
        dto.setId(admin.getId());
        dto.setUsername(admin.getUsername());
        dto.setEmail(admin.getEmail());
        dto.setFullName(admin.getFullName());
        dto.setRole(admin.getRole());
        dto.setAvatar(admin.getAvatar());
        dto.setCreatedAt(admin.getCreatedAt());
        dto.setUpdatedAt(admin.getUpdatedAt());
        return dto;
    }

    // Tạo ResponseEntity lỗi chuẩn
    private ResponseEntity<Map<String, String>> createErrorResponse(String message) {
        Map<String, String> error = Collections.singletonMap("message", message);
        return ResponseEntity.badRequest().body(error);
    }
}
