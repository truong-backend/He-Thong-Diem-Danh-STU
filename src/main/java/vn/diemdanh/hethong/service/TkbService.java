package vn.diemdanh.hethong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.dto.tkb.NgayGiangDayDTO;
import vn.diemdanh.hethong.dto.tkb.TkbDto;
import vn.diemdanh.hethong.dto.tkb.TkbGiaoVienDto;
import vn.diemdanh.hethong.entity.*;
import vn.diemdanh.hethong.repository.*;

import java.time.temporal.ChronoUnit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TkbService {
    @Autowired
    private TkbRepository tkbRepository;

    @Autowired
    private LichGdRepository lichGdRepository;
    @Autowired
    private MonHocRepository monHocRepository;

    @Autowired
    private NgayLeRepository ngayLeRepository;
    // 4. LẤY DANH SÁCH NGÀY GIẢNG DẠY
    public List<NgayGiangDayDTO> getClassDates(Integer maGd) {
        List<Object[]> results = tkbRepository.findClassDates(maGd);

        return results.stream()
                .map(row -> NgayGiangDayDTO.builder()
                        .maTkb((Integer) row[0])
                        .ngayHoc(((java.sql.Date) row[1]).toLocalDate())
                        .phongHoc((String) row[2])
                        .caHoc((String) row[3])
                        .maGv((String) row[4])
                        .tenGv((String) row[5])
                        .tenMh((String) row[6])
                        .nhomMonHoc((Integer) row[7])
                        .trangThai((String) row[8])
                        .build())
                .collect(Collectors.toList());
    }

    

    public List<TkbGiaoVienDto> getTkb(String maSv, Integer hocKy, LocalDate startOfWeek, LocalDate endOfWeek) {
        List<Object[]> results = tkbRepository.findTkbByMaSvAndHocKyAndNgayHocBetween(maSv, hocKy, startOfWeek, endOfWeek);

        List<TkbGiaoVienDto> dtos = new ArrayList<>();
        for (Object[] row : results) {
            TkbGiaoVienDto dto = new TkbGiaoVienDto();
            dto.setMaMH((String) row[0]);
            dto.setTenMon((String) row[1]);
            dto.setNmh(row[2] == null ? null : ((Number) row[2]).intValue());
            dto.setNgayHoc(row[3] == null ? null : ((java.sql.Date) row[3]).toLocalDate());

            dto.setTietBd(row[4] == null ? null : ((Number) row[4]).intValue());
            dto.setTietKt(row[5] == null ? null : ((Number) row[5]).intValue());
            dto.setPhong((String) row[6]);
            dto.setCbgd((String) row[7]);
            dto.setHocKy(row[8] == null ? null : ((Number) row[8]).intValue());
            dto.setGhiChu((String) row[9]);

            dtos.add(dto);
        }

        return dtos;
    }

    public List<TkbDto> getTkbByMaGd(Long maGd) {
        List<Object[]> results = tkbRepository.findByMaGdNative(maGd);
        return results.stream().map(row -> {
            TkbDto tkbDto = new TkbDto();
            tkbDto.setId(((Number) row[0]).longValue()); // ma_tkb
            tkbDto.setMaGd(((Number) row[1]).longValue()); // ma_gd
            tkbDto.setNgayHoc(row[2] instanceof java.sql.Date
                    ? ((java.sql.Date) row[2]).toLocalDate()
                    : (LocalDate) row[2]); // ngay_hoc
            tkbDto.setPhongHoc((String) row[3]); // phong_hoc
            tkbDto.setStBd(((Number) row[4]).intValue()); // st_bd
            tkbDto.setStKt(((Number) row[5]).intValue()); // st_kt
            // maGv, tenGv, maMh, tenMh are not in tkb table, so they remain null
            return tkbDto;
        }).collect(Collectors.toList());
    }
}
