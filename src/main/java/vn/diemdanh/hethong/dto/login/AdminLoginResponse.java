package vn.diemdanh.hethong.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";  // Giá trị mặc định là "Bearer"
    private Integer adminId;     // Admin ID type is Integer
    private String username;
    private String email;
    private String fullName;
    private String role;

    // Constructor with token
    public AdminLoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    // Constructor with all details
    public AdminLoginResponse(String accessToken, Integer adminId, String username, String email, String fullName, String role) {
        this.accessToken = accessToken;
        this.adminId = adminId;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        // tokenType is already initialized with "Bearer"
    }
}