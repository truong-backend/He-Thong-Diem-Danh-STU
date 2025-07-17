package vn.diemdanh.hethong.dto.sinhvien;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SinhVienChuaHocDTO {
    private String maSv;
    private String tenSv;
    private String email;
}
