package vn.diemdanh.hethong.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.diemdanh.hethong.dto.monhoc.listMonHocSV.DiemDanhDto;
import vn.diemdanh.hethong.dto.thucong.DiemDanhRequest;
import vn.diemdanh.hethong.repository.DiemDanhRepository;
import vn.diemdanh.hethong.repository.TkbRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiemDanhService {

    @Autowired
    private DiemDanhRepository diemDanhRepository;

    @Autowired
    private TkbRepository tkbRepository;


    // Lấy danh sách học kỳ
    public List<String> getHocKyList() {
        return tkbRepository.findAll().stream()
                .map(tkb -> tkb.getMaGd().getHocKy().toString())
                .distinct()
                .collect(Collectors.toList());
    }
    // 6. ĐIỂM DANH THỦ CÔNG
    @Transactional
    public void markAttendanceManual(DiemDanhRequest request) {
        try {
            diemDanhRepository.markAttendanceManual(
                    request.getMaTkb(),
                    request.getMaSv(),
                    request.getNgayHoc(),
                    request.getGhiChu()
            );
        } catch (Exception e) {

            throw new RuntimeException("Không thể điểm danh thủ công", e);
        }
    }
    // lấy danh sách điểm danh của sinh vien
    @Transactional

    public List<DiemDanhDto> getDiemDanhBySinhVienVaMonHoc(String maSv, String maMh) {
        List<Object[]> results = diemDanhRepository.findDiemDanhByMaSvAndMaMh(maSv, maMh);
        return results.stream()
                .map(r -> DiemDanhDto.builder()
                        .maDd((Integer) r[0])
                        .maMh((String) r[1])
                        .tenMh((String) r[2])
                        .ngayHoc((Date) r[3])
                        .diemDanh1(((Timestamp) r[4]).toLocalDateTime())
                        .diemDanh2(((Timestamp) r[4]).toLocalDateTime())
                        .ghiChu((String) r[6])
                        .build()
                )
                .collect(Collectors.toList());
    }
}
