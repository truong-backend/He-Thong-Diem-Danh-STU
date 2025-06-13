package vn.diemdanh.hethong.helper;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vn.diemdanh.hethong.dto.sinhvien.SinhVienExcelDto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {
    public static ByteArrayInputStream sinhVienToExcel(List<SinhVienExcelDto> sinhViens) throws IOException {
        String[] columns = {"Mã SV", "Họ Tên", "Ngày Sinh", "Phái", "Địa Chỉ", "Số Điện Thoại", "Email", "Mã Lớp"};
        
        try (Workbook workbook = new XSSFWorkbook(); 
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Sinh Viên");

            // Create header row
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIdx = 1;
            for (SinhVienExcelDto sinhVien : sinhViens) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(sinhVien.getMaSv());
                row.createCell(1).setCellValue(sinhVien.getTenSv());
                if (sinhVien.getNgaySinh() != null) {
                    row.createCell(2).setCellValue(sinhVien.getNgaySinh().toString());
                }
                row.createCell(3).setCellValue(sinhVien.getPhai() == 1 ? "Nam" : "Nữ");
                row.createCell(4).setCellValue(sinhVien.getDiaChi());
                row.createCell(5).setCellValue(sinhVien.getSdt());
                row.createCell(6).setCellValue(sinhVien.getEmail());
                row.createCell(7).setCellValue(sinhVien.getMaLop());
            }

            // Resize columns
            for (int col = 0; col < columns.length; col++) {
                sheet.autoSizeColumn(col);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
} 