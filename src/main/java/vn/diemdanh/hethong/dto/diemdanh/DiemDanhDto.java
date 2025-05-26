package vn.diemdanh.hethong.dto.diemdanh;

import lombok.Data;
import java.time.Instant;
import java.time.LocalDate;

@Data
public class DiemDanhDto {
    private Long id;
    private Long maTkb;
    private String maSv;
    private String tenSv;
    private String maGv;
    private String tenGv;
    private String maMh;
    private String tenMh;
    private LocalDate ngayHoc;
    private String phongHoc;
    private Integer stBd;
    private Integer stKt;
    private Instant diemDanh1;
    private Instant diemDanh2;
    private String ghiChu;
} 