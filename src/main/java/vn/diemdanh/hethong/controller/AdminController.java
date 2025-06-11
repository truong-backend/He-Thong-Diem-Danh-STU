package vn.diemdanh.hethong.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.diemdanh.hethong.dto.sinhvien.SinhVienExcelDto;
import vn.diemdanh.hethong.dto.admin.AdminDto;
import vn.diemdanh.hethong.dto.admin.CreateAdminRequest;
import vn.diemdanh.hethong.dto.admin.UpdateAdminRequest;
import vn.diemdanh.hethong.helper.csvImport;
import vn.diemdanh.hethong.helper.excel_Import;
import vn.diemdanh.hethong.service.AdminService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
} 