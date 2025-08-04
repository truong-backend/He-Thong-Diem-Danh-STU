package vn.diemdanh.hethong.dto.diemdanh;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KetQuaDiemDanhSinhVienDTO {
    private LocalDate ngayHoc;
    private Long stBd;
    private Long stKt;
    private Long svSolanDD;
    private Long gvSoLanDD;
    private String trangThai;
}
