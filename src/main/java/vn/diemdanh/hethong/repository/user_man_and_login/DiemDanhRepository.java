package vn.diemdanh.hethong.repository.user_man_and_login;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.diemdanh.hethong.dto.diemdanh.DiemDanhDto;
import vn.diemdanh.hethong.entity.SinhVien;
import vn.diemdanh.hethong.entity.DiemDanh;
import vn.diemdanh.hethong.entity.Tkb;

import java.time.LocalDate;
import java.util.*;

public interface DiemDanhRepository extends JpaRepository<DiemDanh, Long> {

    @Query("Select sv.maSv,sv.tenSv,lop.tenLop,dd.diemDanh1,dd.diemDanh2,dd.ghiChu "
            +"FROM Lop lop "
            +"join lop.sinhViens sv "
            +"JOIN sv.lichGds gd "
            + "JOIN gd.maMh mh "
            +"JOIN gd.tkbs tkb  "
            + "JOIN tkb.diemDanhs dd "
            +" WHERE mh.tenMh = :tenMh AND gd.phongHoc = :phongHoc")
    List<Object[]> getDanhSachDiemDanhByMH_PH(@Param("tenMh")String tenMh, @Param("phongHoc")String phongHoc);
    Page<DiemDanh> findByMaTkb(Tkb tkb, Pageable pageable);
    Page<DiemDanh> findByMaSv(String maSv, Pageable pageable);
    Page<DiemDanh> findByMaTkbAndMaSv(Tkb tkb, String maSv, Pageable pageable);
    Page<DiemDanh> findByNgayHoc(LocalDate ngayHoc, Pageable pageable);
    Page<DiemDanh> findByNgayHocBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
    Page<DiemDanh> findByMaSvAndNgayHocBetween(String maSv, LocalDate startDate, LocalDate endDate, Pageable pageable);
} 