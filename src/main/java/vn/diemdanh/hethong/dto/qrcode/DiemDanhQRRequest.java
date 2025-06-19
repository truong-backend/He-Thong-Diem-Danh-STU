package vn.diemdanh.hethong.dto.qrcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiemDanhQRRequest {
    private Long qrId;
    private String maSv;
}