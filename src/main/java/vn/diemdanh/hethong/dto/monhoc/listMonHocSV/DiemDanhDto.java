package vn.diemdanh.hethong.dto.monhoc.listMonHocSV;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiemDanhDto {
    private Integer maDd;
    private String maMh;
    private String tenMh;
    private Date ngayHoc;
    private LocalDateTime diemDanh1;
    private LocalDateTime diemDanh2;
    private String ghiChu;
}
