package vn.diemdanh.hethong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.repository.GiaoVienRepository;
import vn.diemdanh.hethong.repository.TkbRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class TkbService {
    @Autowired
    private TkbRepository tkbRepository;
    public List<Date> getNgayHoc(String maGv, String maMh, int nhomMh, String hocKy) {
        return tkbRepository.findNgayHocByLichGd(maGv, maMh, nhomMh, hocKy);
    }
}
