package vn.diemdanh.hethong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.dto.diemdanh.ListDiemDanhMonHoc.MonHocSinhVienDto;
import vn.diemdanh.hethong.dto.monhoc.MonHocGiangVienDTO;
import vn.diemdanh.hethong.dto.monhoc.NhomMonHocDTO;
import vn.diemdanh.hethong.repository.MonHocRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonHocService {
    @Autowired
    private MonHocRepository monHocRepository;




    // 2. LẤY DANH SÁCH MÔN HỌC CỦA GIẢNG VIÊN
    public List<MonHocGiangVienDTO> getSubjectsByTeacher(String maGv, Integer hocKy, Integer namHoc) {
//        log.info("Fetching subjects for teacher: {}, semester: {}, year: {}", maGv, hocKy, namHoc);
        List<Object[]> results = monHocRepository.findSubjectsByTeacher(maGv, hocKy, namHoc);

        return results.stream()
                .map(row -> MonHocGiangVienDTO.builder()
                        .maMh((String) row[0])
                        .tenMh((String) row[1])
                        .hocKy((Integer) row[2])
                        .namHoc(((Number) row[3]).intValue())
                        .build())
                .collect(Collectors.toList());
    }
    // 3. LẤY DANH SÁCH NHÓM MÔN HỌC
    public List<NhomMonHocDTO> getSubjectGroups(String maGv, String maMh, Integer hocKy, Integer namHoc) {
//        log.info("Fetching subject groups for teacher: {}, subject: {}", maGv, maMh);
        List<Object[]> results = monHocRepository.findSubjectGroups(maGv, maMh, hocKy, namHoc);

        return results.stream()
                .map(row -> NhomMonHocDTO.builder()
                        .maGd((Integer) row[0])
                        .nhomMonHoc((Integer) row[1])
                        .tenMh((String) row[2])
                        .phongHoc((String) row[3])
                        .ngayBd(((java.sql.Date) row[4]).toLocalDate())
                        .ngayKt(((java.sql.Date) row[5]).toLocalDate())
                        .caHoc((String) row[6])
                        .build())
                .collect(Collectors.toList());
    }
    // 4. LẤY DANH SÁCH MÔN HỌC CỦA SINH VIÊN
    public List<MonHocSinhVienDto> getMonHocCuaSinhVien(String maSv) {
        List<Object[]> results = monHocRepository.findMonHocByMaSv(maSv);

        return results.stream()
                .map(r -> MonHocSinhVienDto.builder()
                        .maMh((String) r[0])
                        .tenMh((String) r[1])
                        .soTiet(((Number) r[2]).intValue()) // an toàn nếu là Integer/BigInteger
                        .nmh((Integer) r[3])
                        .hocKy((Integer) r[4])
                        .phongHoc((String) r[5])
                        .ngayBd(((java.sql.Date) r[6])) // nếu dùng LocalDate
                        .ngayKt(((java.sql.Date) r[7]))
                        .build())
                .collect(Collectors.toList());
    }

}
