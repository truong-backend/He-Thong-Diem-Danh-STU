package vn.diemdanh.hethong.dto.qrcode;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class QrcodeDto {
    private Long maTkb;
    private String maGv;
    private String tenGv;
    private String maMh;
    private String tenMh;
    private String phongHoc;
    private Integer stBd;
    private Integer stKt;
    private LocalDateTime thoiGianTao;
    private String qrCodeData;
    private Integer thoiGianHieuLuc; // Thời gian hiệu lực tính bằng phút
} 