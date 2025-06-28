package vn.diemdanh.hethong.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vn.diemdanh.hethong.dto.diemdanh.DiemDanhQRSinhVienRequest;
import vn.diemdanh.hethong.dto.monhoc.listMonHocSV.DiemDanhDto;
import vn.diemdanh.hethong.dto.thucong.DiemDanhRequest;
import vn.diemdanh.hethong.repository.DiemDanhRepository;
import vn.diemdanh.hethong.repository.GiaoVienRepository;
import vn.diemdanh.hethong.repository.LichHocRepository;
import vn.diemdanh.hethong.repository.TkbRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiemDanhService {

    @Autowired
    private DiemDanhRepository diemDanhRepository;

    @Autowired
    private TkbRepository tkbRepository;


    // Lấy danh sách học kỳ
    public List<String> getHocKyList() {
        return tkbRepository.findAll().stream()
                .map(tkb -> tkb.getMaGd().getHocKy().toString())
                .distinct()
                .collect(Collectors.toList());
    }
    // 6. ĐIỂM DANH THỦ CÔNG
    @Transactional
    public void markAttendanceManual(DiemDanhRequest request) {
        try {
            diemDanhRepository.markAttendanceManual(
                    request.getMaTkb(),
                    request.getMaSv(),
                    request.getNgayHoc(),
                    request.getGhiChu()
            );
        } catch (Exception e) {

            throw new RuntimeException("Không thể điểm danh thủ công", e);
        }
    }
    // lấy danh sách điểm danh của sinh vien
    @Transactional

    public List<DiemDanhDto> getDiemDanhBySinhVienVaMonHoc(String maSv, String maMh) {
        List<Object[]> results = diemDanhRepository.findDiemDanhByMaSvAndMaMh(maSv, maMh);
        return results.stream()
                .map(r -> DiemDanhDto.builder()
                        .maDd((Integer) r[0])
                        .maMh((String) r[1])
                        .tenMh((String) r[2])
                        .ngayHoc((Date) r[3])
                        .diemDanh1(((Timestamp) r[4]).toLocalDateTime())
                        .diemDanh2(((Timestamp) r[4]).toLocalDateTime())
                        .ghiChu((String) r[6])
                        .build()
                )
                .collect(Collectors.toList());
    }

    //Xóa điêm danh thủ công
    public boolean huyDiemDanh(String maSv, Long maTkb, LocalDate ngayHoc) {
        int deletedCount = diemDanhRepository.deleteDiemDanhByMaSvAndMaTkbAndNgayHoc(maSv, maTkb, ngayHoc);
        return deletedCount > 0;
    }
    @Autowired
    private GiaoVienRepository giaoVienRepository;
    @Autowired
    LichHocRepository lichHocRepository;
    @Transactional
    public int diemDanhMaQRSinhVien(DiemDanhQRSinhVienRequest request) {
        // 获取当前用户的认证信息
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // 获取当前用户的邮箱
        String email = auth.getName();
        // 根据邮箱获取教师的编号
        String maGv = giaoVienRepository.findMaGvByEmail(email);
        // 根据学生的编号和教师的编号查找学生的课程表
        Integer existSinhVien = lichHocRepository.findSinhVienByMaTkb(request.getMaSv(),request.getMaTkb());
        // 如果学生没有课程表，则抛出异常
        if(existSinhVien == 0 || existSinhVien == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Sinh viên không có thời khóa biểu của buổi học này");
        }

        // 学生第二次扫码签到
        int diemdanh = diemDanhRepository.diemDanhQuetMaQRSinhVienLan2(
                request.getMaTkb(),
                request.getMaSv(),
                request.getNgayHoc()
        );

        // 如果学生没有签到，则进行第一次签到
        if(diemdanh == 0){
            return diemDanhRepository.diemDanhQuetMaQRSinhVien(
                    request.getMaTkb(),
                    request.getMaSv(),
                    request.getNgayHoc(),
                    maGv
            );
        }
        // 返回签到结果
        return diemdanh;
    }
}
