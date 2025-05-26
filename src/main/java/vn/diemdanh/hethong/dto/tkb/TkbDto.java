package vn.diemdanh.hethong.dto.tkb;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TkbDto {
    private Long id;
    private Long maGd;
    private String maGv;
    private String tenGv;
    private String maMh;
    private String tenMh;
    private LocalDate ngayHoc;
    private String phongHoc;
    private Integer stBd;
    private Integer stKt;
    private String ghiChu;
} 