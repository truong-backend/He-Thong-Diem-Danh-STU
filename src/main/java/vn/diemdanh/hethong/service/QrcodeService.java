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
            qrcodeRepository.createQRCode(request.getMaTkb(), request.getSoPhut()); // Giả sử thời gian hiệu lực là 15 phút

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
        // Kiểm tra QR code còn hiệu lực không
        Optional<Object[]> qrCode = qrcodeRepository.checkQRCodeValidity(request.getQrId());
        if (qrCode.isEmpty()) {
            throw new RuntimeException("QR Code đã hết hiệu lực hoặc không tồn tại");
        }

        try {
            qrcodeRepository.markAttendanceByQR(request.getQrId(), request.getMaSv());
        } catch (Exception e) {
            throw new RuntimeException("Không thể điểm danh bằng QR Code", e);
        }
    }
}
