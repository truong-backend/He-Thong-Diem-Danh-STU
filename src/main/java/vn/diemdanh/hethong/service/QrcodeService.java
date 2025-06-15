package vn.diemdanh.hethong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.diemdanh.hethong.dto.qrcode.QRCodeDTO;
import vn.diemdanh.hethong.dto.qrcode.TaoQRCodeRequest;
import vn.diemdanh.hethong.repository.QrcodeRepository;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class QrcodeService {
    @Autowired
    private QrcodeRepository qrcodeRepository;

    // Method 1: Two-step approach (recommended)
    @Transactional
    public QRCodeDTO createQRCode(TaoQRCodeRequest request) {
        try {
            // Step 1: Create the QR code
            qrcodeRepository.createQRCode(request.getMaTkb());

            // Step 2: Get the latest QR code ID
            Optional<Long> latestIdOpt = qrcodeRepository.getLatestQRCodeId(request.getMaTkb());
            if (latestIdOpt.isEmpty()) {
                throw new RuntimeException("Không tìm thấy QR Code vừa tạo");
            }

            // Step 3: Get the full QR code data
            return qrcodeRepository.getQRCodeById(latestIdOpt.get())
                    .map(row -> {
                        // Handle potential nested array structure
                        Object[] actualRow = extractActualRow(row);
                        return QRCodeDTO.builder()
                                .id(convertToLong(actualRow[0]))
                                .maTkb(convertToInteger(actualRow[1]))
                                .thoiGianKt(convertToLocalDateTime(actualRow[2]))
                                .ngayHoc(convertToLocalDate(actualRow[3]))
                                .phongHoc((String) actualRow[4])
                                .trangThai((String) actualRow[5])
                                .build();
                    })
                    .orElseThrow(() -> new RuntimeException("Không thể lấy thông tin QR Code"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Không thể tạo QR Code", e);
        }
    }

    // Method 2: Fixed version with proper array handling
    @Transactional
    public QRCodeDTO createQRCodeWithDebug(TaoQRCodeRequest request) {
        try {
            qrcodeRepository.createQRCode(request.getMaTkb());

            return qrcodeRepository.getLatestQRCode(request.getMaTkb())
                    .map(row -> {
                        // Extract the actual data row from nested structure
                        Object[] actualRow = extractActualRow(row);

                        System.out.println("=== PROCESSING ACTUAL ROW ===");
                        for (int i = 0; i < actualRow.length; i++) {
                            System.out.println("actualRow[" + i + "] = " + actualRow[i] +
                                    " (type: " + (actualRow[i] != null ? actualRow[i].getClass().getName() : "null") + ")");
                        }
                        System.out.println("===============================");

                        return QRCodeDTO.builder()
                                .id(convertToLong(actualRow[0]))
                                .maTkb(convertToInteger(actualRow[1]))
                                .thoiGianKt(convertToLocalDateTime(actualRow[2]))
                                .ngayHoc(convertToLocalDate(actualRow[3]))
                                .phongHoc((String) actualRow[4])
                                .trangThai((String) actualRow[5])
                                .build();
                    })
                    .orElseThrow(() -> new RuntimeException("Không thể tạo QR Code"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Không thể tạo QR Code", e);
        }
    }

    // Helper method to extract actual row from nested array structure
    private Object[] extractActualRow(Object[] row) {
        // Check if we have a nested array structure
        if (row.length == 1 && row[0] != null && row[0].getClass().isArray()) {
            // Return the nested array as the actual row
            return (Object[]) row[0];
        }
        // Otherwise, return the row as-is
        return row;
    }

    // Helper methods
    private Long convertToLong(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Long) return (Long) obj;
        if (obj instanceof Integer) return ((Integer) obj).longValue();
        if (obj instanceof BigInteger) return ((BigInteger) obj).longValue();
        if (obj instanceof Number) return ((Number) obj).longValue();

        if (obj.getClass().isArray()) {
            throw new RuntimeException("Received array instead of single value for Long conversion");
        }

        try {
            return Long.parseLong(obj.toString());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Cannot convert " + obj + " to Long", e);
        }
    }

    private Integer convertToInteger(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Integer) return (Integer) obj;
        if (obj instanceof Long) return ((Long) obj).intValue();
        if (obj instanceof Number) return ((Number) obj).intValue();

        try {
            return Integer.parseInt(obj.toString());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Cannot convert " + obj + " to Integer", e);
        }
    }

    private java.time.LocalDateTime convertToLocalDateTime(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Timestamp) return ((Timestamp) obj).toLocalDateTime();
        if (obj instanceof java.sql.Date) return ((java.sql.Date) obj).toLocalDate().atStartOfDay();
        throw new RuntimeException("Cannot convert " + obj + " to LocalDateTime");
    }

    private java.time.LocalDate convertToLocalDate(Object obj) {
        if (obj == null) return null;
        if (obj instanceof java.sql.Date) return ((java.sql.Date) obj).toLocalDate();
        if (obj instanceof Timestamp) return ((Timestamp) obj).toLocalDateTime().toLocalDate();
        throw new RuntimeException("Cannot convert " + obj + " to LocalDate");
    }
}