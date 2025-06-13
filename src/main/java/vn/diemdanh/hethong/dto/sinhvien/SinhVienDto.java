package vn.diemdanh.hethong.dto.sinhvien;

import lombok.Data;
import java.time.LocalDate;

@Data
public class SinhVienDto {
    private String maSv;
    private String tenSv;
    private LocalDate ngaySinh;
    private Byte phai; // 0: Nam, 1: Nữ
    private String diaChi;
    private String sdt;
    private String email;
    private String maLop;
    private String tenLop;
    private String tenKhoa;
    private String avatar;
    private boolean hasAccount; // Kiểm tra có tài khoản hay chưa
}