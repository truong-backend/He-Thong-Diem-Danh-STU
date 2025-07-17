package vn.diemdanh.hethong.controller.attendance;

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
    @PutMapping("/huy")
    public ResponseEntity<?> huyDiemDanh(@RequestBody DiemDanhRequest request) {
        boolean result = diemDanhService.huyDiemDanh(
                request.getMaSv(),
                request.getMaTkb(),
                request.getNgayHoc(),
                request.getGhiChu()
        );
        if (result) {
            return ResponseEntity.ok("Hủy điểm danh thành công");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bản ghi để xóa");
        }
    }
    //danh sách điểm danh cho theo hoc ky va nam hoc trang quan trị vien
    @GetMapping("/report-hoc-ky")
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
    //lay danh sách diem danh theo mon hoc va hoc ky cho quan tri vien
    @GetMapping("/report-mon-hoc")
    public ResponseEntity<List<DiemDanhAdmin>> getAttendanceReportByMonHoc(
            @RequestParam Integer hocKy,
            @RequestParam Integer namHoc,
            @RequestParam String maMh) {
        try {
            List<DiemDanhAdmin> report = diemDanhService.getAttendanceReportByHocKyNamAndMonHoc(hocKy, namHoc, maMh);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    //    //lay danh sach diem danh theo giao vien cua mon hoc do trong hoc ky do cua nam do
    @GetMapping("/report/full")
    public ResponseEntity<List<DiemDanhAdmin>> getFullAttendanceReport(
            @RequestParam Integer hocKy,
            @RequestParam Integer namHoc,
            @RequestParam String maMh,
            @RequestParam String maGv) {
        try {
            List<DiemDanhAdmin> report = diemDanhService.getAttendanceReportByAllParams(hocKy, namHoc, maMh, maGv);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    //KẾT QUẢ ĐIỂM DANH THEO MÔN HỌC
    @GetMapping("/ketqua_diemdanh_sinhvien")
    public ResponseEntity<?> getKetquaDiemdanhTheoSvVaMonHoc(@RequestParam String maMh){
        return ResponseEntity.ok(diemDanhService.getKetQuaDiemDanhSinhVien(maMh));
    }
}