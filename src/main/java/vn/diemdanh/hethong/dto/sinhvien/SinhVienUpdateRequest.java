package vn.diemdanh.hethong.dto.sinhvien;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SinhVienUpdateRequest {
    private String diaChi;
    private String sdt;
    private MultipartFile avatarFile;
}
