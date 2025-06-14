package vn.diemdanh.hethong.dto.sinhvien;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SinhVienDiemDanhDTO {
    private String maSv;
    private String tenSv;
    private String email;
    private String tenLop;
    private String tenKhoa;
    private LocalDateTime diemDanh1;
    private LocalDateTime diemDanh2;
    private String ghiChu;
    private String trangThaiDiemDanh;
    private LocalDate ngayHoc;
    private String phongHoc;
    private String caHoc;
}