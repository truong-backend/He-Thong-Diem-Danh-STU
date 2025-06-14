package vn.diemdanh.hethong.dto.giaovien;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonHocGiangVienDTO {
    private String maMh;
    private String tenMh;
    private Integer hocKy;
    private Integer namHoc;
}