package vn.diemdanh.hethong.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.user.UserDto;
import vn.diemdanh.hethong.entity.User;
import vn.diemdanh.hethong.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired private UserService userService;
    // Lấy user theo id
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(convertToDto(user));
    }
    // Lấy user theo email
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        User user = userService.getCurrentUser(email);
        return ResponseEntity.ok(convertToDto(user));
    }

    // Tạo mới user
    @PreAuthorize("hasRole('admin')")
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User user = userService.createUser(userDto);
        return new ResponseEntity<>(convertToDto(user), HttpStatus.CREATED);
    }

    // Cập nhật user theo id
    @PreAuthorize("hasRole('admin')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(convertToDto(updatedUser));
    }

    // Xóa user theo id
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    // Chuyển đổi entity User sang UserDto
    @PreAuthorize("hasRole('admin')")
    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());

        // Phân biệt loại user và fullname theo quan hệ
        if (user.getMaSv() != null) {
            dto.setUserType("SINH_VIEN");
            dto.setFullName(user.getMaSv().getTenSv());
        } else if (user.getMaGv() != null) {
            dto.setUserType("GIAO_VIEN");
            dto.setFullName(user.getMaGv().getTenGv());
        }

        dto.setActive(true); // Cần thêm trường active trong entity User nếu chưa có
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setEmailVerifiedAt(user.getEmailVerifiedAt());

        return dto;
    }
}
