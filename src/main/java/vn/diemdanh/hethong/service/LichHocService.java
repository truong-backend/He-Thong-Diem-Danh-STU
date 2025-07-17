package vn.diemdanh.hethong.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.diemdanh.hethong.dto.hocky.HocKyDTO;
import vn.diemdanh.hethong.dto.sinhvien.SinhVienChuaHocDTO;
import vn.diemdanh.hethong.dto.sinhvien.SinhVienDto;
import vn.diemdanh.hethong.repository.LichGdRepository;
import vn.diemdanh.hethong.repository.LichHocRepository;
import vn.diemdanh.hethong.repository.SinhVienRepository;

import java.sql.Date;
import java.time.LocalDate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LichHocService {
    @Autowired
    private LichHocRepository lichHocRepository;

    public List<SinhVienChuaHocDTO> getSinhVienChuaHoc(Long maGd) {
        List<Object[]> rawList = lichHocRepository.findSinhVienChuaHocByMaGd(maGd);
        return rawList.stream()
                .map(obj -> new SinhVienChuaHocDTO(
                        (String) obj[0],
                        (String) obj[1],
                        (String) obj[2]

                ))
                .collect(Collectors.toList());
    }
    public List<SinhVienChuaHocDTO> getSinhVienDaHoc(Long maGd) {
        List<Object[]> rawList = lichHocRepository.findSinhVienDaHocByMaGd(maGd);
        return rawList.stream()
                .map(obj -> new SinhVienChuaHocDTO(
                        (String) obj[0],
                        (String) obj[1],
                        (String) obj[2]

                ))
                .collect(Collectors.toList());
    }

    public void themSinhVienVaoLichHoc(Long maSv, Long maGd) {
        lichHocRepository.insertSinhVienToLichHoc(maSv, maGd);
    }

    public void xoaSinhVienKhoiLichHoc(String maSv, Long maGd) {
        lichHocRepository.deleteSinhVienFromLichHoc(maSv, maGd);
    }
}
