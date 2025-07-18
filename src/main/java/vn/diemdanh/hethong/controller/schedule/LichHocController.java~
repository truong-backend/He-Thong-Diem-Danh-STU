package vn.diemdanh.hethong.controller.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.diemdanh.hethong.dto.sinhvien.AddSinhVienRequest;
import vn.diemdanh.hethong.dto.sinhvien.SinhVienChuaHocDTO;
import vn.diemdanh.hethong.service.LichHocService;

import java.util.List;

@RestController
@RequestMapping("/api/lichhoc")
public class LichHocController {
    @Autowired
    private LichHocService lichHocService;
    @GetMapping("/chua-hoc/{maGd}")
    public ResponseEntity<List<SinhVienChuaHocDTO>> getSinhVienChuaHoc(@PathVariable Long maGd) {
        return ResponseEntity.ok(lichHocService.getSinhVienChuaHoc(maGd));
    }
    @GetMapping("/da-hoc/{maGd}")
    public ResponseEntity<List<SinhVienChuaHocDTO>> getSinhVienDaHoc(@PathVariable Long maGd) {
        return ResponseEntity.ok(lichHocService.getSinhVienDaHoc(maGd));
    }

    @PostMapping("/them")
    public ResponseEntity<String> themSinhVien(@RequestBody AddSinhVienRequest request) {
        lichHocService.themSinhVienVaoLichHoc(request.getMaSv(), request.getMaGd());
        return ResponseEntity.ok("Đã thêm sinh viên vào lịch học.");
    }

    @DeleteMapping("/xoa")
    public ResponseEntity<String> xoaSinhVien(@RequestParam String maSv, @RequestParam Long maGd) {
        lichHocService.xoaSinhVienKhoiLichHoc(maSv, maGd);
        return ResponseEntity.ok("Đã xóa sinh viên khỏi lịch học.");
    }
} 