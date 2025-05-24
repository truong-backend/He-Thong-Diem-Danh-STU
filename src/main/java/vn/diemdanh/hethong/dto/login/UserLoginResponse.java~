package vn.diemdanh.hethong.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";  // Giá trị mặc định là "Bearer"
    private Long userId;     // Changed from Integer to Long to match User entity ID type
    private String username;
    private String email;    // Added email field
    private String role;

    // Constructor with token
    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    // Constructor with all details
    public LoginResponse(String accessToken, Long userId, String username, String email, String role) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
        // tokenType is already initialized with "Bearer"
    }
}