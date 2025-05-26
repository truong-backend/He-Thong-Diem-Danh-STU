package vn.diemdanh.hethong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.user_managerment.UserDto;
import vn.diemdanh.hethong.entity.User;
import vn.diemdanh.hethong.service.user_man_and_login.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserDto userDto = convertToDto(user);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        User user = userService.getCurrentUser(email);
        UserDto userDto = convertToDto(user);
        return ResponseEntity.ok(userDto);
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        
        // Set user type and full name based on relationships
        if (user.getMaSv() != null) {
            dto.setUserType("SINH_VIEN");
            dto.setFullName(user.getMaSv().getTenSv());
        } else if (user.getMaGv() != null) {
            dto.setUserType("GIAO_VIEN");
            dto.setFullName(user.getMaGv().getTenGv());
        }

        dto.setActive(true); // You might want to add an 'active' field to User entity
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setEmailVerifiedAt(user.getEmailVerifiedAt());

        return dto;
    }
} 