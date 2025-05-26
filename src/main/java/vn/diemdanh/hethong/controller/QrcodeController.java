package vn.diemdanh.hethong.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.diemdanh.DiemDanhDto;
import vn.diemdanh.hethong.dto.qrcode.QrcodeDto;
import vn.diemdanh.hethong.entity.DiemDanh;
import vn.diemdanh.hethong.entity.SinhVien;
import vn.diemdanh.hethong.entity.Tkb;
import vn.diemdanh.hethong.repository.user_man_and_login.DiemDanhRepository;
import vn.diemdanh.hethong.repository.user_man_and_login.SinhVienRepository;
import vn.diemdanh.hethong.repository.user_man_and_login.TkbRepository;

import jakarta.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/api/qrcode")
public class QrcodeController {

    @Autowired
    private TkbRepository tkbRepository;

    @Autowired
    private DiemDanhRepository diemDanhRepository;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    // Tạo QR code cho điểm danh
    @PostMapping("/generate")
    public ResponseEntity<?> generateQRCode(@Valid @RequestBody QrcodeDto request) {
        try {
            // Kiểm tra thời khóa biểu có tồn tại không
            Tkb tkb = tkbRepository.findById(request.getMaTkb())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thời khóa biểu"));

            // Tạo dữ liệu cho QR code
            String qrData = createQRCodeData(tkb, request.getThoiGianHieuLuc());

            // Tạo QR code
            byte[] qrCodeImage = generateQRCodeImage(qrData, 250, 250);

            // Chuyển đổi byte array thành Base64 string
            String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCodeImage);

            // Cập nhật thông tin QR code vào response
            QrcodeDto response = convertToDto(tkb);
            response.setQrCodeData(qrCodeBase64);
            response.setThoiGianTao(LocalDateTime.now());
            response.setThoiGianHieuLuc(request.getThoiGianHieuLuc());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi tạo QR code: " + e.getMessage());
        }
    }

    // Xác thực QR code và điểm danh
    @PostMapping("/verify")
    public ResponseEntity<?> verifyQRCode(
            @RequestParam String qrData,
            @RequestParam String maSv
    ) {
        try {
            // Giải mã dữ liệu QR code
            String[] parts = qrData.split("\\|");
            if (parts.length != 4) {
                return ResponseEntity.badRequest().body("QR code không hợp lệ");
            }

            Long maTkb = Long.parseLong(parts[0]);
            String uuid = parts[1];
            long timestamp = Long.parseLong(parts[2]);
            int thoiGianHieuLuc = Integer.parseInt(parts[3]);

            // Kiểm tra thời gian hiệu lực
            long currentTime = System.currentTimeMillis();
            if (currentTime - timestamp > thoiGianHieuLuc * 60 * 1000) {
                return ResponseEntity.badRequest().body("QR code đã hết hạn");
            }

            // Kiểm tra thời khóa biểu
            Tkb tkb = tkbRepository.findById(maTkb)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thời khóa biểu"));

            // Kiểm tra sinh viên
            SinhVien sinhVien = sinhVienRepository.findById(maSv)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));

            // Tạo hoặc cập nhật điểm danh
            DiemDanh diemDanh = new DiemDanh();
            diemDanh.setMaTkb(tkb);
            diemDanh.setMaSv(maSv);
            diemDanh.setNgayHoc(LocalDate.now());
            diemDanh.setDiemDanh1(Instant.now());
            diemDanh.setGhiChu("Điểm danh qua QR code");

            diemDanh = diemDanhRepository.save(diemDanh);

            return ResponseEntity.ok(convertToDiemDanhDto(diemDanh));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi xác thực QR code: " + e.getMessage());
        }
    }

    // Helper method to create QR code data
    private String createQRCodeData(Tkb tkb, Integer thoiGianHieuLuc) {
        // Format: maTkb|uuid|timestamp|thoiGianHieuLuc
        return String.format("%d|%s|%d|%d",
                tkb.getId(),
                UUID.randomUUID().toString(),
                System.currentTimeMillis(),
                thoiGianHieuLuc);
    }

    // Helper method to generate QR code image
    private byte[] generateQRCodeImage(String text, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo QR code image", e);
        }
    }

    // Helper method to convert Tkb to QrcodeDto
    private QrcodeDto convertToDto(Tkb tkb) {
        QrcodeDto dto = new QrcodeDto();
        dto.setMaTkb(tkb.getId());
        dto.setMaGv(tkb.getMaGd().getMaGv().getMaGv());
        dto.setTenGv(tkb.getMaGd().getMaGv().getTenGv());
        dto.setMaMh(tkb.getMaGd().getMaMh().getMaMh());
        dto.setTenMh(tkb.getMaGd().getMaMh().getTenMh());
        dto.setPhongHoc(tkb.getPhongHoc());
        dto.setStBd(tkb.getStBd());
        dto.setStKt(tkb.getStKt());
        return dto;
    }

    // Helper method to convert DiemDanh to DiemDanhDto
    private DiemDanhDto convertToDiemDanhDto(DiemDanh diemDanh) {
        DiemDanhDto dto = new DiemDanhDto();
        dto.setId(diemDanh.getId());
        dto.setMaTkb(diemDanh.getMaTkb().getId());
        dto.setMaSv(diemDanh.getMaSv());
        
        // Get student information
        sinhVienRepository.findById(diemDanh.getMaSv()).ifPresent(sv -> 
            dto.setTenSv(sv.getTenSv())
        );

        // Get teacher and subject information from TKB
        Tkb tkb = diemDanh.getMaTkb();
        dto.setMaGv(tkb.getMaGd().getMaGv().getMaGv());
        dto.setTenGv(tkb.getMaGd().getMaGv().getTenGv());
        dto.setMaMh(tkb.getMaGd().getMaMh().getMaMh());
        dto.setTenMh(tkb.getMaGd().getMaMh().getTenMh());
        dto.setNgayHoc(diemDanh.getNgayHoc());
        dto.setPhongHoc(tkb.getPhongHoc());
        dto.setStBd(tkb.getStBd());
        dto.setStKt(tkb.getStKt());
        dto.setDiemDanh1(diemDanh.getDiemDanh1());
        dto.setDiemDanh2(diemDanh.getDiemDanh2());
        dto.setGhiChu(diemDanh.getGhiChu());
        
        return dto;
    }
} 