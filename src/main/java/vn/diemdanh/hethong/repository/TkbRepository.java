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


    // 4. LẤY DANH SÁCH NGÀY GIẢNG DẠY CỦA NHÓM MÔN HỌC
    @Query(value = """
        SELECT 
            t.ma_tkb,
            t.ngay_hoc,
            t.phong_hoc,
            CONCAT('Tiết ', t.st_bd, ' - ', t.st_kt) as ca_hoc,
            lg.ma_gv,
            gv.ten_gv,
            mh.ten_mh,
            lg.nmh as nhom_mon_hoc,
            CASE 
                WHEN t.ngay_hoc < CURDATE() THEN 'Đã qua'
                WHEN t.ngay_hoc = CURDATE() THEN 'Hôm nay'
                ELSE 'Sắp tới'
            END as trang_thai
        FROM tkb t
        JOIN lich_gd lg ON t.ma_gd = lg.ma_gd
        JOIN giao_vien gv ON lg.ma_gv = gv.ma_gv
        JOIN mon_hoc mh ON lg.ma_mh = mh.ma_mh
        WHERE t.ma_gd = :maGd
        ORDER BY t.ngay_hoc DESC
        """, nativeQuery = true)
    List<Object[]> findClassDates(@Param("maGd") Integer maGd);

} 