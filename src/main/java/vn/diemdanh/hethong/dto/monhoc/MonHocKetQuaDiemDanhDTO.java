package vn.diemdanh.hethong.dto.monhoc;

import lombok.*;
import lombok.experimental.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonHocKetQuaDiemDanhDTO {
    private Integer maGd;
    private String maMh;
    private String tenMh;
    private String phongHoc;
    private Integer nmh;
}
