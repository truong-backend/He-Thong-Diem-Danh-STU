package vn.diemdanh.hethong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.dto.diemdanh.MonHocDto;
import vn.diemdanh.hethong.repository.LichGdRepository;
import vn.diemdanh.hethong.repository.MonHocRepository;

import java.util.List;

@Service
public class MonHocService {
    @Autowired
    private MonHocRepository monHocRepository;

    //Lấy danh sách môn học của giảnh viên đó trong học kỳ đó
    public List<MonHocDto> getMonHocByMaGvAndHocKy(String maGv, String hocKy) {
        return monHocRepository.findDistinctMonHocByGiaoVienAndHocKy(maGv, hocKy);
    }
    // Lấy danh sách nhóm môn học của môn hoc đó của giảng viên đó trong học kỳ đó
    public List<Integer> getNhomMonHoc(String maGv, String maMh, String hocKy) {
        return monHocRepository.findDistinctNhomMonHocByMaGvAndMaMhAndHocKy(maGv, maMh, hocKy);
    }
}
