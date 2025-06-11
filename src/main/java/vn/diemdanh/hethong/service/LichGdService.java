package vn.diemdanh.hethong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.repository.LichGdRepository;

@Service
public class LichGdService {
    @Autowired private LichGdRepository lichGdRepository;
//    public List<LichGdDto> getLichGiangDayByIdGV(String maGv) {
//        return lichGdRepository.getLichGiangDayByIdGV(maGv);
//    }
}
