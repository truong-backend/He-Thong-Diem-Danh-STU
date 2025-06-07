package vn.diemdanh.hethong.dto.sinhvien;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SinhVienExcelDto {
    private String maSv;
    private String tenSv;
    private LocalDate ngaySinh;
    private Byte phai;
    private String diaChi;
    private String sdt;
    private String email;
    private String maLop;
    private String avatar;
}
