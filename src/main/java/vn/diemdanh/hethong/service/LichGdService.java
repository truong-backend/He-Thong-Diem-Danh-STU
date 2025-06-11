package vn.diemdanh.hethong.service.lichgd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.dto.lichgd.LichGdDto;
import vn.diemdanh.hethong.repository.user_man_and_login.LichGdRepository;

import java.util.*;

@Service
public class LichGdService {
    @Autowired private LichGdRepository lichGdRepository;
    public List<LichGdDto> getLichGiangDayByIdGV(String maGv) {
        return lichGdRepository.getLichGiangDayByIdGV(maGv);
    }
}
