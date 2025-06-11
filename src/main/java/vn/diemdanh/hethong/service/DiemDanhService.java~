package vn.diemdanh.hethong.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.dto.diemdanh.DiemDanhDto;
import vn.diemdanh.hethong.repository.user_man_and_login.DiemDanhRepository;
import java.util.*;

@Service
public class DiemDanhService {
    @Autowired private DiemDanhRepository diemDanhRepository;

    public List<Object[]> getDanhSachdiemDanhByMH_PH(String tenMH,String phongHoc) {
       return diemDanhRepository.getDanhSachDiemDanhByMH_PH(tenMH, phongHoc);
    }
}
