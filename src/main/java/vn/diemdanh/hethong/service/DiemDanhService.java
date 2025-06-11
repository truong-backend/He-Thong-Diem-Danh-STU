package vn.diemdanh.hethong.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.repository.DiemDanhRepository;

@Service
public class DiemDanhService {
    @Autowired private DiemDanhRepository diemDanhRepository;

//    public List<Object[]> getDanhSachdiemDanhByMH_PH(String tenMH,String phongHoc) {
//       return diemDanhRepository.getDanhSachDiemDanhByMH_PH(tenMH, phongHoc);
//    }
}
