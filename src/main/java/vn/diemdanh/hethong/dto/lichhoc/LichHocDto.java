package vn.diemdanh.hethong.dto.lichhoc;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LichHocDto {
    private String maSv;
    private String tenSv= "";
    private Long maGd;
    private String maGv= "";
    private String tenGv= "";
    private String maMh= "";
    private String tenMh= "";
    private Integer nmh= 0;
    private String phongHoc= "";
    private LocalDate ngayBd= null;
    private LocalDate ngayKt= null;
    private Integer stBd= 0;
    private Integer stKt= 0;
    private Integer hocKy= 0;
} 