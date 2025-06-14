package vn.diemdanh.hethong.helper;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vn.diemdanh.hethong.dto.giaovien.GiaoVienDTOExcel;
import vn.diemdanh.hethong.dto.giaovien.GiaoVienDto;
import vn.diemdanh.hethong.dto.sinhvien.SinhVienExcelDto;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class excel_Import {
    public static List<SinhVienExcelDto> excelImportFile(InputStream is) {
        List<SinhVienExcelDto> list = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (Workbook wb = new XSSFWorkbook(is)) {
           for(int i = 0;i < wb.getNumberOfSheets();i++) {
               Sheet sheet = wb.getSheetAt(0);
               Iterator<Row> rows = sheet.iterator();
               if (rows.hasNext()) {
                   rows.next();
               }

               while (rows.hasNext()) {
                   Row current = rows.next();
                   SinhVienExcelDto dto = new SinhVienExcelDto();
                   dto.setMaSv(formatter.formatCellValue(current.getCell(0)));
                   dto.setTenSv(formatter.formatCellValue(current.getCell(1)));
                   dto.setNgaySinh(LocalDate.parse(formatter.formatCellValue(current.getCell(2))));
                   dto.setPhai(Byte.parseByte(formatter.formatCellValue(current.getCell(3))));
                   dto.setDiaChi(formatter.formatCellValue(current.getCell(4)));
                   dto.setSdt(formatter.formatCellValue(current.getCell(5)));
                   dto.setEmail(formatter.formatCellValue(current.getCell(6)));
                   dto.setMaLop(formatter.formatCellValue(current.getCell(7)).trim());
                   dto.setAvatar(formatter.formatCellValue(current.getCell(8)));
                   list.add(dto);
               }
           }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static List<GiaoVienDTOExcel> importGiaoVienExcel(InputStream is) {
        List<GiaoVienDTOExcel> list = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();
        try (Workbook wb = new XSSFWorkbook(is)) {
           for(int i = 0; i < wb.getNumberOfSheets();i++){
               Sheet sheet = wb.getSheetAt(i);
               Iterator<Row> rows = sheet.iterator();
               if (rows.hasNext()) {
                   rows.next();
               }
               while (rows.hasNext()) {
                   Row current = rows.next();
                   Cell cellMaGV = current.getCell(0);
                   if(cellMaGV == null || formatter.formatCellValue(cellMaGV).isEmpty()){
                       continue;
                   }
                   GiaoVienDTOExcel dto = new GiaoVienDTOExcel();
                   dto.setMaGv(formatter.formatCellValue(current.getCell(0)));
                   dto.setTenGv(formatter.formatCellValue(current.getCell(1)));

                   String ngaySinhStr = formatter.formatCellValue(current.getCell(2));
                   if(!ngaySinhStr.isEmpty()) {
                       dto.setNgaySinh(LocalDate.parse(ngaySinhStr));
                   } else {
                       throw new RuntimeException("Ngày sinh bị trống tại dòng " + current.getRowNum());
                   }
                   String phaiStr = formatter.formatCellValue(current.getCell(3));
                   if(!phaiStr.isEmpty()) {
                       dto.setPhai(Byte.parseByte(phaiStr));
                   } else {
                       throw new RuntimeException("Giới tính bị trống tại dòng " + current.getRowNum());
                   }                   dto.setDiaChi(formatter.formatCellValue(current.getCell(4)));
                   dto.setSdt(formatter.formatCellValue(current.getCell(5)));
                   dto.setEmail(formatter.formatCellValue(current.getCell(6)));
                   dto.setAvatar(formatter.formatCellValue(current.getCell(7)));
                   list.add(dto);
               }
           }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
