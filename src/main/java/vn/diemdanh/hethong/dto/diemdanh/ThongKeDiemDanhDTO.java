package vn.diemdanh.hethong.dto.diemdanh;

import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ThongKeDiemDanhDTO {
    private String maSv;
    private String tenSv;
    private String tenLop;
    private Long so_buoi_hoc;
    private Long so_buoi_diem_danh;
    private Long so_buoi_chua_diem_danh;
}
