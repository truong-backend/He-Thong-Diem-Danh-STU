package vn.diemdanh.hethong.dto.sinhvien;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddSinhVienRequest {
    private String maSv;
    private Long maGd;
}
