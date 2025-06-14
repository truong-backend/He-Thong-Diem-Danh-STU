package vn.diemdanh.hethong.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.diemdanh.hethong.entity.LichGd;
import vn.diemdanh.hethong.entity.MonHoc;
import vn.diemdanh.hethong.entity.Tkb;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public interface TkbRepository extends JpaRepository<Tkb, Long> {
    Page<Tkb> findByMaGd(LichGd lichGd, Pageable pageable);
    Page<Tkb> findByNgayHoc(LocalDate ngayHoc, Pageable pageable);
    Page<Tkb> findByNgayHocBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    //Phần điểm danh QR code
    @Query(value = """
        SELECT tkb.ngay_hoc 
        FROM tkb 
        JOIN lich_gd ON tkb.ma_gd = lich_gd.ma_gd 
        WHERE lich_gd.ma_gv = :maGv 
          AND lich_gd.ma_mh = :maMh 
          AND lich_gd.nmh = :nmh 
          AND lich_gd.hoc_ky = :hocKy 
        ORDER BY tkb.ngay_hoc ASC
        """, nativeQuery = true)
    List<Date> findNgayHocByLichGd(
            @Param("maGv") String maGv,
            @Param("maMh") String maMh,
            @Param("nmh") int nhomMh,
            @Param("hocKy") int hocKy
    );
} 