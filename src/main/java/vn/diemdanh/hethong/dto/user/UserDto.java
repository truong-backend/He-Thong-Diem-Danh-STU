package vn.diemdanh.hethong.dto.user;

import lombok.Data;
import java.time.Instant;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String password; // Added for create/update operations
    private String role;
    private String fullName; // Tên sinh viên hoặc giáo viên
    private String userType; // SINH_VIEN hoặc GIAO_VIEN
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant emailVerifiedAt;
}