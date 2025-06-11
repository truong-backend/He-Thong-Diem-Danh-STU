package vn.diemdanh.hethong.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {
    private Integer id;
    private String username;
    private String email;
    private String fullName;
    private String role;
    private String avatar;
    private Instant createdAt;
    private Instant updatedAt;
}