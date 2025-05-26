package vn.diemdanh.hethong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String email;
    private String fullName;
    private Object id; // Can be Integer for Admin or Long for User
    private String role;
} 