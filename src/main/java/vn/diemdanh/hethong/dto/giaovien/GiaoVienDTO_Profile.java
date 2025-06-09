package vn.diemdanh.hethong.dto.giaovien;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiaoVienDTO_Profile {
    private String maGv;
    private String tenGv;
    private LocalDate ngaySinh;
    private Byte phai;
    @NotNull(message = "Địa chỉ không được để trống")
    @Size(min = 3, message = "Địa chỉ quá ngắn")
    private String diaChi;
    @NotNull(message = "SĐT không được để trống")
    @Size(min = 9, max = 15, message = "Số điện thoại không hợp lệ")
    private String sdt;
    private String email;
    private String avatar;

}
