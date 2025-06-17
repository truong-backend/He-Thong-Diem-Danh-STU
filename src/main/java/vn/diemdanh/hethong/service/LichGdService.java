package vn.diemdanh.hethong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.dto.hocky.HocKyDTO;
import vn.diemdanh.hethong.repository.LichGdRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LichGdService {

    // 1. LẤY DANH SÁCH HỌC KỲ
    @Autowired
    private LichGdRepository lichGdRepository;
    public List<HocKyDTO> getAllSemesters() {
        List<Object[]> results = lichGdRepository.findAllSemesters();

        return results.stream()
                .map(row -> HocKyDTO.builder()
                        .hocKy((Integer) row[0])
                        .hocKyDisplay((String) row[1])
                        .namHoc((Integer) row[2])
                        .build())
                .collect(Collectors.toList());
    }

}
