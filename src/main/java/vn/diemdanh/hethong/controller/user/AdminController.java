package vn.diemdanh.hethong.controller.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.diemdanh.hethong.dto.sinhvien.SinhVienDto;
import vn.diemdanh.hethong.dto.sinhvien.SinhVienExcelDto;
import vn.diemdanh.hethong.dto.admin.AdminDto;
import vn.diemdanh.hethong.dto.admin.CreateAdminRequest;
import vn.diemdanh.hethong.dto.admin.UpdateAdminRequest;
import vn.diemdanh.hethong.entity.Admin;
import vn.diemdanh.hethong.entity.SinhVien;
import vn.diemdanh.hethong.helper.ExcelExporter;
import vn.diemdanh.hethong.helper.csvImport;
import vn.diemdanh.hethong.helper.excel_Import;
import vn.diemdanh.hethong.service.AdminService;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping(value = "/importExcel-SinhVien", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importExcel(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            List<SinhVienExcelDto> sinhvienlist = new ArrayList<>();
            if(fileName.endsWith(".csv")){
                sinhvienlist = csvImport.csvImportFile(file.getInputStream());
            }
            else if(fileName.endsWith(".xls") || fileName.endsWith(".xlsx")){
                sinhvienlist = excel_Import.excelImportFile(file.getInputStream());
            }
            adminService.saveImportData(sinhvienlist);
            return ResponseEntity.ok("Import thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getAdminById(@PathVariable Integer id) {
        return adminService.getAdminDtoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createAdmin(@Valid @RequestBody CreateAdminRequest request) {
        try {
            AdminDto createdAdmin = adminService.createAdmin(request);
            return ResponseEntity.ok(createdAdmin);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable Integer id,
                                         @Valid @RequestBody UpdateAdminRequest request) {
        try {
            AdminDto updatedAdmin = adminService.updateAdmin(id, request);
            return ResponseEntity.ok(updatedAdmin);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Integer id) {
        try {
            adminService.deleteAdmin(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Admin đã được xóa thành công");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/export-sinhvien")
    public ResponseEntity<InputStreamResource> exportSinhVien() {
        try {
            List<SinhVienExcelDto> sinhViens = adminService.getAllSinhVienForExcel();
            ByteArrayInputStream in = ExcelExporter.sinhVienToExcel(sinhViens);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=sinh_vien.xlsx");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(new InputStreamResource(in));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // ✅ API lấy danh sách tài khoản không phân trang
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
    public AdminDto convertToDto(Admin admin) {
        if (admin == null) {
            return null; // Hoặc ném ngoại lệ tùy theo yêu cầu
        }
        AdminDto adminDto = new AdminDto();
        adminDto.setId(admin.getId());
        adminDto.setUsername(admin.getUsername());
        adminDto.setEmail(admin.getEmail());
        adminDto.setFullName(admin.getFullName());
        adminDto.setRole(admin.getRole());
        adminDto.setAvatar(admin.getAvatar());
        adminDto.setCreatedAt(admin.getCreatedAt());
        adminDto.setUpdatedAt(admin.getUpdatedAt());
        return adminDto;
    }
}