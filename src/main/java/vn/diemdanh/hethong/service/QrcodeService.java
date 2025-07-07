package vn.diemdanh.hethong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.diemdanh.hethong.dto.qrcode.DiemDanhQRRequest;
import vn.diemdanh.hethong.dto.qrcode.QRCodeDTO;
import vn.diemdanh.hethong.dto.qrcode.TaoQRCodeRequest;
import vn.diemdanh.hethong.repository.QrcodeRepository;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class QrcodeService {
    @Autowired
    private QrcodeRepository qrcodeRepository;

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

    // 9. ĐIỂM DANH BẰNG QR CODE
    @Transactional
    public void markAttendanceByQR(DiemDanhQRRequest request) {
        // Kiểm tra trạng thái QR code
        String qrStatus = qrcodeRepository.checkQRCodeStatus(request.getQrId());

        switch (qrStatus) {
            case "NOT_EXIST":
                throw new RuntimeException("QR Code không tồn tại");
            case "EXPIRED":
                throw new RuntimeException("QR Code đã hết hiệu lực");
            case "VALID":
                break;
            default:
                throw new RuntimeException("QR Code không hợp lệ");
        }

        // Kiểm tra sinh viên đã điểm danh chưa
        Integer hasAttended = qrcodeRepository.checkStudentAttended(request.getQrId(), request.getMaSv());
        if (hasAttended > 0) {
            throw new RuntimeException("Sinh viên đã điểm danh rồi");
        }

        try {
            qrcodeRepository.markAttendanceByQR(request.getQrId(), request.getMaSv());
        } catch (Exception e) {
            throw new RuntimeException("Không thể điểm danh bằng QR Code", e);
        }
    }
}