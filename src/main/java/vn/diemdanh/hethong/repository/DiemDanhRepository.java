package vn.diemdanh.hethong.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.diemdanh.hethong.entity.DiemDanh;
import vn.diemdanh.hethong.entity.Tkb;

import java.time.LocalDate;
import java.util.List;

public interface DiemDanhRepository extends JpaRepository<DiemDanh, Long> {

    // 6. ĐIỂM DANH THỦ CÔNG - INSERT/UPDATE
    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO diem_danh (ma_tkb, ma_sv, ngay_hoc, diem_danh1, ghi_chu)
        VALUES (:maTkb, :maSv, :ngayHoc, NOW(), :ghiChu)
        ON DUPLICATE KEY UPDATE 
            diem_danh2 = CASE 
                WHEN diem_danh1 IS NOT NULL AND diem_danh2 IS NULL THEN NOW()
                ELSE diem_danh2
            END,
            ghi_chu = VALUES(ghi_chu)
        """, nativeQuery = true)
    void markAttendanceManual(@Param("maTkb") Integer maTkb,
                              @Param("maSv") String maSv,
                              @Param("ngayHoc") LocalDate ngayHoc,
                              @Param("ghiChu") String ghiChu);
    //lấy danh sách điểm danh của sinh viên theo mã sinh viên và mã môn học
    @Query(value = """
        SELECT 
            dd.ma_dd, mh.ma_mh, mh.ten_mh, tkb.ngay_hoc, 
            dd.diem_danh1, dd.diem_danh2, dd.ghi_chu
        FROM diem_danh dd
        JOIN tkb ON dd.ma_tkb = tkb.ma_tkb
        JOIN lich_gd lgd ON tkb.ma_gd = lgd.ma_gd
        JOIN mon_hoc mh ON lgd.ma_mh = mh.ma_mh
        WHERE dd.ma_sv = :maSv AND mh.ma_mh = :maMh
        ORDER BY tkb.ngay_hoc
    """, nativeQuery = true)
    List<Object[]> findDiemDanhByMaSvAndMaMh(@Param("maSv") String maSv, @Param("maMh") String maMh);

} 