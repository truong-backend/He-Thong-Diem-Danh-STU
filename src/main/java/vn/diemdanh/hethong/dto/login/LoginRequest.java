package vn.diemdanh.hethong.dto.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements Serializable {
        @NotNull
        @Size(max = 191)
        @NotEmpty
        @NotBlank
        private String email;

        @NotNull
        @Size(max = 191)
        private String password;
}
