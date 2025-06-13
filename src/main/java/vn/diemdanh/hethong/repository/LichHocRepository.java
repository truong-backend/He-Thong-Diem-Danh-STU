package vn.diemdanh.hethong.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.diemdanh.hethong.entity.LichHoc;
import vn.diemdanh.hethong.entity.LichHocId;
import vn.diemdanh.hethong.entity.SinhVien;

import java.time.LocalDate;

public interface LichHocRepository extends JpaRepository<LichHoc, LichHocId> {
    Page<LichHoc> findByMaSv(SinhVien sinhVien, Pageable pageable);
    Page<LichHoc> findByMaSvAndMaGd_HocKy(SinhVien sinhVien, Integer hocKy, Pageable pageable);
    Page<LichHoc> findByMaSvAndMaGd_NgayBdBetweenOrMaGd_NgayKtBetween(
            SinhVien sinhVien, 
            LocalDate startDate1, 
            LocalDate endDate1,
            LocalDate startDate2,
            LocalDate endDate2,
            Pageable pageable
    );
} 