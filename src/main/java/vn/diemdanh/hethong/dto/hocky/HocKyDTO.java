package vn.diemdanh.hethong.dto.hocky;

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
public class HocKyDTO {
    private Integer hocKy;
    private String hocKyDisplay;
    private Integer namHoc;
}
