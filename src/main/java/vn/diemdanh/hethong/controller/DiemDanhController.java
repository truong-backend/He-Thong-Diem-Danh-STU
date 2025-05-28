package vn.diemdanh.hethong.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.diemdanh.hethong.service.DiemDanhService;

import java.util.*;

@RestController
@RequestMapping("/api/getDanhSachDiemDanh")
public class DiemDanhController {
    @Autowired private DiemDanhService diemDanhService;

    @GetMapping("/monhoc-phonghoc")
    public List<Object[]> getDanhSachDiemDanh(@RequestParam String tenMh,@RequestParam String tenPH) {
        return diemDanhService.getDanhSachdiemDanhByMH_PH(tenMh, tenPH);
    }
}
