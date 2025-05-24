package vn.diemdanh.hethong.dto.forgot_password;

import jakarta.validation.constraints.*;

public class YeuCauDatLaiMatKhau {
    @NotNull
    @NotEmpty
    @NotBlank
    @Email
    private String email;
}
