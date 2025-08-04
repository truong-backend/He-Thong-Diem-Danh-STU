package vn.diemdanh.hethong.controller.attendance;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.diemdanh.DiemDanhAdmin;
import vn.diemdanh.hethong.dto.diemdanh.ThongKeDiemDanhDTO;
import vn.diemdanh.hethong.dto.monhoc.listMonHocSV.DiemDanhDto;
import vn.diemdanh.hethong.dto.thucong.DiemDanhRequest;
import vn.diemdanh.hethong.helper.ExcelThongKeExport;
import vn.diemdanh.hethong.service.DiemDanhService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/diemdanh")
public class DiemDanhController {

    @Autowired
    private DiemDanhService diemDanhService;

    //Thống kê điểm danh
    @GetMapping("/thongke_diemdanh")
    public ResponseEntity<?> thongKeDiemDanh(@RequestParam String maMh,
                                             @RequestParam Integer nmh,
                                             @RequestParam Integer maGd) {
        return ResponseEntity.ok(diemDanhService.getKetQuaDiemDanhAllSinhVien(maMh, nmh, maGd));
    }
    // Xuất excel thống kê điểm danh
    @GetMapping("/export-thong-ke-excel")
    public void xuatThongKeDiemDanh(HttpServletResponse response,
                                    @RequestParam String maMh,
                                    @RequestParam Integer nmh,
                                    @RequestParam Integer maGd) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=thong_ke_diem_danh.xlsx");
        List<ThongKeDiemDanhDTO>  thongKe  = diemDanhService.getKetQuaDiemDanhAllSinhVien(maMh, nmh, maGd);
        ExcelThongKeExport excel = new ExcelThongKeExport(thongKe);
        excel.export(response);
    }


    // ========================== ĐIỂM DANH THỦ CÔNG ==========================

    @PostMapping("/diem-danh-thu-cong")
    public ResponseEntity<String> markAttendanceManual(@Valid @RequestBody DiemDanhRequest request) {
        try {
            int result = diemDanhService.diemDanhSinhVien(request);
            if (result > 0) {
                return ResponseEntity.ok("Điểm danh thành công");
            }
            return ResponseEntity.ok("Lỗi điểm danh");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Điểm danh thất bại: " + e.getMessage());
        }
    }

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

    // ========================== SINH VIÊN XEM ĐIỂM DANH ==========================

    @GetMapping("/diemdanh_sinhvien")
    public ResponseEntity<List<DiemDanhDto>> getDiemDanhTheoSvVaMon(
            @RequestParam String maSv,
            @RequestParam String maMh) {
        List<DiemDanhDto> result = diemDanhService.getDiemDanhBySinhVienVaMonHoc(maSv, maMh);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/ketqua_diemdanh_sinhvien")
    public ResponseEntity<?> getKetquaDiemdanhTheoSvVaMonHoc(@RequestParam String maMh,  @RequestParam Integer nmh) {
        return ResponseEntity.ok(diemDanhService.getKetQuaDiemDanhSinhVien(maMh,nmh));
    }

    // ========================== QUẢN TRỊ VIÊN - BÁO CÁO ==========================

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
}
