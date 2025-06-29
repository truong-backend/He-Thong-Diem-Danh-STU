package vn.diemdanh.hethong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.diemdanh.DiemDanhAdmin;
import vn.diemdanh.hethong.dto.monhoc.listMonHocSV.DiemDanhDto;
import vn.diemdanh.hethong.dto.thucong.DiemDanhRequest;
import vn.diemdanh.hethong.service.DiemDanhService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/diemdanh")
public class DiemDanhController {

    @Autowired
    private DiemDanhService diemDanhService;
    // 6. ĐIỂM DANH THỦ CÔNG
    @PostMapping("/diem-danh-thu-cong")
    public ResponseEntity<String> markAttendanceManual(@Valid @RequestBody DiemDanhRequest request) {
        diemDanhService.markAttendanceManual(request);
        return ResponseEntity.ok("Điểm danh thành công");
    }
    @GetMapping("/diemdanh_sinhvien")
    public ResponseEntity<List<DiemDanhDto>> getDiemDanhTheoSvVaMon(
            @RequestParam String maSv,
            @RequestParam String maMh) {
        List<DiemDanhDto> result = diemDanhService.getDiemDanhBySinhVienVaMonHoc(maSv, maMh);
        return ResponseEntity.ok(result);
    }
    // Xóa điểm danh thủ công
    @DeleteMapping("/huy")
    public ResponseEntity<?> huyDiemDanh(@RequestParam String maSv,
                                         @RequestParam Long maTkb,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayHoc) {
        boolean result = diemDanhService.huyDiemDanh(maSv, maTkb, ngayHoc);
        if (result) {
            return ResponseEntity.ok("Hủy điểm danh thành công");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bản ghi để xóa");
        }
    }
    //danh sách điểm danh cho theo hoc ky va nam hoc trang quan trị vien
    @GetMapping("/report")
    public ResponseEntity<List<DiemDanhAdmin>> getAttendanceReport(
            @RequestParam Integer hocKy,
            @RequestParam Integer namHoc) {
        try {
            List<DiemDanhAdmin> report = diemDanhService.getAttendanceReportByHocKyAndNam(hocKy, namHoc);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}