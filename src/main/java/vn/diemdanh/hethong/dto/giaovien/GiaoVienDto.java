package vn.diemdanh.hethong.dto.giaovien;

import lombok.Data;
import java.time.LocalDate;

@Data
public class GiaoVienDto {
    private String maGv;
    private String tenGv;
    private LocalDate ngaySinh;
    private Byte phai;
    private String diaChi;
    private String sdt;
    private String email;
    private String avatar;
    private boolean hasAccount;
}