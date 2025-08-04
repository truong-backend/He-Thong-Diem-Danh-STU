package vn.diemdanh.hethong.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vn.diemdanh.hethong.dto.diemdanh.DiemDanhAdmin;
import vn.diemdanh.hethong.dto.diemdanh.DiemDanhQRSinhVienRequest;
import vn.diemdanh.hethong.dto.diemdanh.KetQuaDiemDanhSinhVienDTO;
import vn.diemdanh.hethong.dto.diemdanh.ThongKeDiemDanhDTO;
import vn.diemdanh.hethong.dto.monhoc.listMonHocSV.DiemDanhDto;
import vn.diemdanh.hethong.dto.thucong.DiemDanhRequest;
import vn.diemdanh.hethong.entity.DiemDanh;
import vn.diemdanh.hethong.entity.DiemDanhLog;
import vn.diemdanh.hethong.exception.AppException;
import vn.diemdanh.hethong.exception.ErrorCode;
import vn.diemdanh.hethong.repository.*;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Autowired
    DiemDanhLogRepository diemDanhLogRepo;
    @Autowired
    private SinhVienRepository sinhVienRepository;

    public List<ThongKeDiemDanhDTO> getKetQuaDiemDanhAllSinhVien(String maMh, Integer Nmh, Integer maGd) {
        List<Object[]> result = diemDanhRepository.thongKeDiemDanh(maMh,Nmh,maGd);
        List<ThongKeDiemDanhDTO> list = new ArrayList<>();
        for (Object[] row : result) {
            ThongKeDiemDanhDTO dto = new ThongKeDiemDanhDTO();
            dto.setMaSv((String) row[0]);
            dto.setTenSv((String) row[1]);
            dto.setTenLop((String) row[2]);
            dto.setSo_buoi_hoc(((Number) row[3]).longValue());
            dto.setSo_buoi_diem_danh(((Number) row[4]).longValue());
            dto.setSo_buoi_chua_diem_danh(((Number) row[5]).longValue());
            list.add(dto);
        }
        return list;

    }

    public List<KetQuaDiemDanhSinhVienDTO> getKetQuaDiemDanhSinhVien(String maMH,Integer Nmh) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        String maSv = sinhVienRepository.findMaSvByEmail(email);
        List<Object[]> getKetQua = diemDanhRepository.getKetQuaDDByMaSVAndMaMonHoc(maSv, maMH,Nmh);
        return getKetQua.stream().map((Object[] row) ->
                {
                    KetQuaDiemDanhSinhVienDTO dto = new KetQuaDiemDanhSinhVienDTO();
                    dto.setNgayHoc(((java.sql.Date) row[0]).toLocalDate());
                    dto.setStBd(((Number) row[1]).longValue());
                    dto.setStKt(((Number) row[2]).longValue());
                    dto.setSvSolanDD(((Number) row[3]).longValue());
                    dto.setGvSoLanDD(((Number) row[4]).longValue());
                    dto.setTrangThai((String) row[5]);
                    return dto;
                }
        ).toList();
    }
    //Quét QR Sinh viên + Thủ công
    @Transactional
    public int diemDanhSinhVien(DiemDanhRequest request) {
        Integer existSinhVien = lichHocRepository.findSinhVienByMaTkb(request.getMaSv(), request.getMaTkb());
        if (existSinhVien == 0 || existSinhVien == null) {
            throw new AppException(ErrorCode.SINHVIEN_NOTEXIST_TKB);
        }
        Object[] countDiemDanh = diemDanhRepository.getLanDiemDanh(
                request.getMaTkb(),
                request.getMaSv(),
                request.getNgayHoc()
        );
        Object[] result = (Object[]) countDiemDanh[0];
        Integer diemDanh1 = ((Number) result[0]).intValue();
        Integer diemDanh2= ((Number) result[1]).intValue();

//        Integer countDiemDanh = diemDanhLogRepo.getSoLanDiemDanh(
//                request.getMaTkb(),
//                request.getMaSv(),
//                request.getNgayHoc()
//        );
        int diemDanhCountCurrent = 0;
        String ghiChu = "";

//        if(diemDanh1 == 1 && diemDanh2 == 1){
//            throw new AppException(ErrorCode.PASSED_DIEMDANH_ALL);
//        }
        if(diemDanh1 == 0){
            diemDanhCountCurrent = diemDanh1 + 1;
            ghiChu = "Điểm danh lần " + diemDanhCountCurrent;
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
                //set thời gian cho phép điểm danh lần tiếp theo.
                if(checkTimeDiemDanh.toSeconds() < 30){
                    throw new AppException(ErrorCode.PASSED_DIEMDANH_LAN1);
                }
            }
            diemDanhCountCurrent = diemDanh1 + 1;
            ghiChu = "Điểm danh lần " + diemDanhCountCurrent;
        }else if (diemDanh1 == 1 && diemDanh2 == 1) {
            throw new AppException(ErrorCode.PASSED_DIEMDANH_ALL);
        }

        System.out.println("maTkb: " + request.getMaTkb());
        System.out.println("maSv: " + request.getMaSv());
        System.out.println("ngayHoc: " + request.getNgayHoc());

        int resultDiemDanh = diemDanhRepository.markAttendanceManual(
                request.getMaTkb(),
                request.getMaSv(),
                request.getNgayHoc(),
                ghiChu
        );
        if (resultDiemDanh >= 1) {
            insertDiemDanhLog(request, diemDanhCountCurrent);
            return resultDiemDanh;
        }
        return 0;
    }
    //Ghi thông tin điểm danh vào log
    private void insertDiemDanhLog(DiemDanhRequest req,int soLan){
        List<DiemDanh> existDiemDanh = diemDanhRepository.findDiemDanhExist(
                req.getMaTkb(), req.getMaSv(), req.getNgayHoc()
        );
        if(existDiemDanh != null && existDiemDanh.size() > 0){
            DiemDanh diemDanhGet = existDiemDanh.get(0);

            DiemDanhLog diemDanhLog = new DiemDanhLog();
            diemDanhLog.setMaDd(diemDanhGet);
            diemDanhLog.setLanDiemDanh(soLan);
            diemDanhLog.setThoiGianDiemDanh(Instant.now());
            diemDanhLogRepo.save(diemDanhLog);
        }
    }

    // Xóa điểm danh
    public boolean huyDiemDanh(String maSv, int maTkb, LocalDate ngayHoc,String ghiChu) {
        int deletedCount = diemDanhRepository.deleteDiemDanhByMaSvAndMaTkbAndNgayHoc(maSv, maTkb, ngayHoc,ghiChu);
        return deletedCount > 0;
    }

    // Lấy danh sách học kỳ
    public List<String> getHocKyList() {
        return tkbRepository.findAll().stream()
                .map(tkb -> tkb.getMaGd().getHocKy().toString())
                .distinct()
                .collect(Collectors.toList());
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
                        .ghiChu((String) r[6])
                        .build()
                )
                .collect(Collectors.toList());
    }

    //danh sách điểm danh cho theo hoc ky va nam hoc trang quan trị vien
    public List<DiemDanhAdmin> getAttendanceReportByHocKyAndNam(Integer hocKy, Integer namHoc) {
        List<Object[]> results = diemDanhRepository.findAttendanceReportByHocKyAndNam(hocKy, namHoc);
        return results.stream()
                .map(row -> new DiemDanhAdmin(
                        (String) row[0],
                        (String) row[1],
                        (String) row[2],
                        ((Number) row[3]).longValue(),
                        ((Number) row[4]).longValue(),
                        ((Number) row[5]).longValue()
                ))
                .collect(Collectors.toList());
    }
    //lay danh sach diem danh theo giao vien cua mon hoc do trong hoc ky do cua nam do
    public List<DiemDanhAdmin> getAttendanceReportByAllParams(Integer hocKy, Integer namHoc, String maMh, String maGv) {
        List<Object[]> results = diemDanhRepository.findAttendanceReportByAllParams(hocKy, namHoc, maMh, maGv);
        return results.stream()
                .map(row -> new DiemDanhAdmin(
                        (String) row[0],
                        (String) row[1],
                        (String) row[2],
                        ((Number) row[3]).longValue(),
                        ((Number) row[4]).longValue(),
                        ((Number) row[5]).longValue()
                ))
                .collect(Collectors.toList());
    }
    //lay danh sách diem danh theo mon hoc va hoc ky cho quan tri vien
    public List<DiemDanhAdmin> getAttendanceReportByHocKyNamAndMonHoc(Integer hocKy, Integer namHoc, String maMh) {
        List<Object[]> results = diemDanhRepository.findAttendanceReportByHocKyNamAndMonHoc(hocKy, namHoc, maMh);
        return results.stream()
                .map(row -> new DiemDanhAdmin(
                        (String) row[0],
                        (String) row[1],
                        (String) row[2],
                        ((Number) row[3]).longValue(),
                        ((Number) row[4]).longValue(),
                        ((Number) row[5]).longValue()
                ))
                .collect(Collectors.toList());
    }
}