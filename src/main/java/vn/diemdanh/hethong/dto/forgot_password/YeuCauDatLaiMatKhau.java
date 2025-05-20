package vn.diemdanh.hethong.dto.forgot_password;

import jakarta.validation.constraints.*;

public class DatLaiMatKhau {
    @NotNull
    @NotEmpty
    @NotBlank
    @Email
    private String email;
}
