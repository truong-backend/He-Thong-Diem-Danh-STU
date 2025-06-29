package vn.diemdanh.hethong.dto.giaovien;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiaoVienInfo {
    private String maGv;
    private String tenGv;
}
