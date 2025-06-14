package vn.diemdanh.hethong.dto.thucong;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiemDanhRequest {
    private Integer maTkb;
    private String maSv;
    private LocalDate ngayHoc;
    private String ghiChu;
}
