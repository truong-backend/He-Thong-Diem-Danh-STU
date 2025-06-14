package vn.diemdanh.hethong.dto.giaovien;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiaoVienDTOExcel {
    private String maGv;
    private String tenGv;
    private LocalDate ngaySinh;
    private Byte phai;
    private String diaChi;
    private String sdt;
    private String email;
    private String avatar;
}
