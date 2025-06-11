package vn.diemdanh.hethong.dto.sinhvien;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SinhVienDTOProfile {
    private String maSv;
    private String tenSv;
    private String tenLop;
    private LocalDate ngaySinh;
    private Byte phai;
    private String diaChi;
    private String email;
    private String sdt;
    private String avatar;
}
