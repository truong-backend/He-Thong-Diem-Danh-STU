package vn.diemdanh.hethong.dto.forgot_password;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YeuCauDatLaiMatKhau {
    @NotNull
    @NotEmpty
    @NotBlank
    @Email
    private String email;
}
