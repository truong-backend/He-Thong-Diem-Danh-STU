package vn.diemdanh.hethong.dto.monhoc;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

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
