package vn.diemdanh.hethong.dto.user_managerment;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class CreateAdminRequest {
    @NotBlank(message = "Username không được để trống")
    @Size(max = 50, message = "Username không được vượt quá 50 ký tự")
    private String username;

    @NotBlank(message = "Password không được để trống")
    @Size(min = 6, message = "Password phải có ít nhất 6 ký tự")
    private String password;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(max = 200, message = "Email không được vượt quá 200 ký tự")
    private String email;

    @Size(max = 200, message = "Họ tên không được vượt quá 200 ký tự")
    private String fullName;

    @NotBlank(message = "Role không được để trống")
    private String role;
}