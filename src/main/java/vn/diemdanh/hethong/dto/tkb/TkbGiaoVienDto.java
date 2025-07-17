package vn.diemdanh.hethong.dto.tkb;

import java.time.LocalDate;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TkbGiaoVienDto {
    private String maMH;
    private String tenMon;
    private Integer nmh;
    private LocalDate ngayHoc;
    private Integer tietBd;
    private Integer tietKt;
    private String phong;
    private String cbgd;
    private Integer hocKy;
    private String ghiChu;
}
