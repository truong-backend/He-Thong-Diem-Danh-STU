package vn.diemdanh.hethong.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.qrcode.DiemDanhQRRequest;
import vn.diemdanh.hethong.dto.qrcode.QRCodeDTO;
import vn.diemdanh.hethong.dto.qrcode.TaoQRCodeRequest;
import vn.diemdanh.hethong.service.QrcodeService;

@RestController
@RequestMapping("/api/qrcode")
public class QrcodeController {
    @Autowired
    private QrcodeService qrcodeService;
    // 8. TẠO QR CODE MỚI
    @PostMapping("/qr/TaoQRCode")
    public ResponseEntity<QRCodeDTO> generateQRCode(@Valid @RequestBody TaoQRCodeRequest request) {
        QRCodeDTO qrCode = qrcodeService.createQRCode(request); // Use debug version first
        return ResponseEntity.ok(qrCode);
    }
    // 9. ĐIỂM DANH BẰNG QR CODE
    @PostMapping("/qr")
    public ResponseEntity<String> markAttendanceByQR(@Valid @RequestBody DiemDanhQRRequest request) {
        try {
            qrcodeService.markAttendanceByQR(request);
            return ResponseEntity.ok("Điểm danh bằng QR Code thành công");
        } catch (RuntimeException e) {
            String message = e.getMessage().contains("đã điểm danh")
                    ? "Sinh viên đã điểm danh rồi"
                    : "Không thể điểm danh: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

} 