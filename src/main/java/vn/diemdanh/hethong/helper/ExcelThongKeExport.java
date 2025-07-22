package vn.diemdanh.hethong.helper;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vn.diemdanh.hethong.dto.diemdanh.ThongKeDiemDanhDTO;

import java.io.IOException;
import java.util.List;

public class ExcelThongKeExport {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<ThongKeDiemDanhDTO> thongKeList;

    public ExcelThongKeExport(List<ThongKeDiemDanhDTO> thongKeList) {
        this.thongKeList = thongKeList;
        workbook = new XSSFWorkbook();
    }
    private void writeHeaderRow(){
        sheet = workbook.createSheet("Thống kê điểm danh");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        String[] headers = {
                "MSSV","Họ và tên","Lớp","Số buổi học","Số buổi đã điểm danh","Số buổi chưa điểm danh"
        };
        for(int i = 0; i < headers.length; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }
    private void writeDataRow(){
        int rowCount = 1;
        for(ThongKeDiemDanhDTO dto : thongKeList){
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(dto.getMaSv());
            row.createCell(1).setCellValue(dto.getTenSv());
            row.createCell(2).setCellValue(dto.getTenLop());
            row.createCell(3).setCellValue(dto.getSo_buoi_hoc());
            row.createCell(4).setCellValue(dto.getSo_buoi_diem_danh());
            row.createCell(5).setCellValue(dto.getSo_buoi_chua_diem_danh());
        }
        for(int i = 0; i < 6 ;i++){
            sheet.autoSizeColumn(i);
        }
    }
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRow();
        writeDataRow();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
