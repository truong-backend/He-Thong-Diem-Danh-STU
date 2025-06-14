package vn.diemdanh.hethong.helper;

import com.opencsv.CSVReader;
import org.apache.commons.math3.analysis.function.Sinh;
import vn.diemdanh.hethong.dto.giaovien.GiaoVienDTOExcel;
import vn.diemdanh.hethong.dto.giaovien.GiaoVienDto;
import vn.diemdanh.hethong.dto.sinhvien.SinhVienExcelDto;
import vn.diemdanh.hethong.entity.SinhVien;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class csvImport {
    public static List<SinhVienExcelDto> csvImportFile(InputStream is){
        List<SinhVienExcelDto> list = new ArrayList<>();
        try(BufferedReader fileRead = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            CSVReader csvReader = new CSVReader(fileRead))
        {
            String[] nextLine;
            csvReader.readNext();
            while( (nextLine = csvReader.readNext()) != null){
                if (nextLine.length < 9){
                    System.err.println("Dòng lỗi: " + String.join(",", nextLine));
                    continue;
                }

                SinhVienExcelDto dto = new SinhVienExcelDto();
                dto.setMaSv(nextLine[0]);
                dto.setTenSv(nextLine[1]);
                dto.setNgaySinh(LocalDate.parse(nextLine[2]));
                dto.setPhai(Byte.parseByte(nextLine[3]));
                dto.setDiaChi(nextLine[4]);
                dto.setSdt(nextLine[5]);
                dto.setEmail(nextLine[6]);
                dto.setMaLop(nextLine[7]);
                dto.setAvatar("NULL".equals(nextLine[8]) ? "NULL" : nextLine[8]);
                list.add(dto);
            }
        }catch (Exception e) {
            throw new RuntimeException("Lỗi đọc CSV: " + e.getMessage());
        }
        return list;
    }
    public static List<GiaoVienDTOExcel> importCSVGiaoVien(InputStream is){
        List<GiaoVienDTOExcel> list = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        CSVReader csvReader = new CSVReader(bufferedReader)){
            String[] nextLine;
            csvReader.readNext();
            while( (nextLine = csvReader.readNext()) != null){
                GiaoVienDTOExcel dto = new GiaoVienDTOExcel();
                dto.setMaGv(nextLine[0]);
                dto.setTenGv(nextLine[1]);
                dto.setPhai(Byte.parseByte(nextLine[2]));
                dto.setDiaChi(nextLine[3]);
                dto.setSdt(nextLine[4]);
                dto.setEmail(nextLine[5]);
                dto.setAvatar("NULL".equals(nextLine[6]) ? "NULL" : nextLine[6]);
                list.add(dto);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
