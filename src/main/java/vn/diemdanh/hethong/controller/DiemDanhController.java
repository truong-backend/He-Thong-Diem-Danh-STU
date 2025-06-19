package vn.diemdanh.hethong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.monhoc.listMonHocSV.DiemDanhDto;
import vn.diemdanh.hethong.dto.thucong.DiemDanhRequest;
import vn.diemdanh.hethong.service.DiemDanhService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}