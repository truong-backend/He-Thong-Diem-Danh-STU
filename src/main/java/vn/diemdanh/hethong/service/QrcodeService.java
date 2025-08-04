package vn.diemdanh.hethong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.diemdanh.hethong.dto.qrcode.DiemDanhQRRequest;
import vn.diemdanh.hethong.dto.qrcode.QRCodeDTO;
import vn.diemdanh.hethong.dto.qrcode.TaoQRCodeRequest;
import vn.diemdanh.hethong.dto.thucong.DiemDanhRequest;
import vn.diemdanh.hethong.entity.DiemDanh;
import vn.diemdanh.hethong.entity.DiemDanhLog;
import vn.diemdanh.hethong.exception.AppException;
import vn.diemdanh.hethong.exception.ErrorCode;
import vn.diemdanh.hethong.repository.DiemDanhLogRepository;
import vn.diemdanh.hethong.repository.DiemDanhRepository;
import vn.diemdanh.hethong.repository.LichHocRepository;
import vn.diemdanh.hethong.repository.QrcodeRepository;

import java.sql.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class QrcodeService {
    @Autowired
    private QrcodeRepository qrcodeRepository;
    @Autowired
    private DiemDanhRepository diemDanhRepo;
    @Autowired
    DiemDanhLogRepository diemDanhLogRepo;
    @Autowired
    LichHocRepository lichHocRepository;
    // 8. TẠO QR CODE MỚI
    @Transactional
    public QRCodeDTO createQRCode(TaoQRCodeRequest request) {
        try {
            qrcodeRepository.createQRCode(request.getMaTkb(), request.getSoPhut());

            return qrcodeRepository.getLatestQRCode(request.getMaTkb())
                    .map(row -> QRCodeDTO.builder()
                            .id((Long) row[0][0])
                            .maTkb((Integer) row[0][1])
                            .thoiGianKt(((Timestamp) row[0][2]).toLocalDateTime())
                            .ngayHoc(((java.sql.Date) row[0][3]).toLocalDate())
                            .phongHoc((String) row[0][4])
                            .trangThai((String) row[0][5])
                            .build())
                    .orElseThrow(() -> new RuntimeException("Không thể tạo QR Code"));
        } catch (Exception e) {
            throw new RuntimeException("Không thể tạo QR Code", e);
        }
    }

    //Ghi thông tin điểm danh vào log
    private void insertDiemDanhLog(DiemDanhRequest req,int soLan){
        List<DiemDanh> existDiemDanh = diemDanhRepo.findDiemDanhExist(
                req.getMaTkb(), req.getMaSv(), req.getNgayHoc()
        );
        if(existDiemDanh != null){
            DiemDanh diemDanhGet = existDiemDanh.get(0);

            DiemDanhLog diemDanhLog = new DiemDanhLog();
            diemDanhLog.setMaDd(diemDanhGet);
            diemDanhLog.setLanDiemDanh(soLan);
            diemDanhLog.setThoiGianDiemDanh(Instant.now());
            diemDanhLogRepo.save(diemDanhLog);
        }
    }
    // 9. ĐIỂM DANH BẰNG QR CODE
    @Transactional
    public int markAttendanceByQR(DiemDanhQRRequest request) {
        // Kiểm tra QR code còn hiệu lực không
        Optional<Object[]> qrCode = qrcodeRepository.checkQRCodeValidity(request.getQrId());
        if (qrCode.isEmpty()) {
            throw new RuntimeException("QR Code đã hết hiệu lực hoặc không tồn tại");
        }
        Object[] data = qrCode.get();
        Object[] row = (Object[]) data[0];
        Integer maTkb = ((Integer) row[1]).intValue();
        LocalDate ngayHoc = ((Date) row[3]).toLocalDate();
        //Kiểm tra sinh viên có thuộc tkb của buổi học
        Integer existSinhVien = lichHocRepository.findSinhVienByMaTkb(request.getMaSv(),maTkb);
        if(existSinhVien == 0|| existSinhVien == null) {
            throw new AppException(ErrorCode.SINHVIEN_NOTEXIST_TKB);
        }
        Object[] countDiemDanh = diemDanhRepo.getLanDiemDanh(
                maTkb,
                request.getMaSv(),
                ngayHoc
        );
        Object[] result = (Object[]) countDiemDanh[0];
        Integer diemDanh1 = ((Number) result[0]).intValue();
        Integer diemDanh2= ((Number) result[1]).intValue();
//        Integer countDiemDanh = diemDanhLogRepo.getSoLanDiemDanh(
//                maTkb,
//                request.getMaSv(),
//                ngayHoc
//        );
        int diemDanhCountCurrent = 0;
        String ghiChu = "";
        if(diemDanh1 == 1 && diemDanh2 == 1){
            throw new AppException(ErrorCode.PASSED_DIEMDANH_ALL);
        }
        if(diemDanh1 == 0){
            diemDanhCountCurrent = diemDanh1 + 1;
            ghiChu = "\nĐiểm danh lần " + diemDanhCountCurrent;
        }
        else if (diemDanh1 == 1 && diemDanh2 == 0) {
            LocalDateTime timeDiemDanhlan1 = diemDanhRepo.getDiemDanhLan1(
                    maTkb,
                    request.getMaSv(),
                    ngayHoc
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
            ghiChu = "\nĐiểm danh lần " + diemDanhCountCurrent;
        }


        int resultDiemDanh = diemDanhRepo.markAttendanceManual(
                maTkb,
                request.getMaSv(),
                ngayHoc,
                ghiChu
        );
        if (resultDiemDanh >= 1) {
            DiemDanhRequest req = new DiemDanhRequest();
            req.setMaTkb(maTkb);
            req.setMaSv(request.getMaSv());
            req.setNgayHoc(ngayHoc);
            req.setGhiChu(ghiChu);
            insertDiemDanhLog(req, diemDanhCountCurrent);
            return resultDiemDanh;
        }

        return 0;
    }

}