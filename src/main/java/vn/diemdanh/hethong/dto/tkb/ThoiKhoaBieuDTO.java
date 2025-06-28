package vn.diemdanh.hethong.dto.tkb;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThoiKhoaBieuDTO {
    private String maMonHoc;
    private String tenMonHoc;
    private Integer nhomMonHoc; // ma_gd là int
    private Integer soTiet;
    private String maLop;
    private Integer thu;
    private Integer tietBD;
    private String phong;
    private String tenGiaoVien;
    private String tuanNgayBatDauKetThuc;
}
