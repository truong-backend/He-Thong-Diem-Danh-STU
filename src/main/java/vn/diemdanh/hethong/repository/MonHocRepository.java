package vn.diemdanh.hethong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.diemdanh.hethong.dto.diemdanh.MonHocDto;
import vn.diemdanh.hethong.entity.MonHoc;

import java.util.List;

public interface MonHocRepository extends JpaRepository<MonHoc, String> {


    // 2. LẤY DANH SÁCH MÔN HỌC CỦA GIẢNG VIÊN THEO HỌC KỲ VÀ NĂM
    @Query(value = """
        SELECT DISTINCT 
            lg.ma_mh,
            mh.ten_mh,
            lg.hoc_ky,
            YEAR(lg.ngay_bd) as nam_hoc
        FROM lich_gd lg
        JOIN mon_hoc mh ON lg.ma_mh = mh.ma_mh
        WHERE lg.ma_gv = :maGv
            AND lg.hoc_ky = :hocKy
            AND YEAR(lg.ngay_bd) = :namHoc
        ORDER BY mh.ten_mh
        """, nativeQuery = true)
    List<Object[]> findSubjectsByTeacher(@Param("maGv") String maGv,
                                         @Param("hocKy") Integer hocKy,
                                         @Param("namHoc") Integer namHoc);
    // 3. LẤY DANH SÁCH NHÓM MÔN HỌC
    @Query(value = """
        SELECT DISTINCT
            lg.ma_gd,
            lg.nmh as nhom_mon_hoc,
            mh.ten_mh,
            lg.phong_hoc,
            lg.ngay_bd,
            lg.ngay_kt,
            CONCAT('Tiết ', lg.st_bd, ' - ', lg.st_kt) as ca_hoc
        FROM lich_gd lg
        JOIN mon_hoc mh ON lg.ma_mh = mh.ma_mh
        WHERE lg.ma_gv = :maGv
            AND lg.ma_mh = :maMh
            AND lg.hoc_ky = :hocKy
            AND YEAR(lg.ngay_bd) = :namHoc
        ORDER BY lg.nmh
        """, nativeQuery = true)
    List<Object[]> findSubjectGroups(@Param("maGv") String maGv,
                                     @Param("maMh") String maMh,
                                     @Param("hocKy") Integer hocKy,
                                     @Param("namHoc") Integer namHoc);
} 