package vn.diemdanh.hethong.dto.sinhvien;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
public class UpdateSinhVienRequest {
    @NotBlank(message = "Tên sinh viên không được để trống")
    @Size(max = 150, message = "Tên sinh viên không được vượt quá 150 ký tự")
    private String tenSv;

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    private LocalDate ngaySinh;

    @NotNull(message = "Giới tính không được để trống")
    @Min(value = 0, message = "Giới tính không hợp lệ")
    @Max(value = 1, message = "Giới tính không hợp lệ")
    private Byte phai;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(max = 300, message = "Địa chỉ không được vượt quá 300 ký tự")
    private String diaChi;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Số điện thoại không hợp lệ")
    private String sdt;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(max = 50, message = "Email không được vượt quá 50 ký tự")
    private String email;

    @NotBlank(message = "Mã lớp không được để trống")
    private String maLop;
}
