package vn.diemdanh.hethong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.dto.hocky.HocKyDTO;
import vn.diemdanh.hethong.dto.lichgd.LichGdDto;
import vn.diemdanh.hethong.entity.LichGd;
import vn.diemdanh.hethong.repository.LichGdRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LichGdService {

    // 1. LẤY DANH SÁCH HỌC KỲ
    @Autowired
    private LichGdRepository lichGdRepository;
    public List<HocKyDTO> getAllSemesters(String maGv) {
        List<Object[]> results = lichGdRepository.findAllSemesters(maGv);

        return results.stream()
                .map(row -> HocKyDTO.builder()
                        .hocKy((Integer) row[0])
                        .hocKyDisplay((String) row[1])
                        .namHoc((Integer) row[2])
                        .build())
                .collect(Collectors.toList());
    }
    //lấy tất danh sách học kỳ cho admin

    public List<HocKyDTO> getAllHocKy() {
        List<Object[]> results = lichGdRepository.findAllHocKy();
        return results.stream()
                .map(row -> new HocKyDTO(
                        (Integer) row[0],
                        (String) row[1],
                        (Integer) row[2]
                ))
                .collect(Collectors.toList());
    }

    public Integer getMaGd(int hocKy, String maMh, String maGv, int nhom) {
        return lichGdRepository.findMaGd(hocKy, maMh, maGv, nhom);
    }

    public List<LichGdDto> getLichGdByMaGv(String maGv) {
        List<Object[]> rows = lichGdRepository.findLichGdByMaGv(maGv);
        return rows.stream().map(row -> {
            LichGdDto dto = new LichGdDto();
            dto.setId(((Number) row[0]).longValue());
            dto.setMaGv((String) row[1]);
            dto.setTenGv((String) row[2]);
            dto.setMaMh((String) row[3]);
            dto.setTenMh((String) row[4]);
            dto.setNmh((Integer) row[5]);
            dto.setPhongHoc((String) row[6]);
            dto.setNgayBd(row[7] instanceof java.sql.Date
                    ? ((java.sql.Date) row[7]).toLocalDate()
                    : (LocalDate) row[7]);
            dto.setNgayKt(row[8] instanceof java.sql.Date
                    ? ((java.sql.Date) row[8]).toLocalDate()
                    : (LocalDate) row[8]);
            dto.setStBd((Integer) row[9]);
            dto.setStKt((Integer) row[10]);
            dto.setHocKy(String.valueOf(row[11])); // Ép kiểu từ Integer sang String
            return dto;
        }).collect(Collectors.toList());
    }

}
