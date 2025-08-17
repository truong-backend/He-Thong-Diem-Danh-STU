package vn.diemdanh.hethong.controller.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // Lấy admin theo id

    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getAdminById(@PathVariable Integer id) {
        return adminService.getAdminDtoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // Tạo mới admin
    @PreAuthorize("hasRole('admin')")
    @PostMapping
    public ResponseEntity<?> taoAdmin(@Valid @RequestBody CreateAdminRequest request) {
        try {
            AdminDto createdAdmin = adminService.createAdmin(request);
            return ResponseEntity.ok(createdAdmin);
        } catch (Exception e) {
            return taoPhanHoiLoi(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('admin')")
    // Cập nhật admin
    @PutMapping("/{id}")
    public ResponseEntity<?> capNhatAdmin(@PathVariable Integer id,
                                          @Valid @RequestBody UpdateAdminRequest request) {
        try {
            AdminDto updatedAdmin = adminService.updateAdmin(id, request);
            return ResponseEntity.ok(updatedAdmin);
        } catch (Exception e) {
            return taoPhanHoiLoi(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('admin')")
    // Xóa admin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoaAdmin(@PathVariable Integer id) {
        try {
            adminService.deleteAdmin(id);
            Map<String, String> response = Collections.singletonMap("message", "Admin đã được xóa thành công");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return taoPhanHoiLoi(e.getMessage());
        }
    }


    // Tạo ResponseEntity lỗi chuẩn
    private ResponseEntity<Map<String, String>> taoPhanHoiLoi(String message) {
        Map<String, String> error = Collections.singletonMap("message", message);
        return ResponseEntity.badRequest().body(error);
    }
    ///Phân trang trên BE
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/danh-sach-quan-tri-vien")
    public ResponseEntity<Page<AdminDto>> layDanhSachQuanTriVien(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        Sort.Direction direction = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));

        Page<AdminDto> adminPage = adminService.layDanhSachQuanTriVien(search, pageable);

        return ResponseEntity.ok(adminPage);
    }

}
