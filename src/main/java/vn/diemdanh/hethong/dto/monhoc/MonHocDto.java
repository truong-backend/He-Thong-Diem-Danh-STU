package vn.diemdanh.hethong.dto.monhoc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonHocDto {
    private String maMh;
    private String tenMh;
    private Integer soTiet;
} 