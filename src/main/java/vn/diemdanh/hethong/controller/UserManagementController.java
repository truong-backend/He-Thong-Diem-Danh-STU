package vn.diemdanh.hethong.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.user_managerment.AdminDto;
import vn.diemdanh.hethong.dto.user_managerment.CreateAdminRequest;
import vn.diemdanh.hethong.dto.user_managerment.UpdateAdminRequest;
import vn.diemdanh.hethong.service.user_man_and_login.AdminService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
//@PreAuthorize("hasRole('ADMIN')")
public class UserManagementController {

    @Autowired
    private AdminService adminService;
    // QUẢN LÝ ADMIN
    @GetMapping("/admins")
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
    @GetMapping("/admins/{id}")
    public ResponseEntity<AdminDto> getAdminById(@PathVariable Integer id) {
        return adminService.getAdminDtoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/admins")
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
    @PutMapping("/admins/{id}")
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
    @DeleteMapping("/admins/{id}")
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
