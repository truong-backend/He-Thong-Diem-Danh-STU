package vn.diemdanh.hethong.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.diemdanh.hethong.entity.GiaoVien;
import vn.diemdanh.hethong.entity.LichGd;
import vn.diemdanh.hethong.entity.MonHoc;

import java.util.List;
import java.util.Optional;

public interface LichGdRepository extends JpaRepository<LichGd, Long> {
    Page<LichGd> findByMaGv(GiaoVien giaoVien, Pageable pageable);
    Page<LichGd> findByMaMh(MonHoc monHoc, Pageable pageable);
    Page<LichGd> findByHocKy(Integer hocKy, Pageable pageable);

    // 1. LẤY DANH SÁCH HỌC KỲ THEO NĂM HỌC
    @Query(value = """
    SELECT DISTINCT 
        hoc_ky,
        CASE 
            WHEN hoc_ky = 1 THEN CONCAT('HK1 - Năm học ', (YEAR(ngay_bd) - 1), ' - ', YEAR(ngay_bd))
            WHEN hoc_ky = 2 THEN CONCAT('HK2 - Năm học ', (YEAR(ngay_bd) - 1), ' - ', YEAR(ngay_bd))
            WHEN hoc_ky = 3 THEN CONCAT('HK3 - Năm học ', YEAR(ngay_bd), ' - ', YEAR(ngay_bd))
            ELSE CONCAT('HK', hoc_ky, ' - Năm học ', YEAR(ngay_bd))
        END as hoc_ky_display,
        YEAR(ngay_bd) as nam_hoc
    FROM lich_gd 
    WHERE ma_gv = :maGv
    ORDER BY nam_hoc DESC, hoc_ky
    """, nativeQuery = true)
    List<Object[]> findAllSemesters(@Param("maGv") String maGv);
    //lấy tất danh sách học kỳ cho admin
    @Query(value = """
        SELECT DISTINCT
            hoc_ky,
            CASE
                WHEN hoc_ky = 1 THEN CONCAT('HK1 - Năm học ', (YEAR(ngay_bd) - 1), ' - ', YEAR(ngay_bd))
                WHEN hoc_ky = 2 THEN CONCAT('HK2 - Năm học ', (YEAR(ngay_bd) - 1), ' - ', YEAR(ngay_bd))
                WHEN hoc_ky = 3 THEN CONCAT('HK3 - Năm học ', YEAR(ngay_bd), ' - ', YEAR(ngay_bd))
                ELSE CONCAT('HK', hoc_ky, ' - Năm học ', YEAR(ngay_bd))
            END as hoc_ky_display,
            YEAR(ngay_bd) as nam_hoc
        FROM lich_gd
        ORDER BY nam_hoc DESC, hoc_ky
        """, nativeQuery = true)
    List<Object[]> findAllHocKy();


    @Query(value = "SELECT ma_gd FROM lich_gd WHERE hoc_ky = :hocKy AND ma_mh = :maMh AND ma_gv = :maGv AND nmh = :nhom", nativeQuery = true)
    Integer findMaGd(
            @Param("hocKy") int hocKy,
            @Param("maMh") String maMh,
            @Param("maGv") String maGv,
            @Param("nhom") int nhom
    );

    @Query(value = "SELECT \n" +
            "        lg.ma_gd AS id,\n" +
            "        lg.ma_gv,\n" +
            "        gv.ten_gv,\n" +
            "        lg.ma_mh,\n" +
            "        mh.ten_mh,\n" +
            "        lg.nmh,\n" +
            "        lg.phong_hoc,\n" +
            "        lg.ngay_bd,\n" +
            "        lg.ngay_kt,\n" +
            "        lg.st_bd,\n" +
            "        lg.st_kt,\n" +
            "        lg.hoc_ky\n" +
            "    FROM lich_gd lg\n" +
            "    JOIN giao_vien gv ON lg.ma_gv = gv.ma_gv\n" +
            "    JOIN mon_hoc mh ON lg.ma_mh = mh.ma_mh\n" +
            "    WHERE lg.ma_gv = :maGv", nativeQuery = true)
    // 根据教师编号查询课程表
    List<Object[]> findLichGdByMaGv(@Param("maGv") String maGv);

    Optional<LichGd> findById(Long maGd);

} 