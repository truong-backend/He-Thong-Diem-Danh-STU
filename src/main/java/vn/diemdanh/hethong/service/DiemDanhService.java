package vn.diemdanh.hethong.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vn.diemdanh.hethong.dto.diemdanh.DiemDanhQRSinhVienRequest;
import vn.diemdanh.hethong.dto.monhoc.listMonHocSV.DiemDanhDto;
import vn.diemdanh.hethong.dto.thucong.DiemDanhRequest;
import vn.diemdanh.hethong.exception.AppException;
import vn.diemdanh.hethong.exception.ErrorCode;
import vn.diemdanh.hethong.repository.DiemDanhRepository;
import vn.diemdanh.hethong.repository.GiaoVienRepository;
import vn.diemdanh.hethong.repository.LichHocRepository;
import vn.diemdanh.hethong.repository.TkbRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
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
    @Autowired
    private GiaoVienRepository giaoVienRepository;
    @Autowired
    LichHocRepository lichHocRepository;

    @Transactional
    public int diemDanhMaQRSinhVien(DiemDanhQRSinhVienRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        String maGv = giaoVienRepository.findMaGvByEmail(email);
        Integer existSinhVien = lichHocRepository.findSinhVienByMaTkb(request.getMaSv(), request.getMaTkb());
        if (existSinhVien == 0 || existSinhVien == null) {
            throw new AppException(ErrorCode.SINHVIEN_NOTEXIST_TKB);
        }

        Object[] countDiemDanh = diemDanhRepository.getSoLanDiemDanh(
                request.getMaTkb(),
                request.getMaSv(),
                request.getNgayHoc()
        );
        Object[] result = (Object[]) countDiemDanh[0];
        int diemDanh1 = ((Number) result[0]).intValue();
        int diemDanh2 = ((Number) result[1]).intValue();

        if (diemDanh1 == 1 && diemDanh2 == 1) {
            throw new AppException(ErrorCode.PASSED_DIEMDANH_ALL);
        }

        if (diemDanh1 == 1 && diemDanh2 == 0) {
            LocalDateTime timeDiemDanhlan1 = diemDanhRepository.getDiemDanhLan1(
                    request.getMaTkb(),
                    request.getMaSv(),
                    request.getNgayHoc()
            );
            if(timeDiemDanhlan1 != null){
                //So sánh thời gian từ lúc điểm danh đến hiện tại bao nhiêu giờ ,phút, giây
                Duration checkTimeDiemDanh = Duration.between(timeDiemDanhlan1, LocalDateTime.now());
                //set thời gian cho phép điểm danh lần 2.
                if(checkTimeDiemDanh.toSeconds() < 30){
                    throw new AppException(ErrorCode.PASSED_DIEMDANH_LAN1);
                }
            }

            int diemdanhlan2 = diemDanhRepository.diemDanhQuetMaQRSinhVienLan2(
                    request.getMaTkb(),
                    request.getMaSv(),
                    request.getNgayHoc()
            );
            if(diemdanhlan2 == 1) return diemdanhlan2;
        }

        return diemDanhRepository.diemDanhQuetMaQRSinhVien(
                request.getMaTkb(),
                request.getMaSv(),
                request.getNgayHoc(),
                maGv
        );
    }

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
