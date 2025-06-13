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

    public List<MonHocDto> getMonHocByMaGvAndHocKy(String maGv, int hocKy) {
        return monHocRepository.findDistinctMonHocByGiaoVienAndHocKy(maGv, hocKy);
    }
}
