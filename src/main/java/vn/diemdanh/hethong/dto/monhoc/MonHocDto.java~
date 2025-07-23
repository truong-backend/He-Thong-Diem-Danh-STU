package vn.diemdanh.hethong.dto.monhoc;

import jakarta.validation.constraints.*;  // hoặc javax.validation.constraints.*
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonHocDto {

    @NotBlank(message = "Mã môn học không được để trống")
    @Size(max = 20, message = "Mã môn học không được vượt quá 20 ký tự")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Mã môn học chỉ chứa chữ và số")
    private String maMh;

    @NotBlank(message = "Tên môn học không được để trống")
    @Size(max = 50, message = "Tên môn học không được vượt quá 50 ký tự")
    private String tenMh;

    @NotNull(message = "Số tiết không được để trống")
    @Min(value = 1, message = "Số tiết phải lớn hơn 0")
    private Integer soTiet;
}
