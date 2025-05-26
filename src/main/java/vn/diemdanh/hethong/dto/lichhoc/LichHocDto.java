package vn.diemdanh.hethong.dto.lichhoc;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LichHocDto {
    private String maSv;
    private String tenSv;
    private Long maGd;
    private String maGv;
    private String tenGv;
    private String maMh;
    private String tenMh;
    private Integer nmh;
    private String phongHoc;
    private LocalDate ngayBd;
    private LocalDate ngayKt;
    private Integer stBd;
    private Integer stKt;
    private Integer hocKy;
} 