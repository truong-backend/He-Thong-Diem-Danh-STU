package vn.diemdanh.hethong.dto.diemdanh;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiemDanhQRSinhVienRequest {
    private String maSv;
    private int maTkb;
    private LocalDate ngayHoc;
}
