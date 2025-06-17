package vn.diemdanh.hethong.dto.diemdanh.ListDiemDanhMonHoc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonHocSinhVienDto {
    private String maMh;
    private String tenMh;
    private Integer soTiet;
    private Integer nmh;
    private Integer hocKy;
    private String phongHoc;
    private Date ngayBd;
    private Date ngayKt;
}
