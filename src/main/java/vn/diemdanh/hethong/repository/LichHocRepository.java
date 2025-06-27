package vn.diemdanh.hethong.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.diemdanh.hethong.entity.LichHoc;
import vn.diemdanh.hethong.entity.LichHocId;
import vn.diemdanh.hethong.entity.SinhVien;

import java.time.LocalDate;
import java.util.List;

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
    @Query(value = """
        select count(*)
            from sinh_vien sv
            join lich_hoc lh on sv.ma_sv = lh.ma_sv
            join lich_gd gd on gd.ma_gd = lh.ma_gd
            join tkb t on t.ma_gd = gd.ma_gd
        where sv.ma_sv = :maSv and t.ma_tkb = :maTkb
    """,nativeQuery = true)
    Integer findSinhVienByMaTkb(@Param("maSv")String maSv,@Param("maTkb")int maTkb);
} 