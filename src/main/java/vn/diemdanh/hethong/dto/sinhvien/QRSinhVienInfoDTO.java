package vn.diemdanh.hethong.dto.sinhvien;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QRSinhVienInfoDTO {
    private String maSv;
    private String tenSv;
    private String tenLop;
}

