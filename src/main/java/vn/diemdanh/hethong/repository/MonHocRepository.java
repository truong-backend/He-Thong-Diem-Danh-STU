package vn.diemdanh.hethong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    // 4. LẤY DANH SÁCH MÔN HỌC CỦA SINH VIÊN đang hoc
    @Query(value = """
        SELECT 
            mh.ma_mh, mh.ten_mh, mh.so_tiet, lgd.nmh,
            lgd.hoc_ky, lgd.phong_hoc, lgd.ngay_bd, lgd.ngay_kt
        FROM sinh_vien sv
        JOIN lich_hoc lh ON sv.ma_sv = lh.ma_sv
        JOIN lich_gd lgd ON lh.ma_gd = lgd.ma_gd
        JOIN mon_hoc mh ON lgd.ma_mh = mh.ma_mh
        WHERE sv.ma_sv = :maSv
    """, nativeQuery = true)
    List<Object[]> findMonHocByMaSv(@Param("maSv") String maSv);
    //Lấy danh sách môn học theo thứ
    @Query(value = """
        SELECT 
            mh.ten_mh AS tenMonHoc,
            gv.ten_gv AS tenGiaoVien,
            tkb.ngay_hoc AS ngayHoc,
            tkb.st_bd AS tietBatDau,
            tkb.st_kt AS tietKetThuc
        FROM tkb
        JOIN lich_gd lgd ON tkb.ma_gd = lgd.ma_gd
        JOIN mon_hoc mh ON lgd.ma_mh = mh.ma_mh
        JOIN giao_vien gv ON lgd.ma_gv = gv.ma_gv
        WHERE DAYOFWEEK(tkb.ngay_hoc) = :thu
    """, nativeQuery = true)
    List<Object[]> findLichHocByThu(@Param("thu") int thu);

    //lấy danh sách môn của sinh viên để phục vụ hiển thị thời khóa biếu
    @Query(value = """
        SELECT
          mh.ma_mh AS maMonHoc,
          mh.ten_mh AS tenMonHoc,
          lgd.ma_gd AS nhomMonHoc,
          mh.so_tiet AS soTiet,
          sv.ma_lop AS maLop,
          DAYOFWEEK(MIN(tkb.ngay_hoc)) AS thu,
          MIN(tkb.st_bd) AS tietBD,
          lgd.phong_hoc AS phong,
          gv.ten_gv AS tenGiaoVien,
          CONCAT(DATE_FORMAT(lgd.ngay_bd, '%Y-%m-%d'), ' đến ', DATE_FORMAT(lgd.ngay_kt, '%Y-%m-%d')) AS tuanNgayBatDauKetThuc
        FROM sinh_vien sv
        JOIN lich_hoc lh ON sv.ma_sv = lh.ma_sv
        JOIN lich_gd lgd ON lh.ma_gd = lgd.ma_gd
        JOIN mon_hoc mh ON lgd.ma_mh = mh.ma_mh
        JOIN giao_vien gv ON lgd.ma_gv = gv.ma_gv
        JOIN tkb ON tkb.ma_gd = lgd.ma_gd
        WHERE sv.ma_sv = :maSv
        GROUP BY
          mh.ma_mh,
          mh.ten_mh,
          lgd.ma_gd,
          mh.so_tiet,
          sv.ma_lop,
          lgd.phong_hoc,
          gv.ten_gv,
          lgd.ngay_bd,
          lgd.ngay_kt
        ORDER BY MIN(tkb.ngay_hoc), MIN(tkb.st_bd)
        """, nativeQuery = true)
    List<Object[]> findThoiKhoaBieuByMaSv(@Param("maSv") String maSv);
    //lay danh sach mon hoc cho trong quan tri vien
    @Query(value = """
        SELECT DISTINCT
            mh.ma_mh,
            mh.ten_mh,
            mh.so_tiet
        FROM mon_hoc mh
        JOIN lich_gd lgd ON mh.ma_mh = lgd.ma_mh
        WHERE lgd.hoc_ky = :hocKy AND YEAR(lgd.ngay_bd) = :namHoc
        """, nativeQuery = true)
    List<Object[]> findMonHocByHocKyAndNam(@Param("hocKy") Integer hocKy,
                                           @Param("namHoc") Integer namHoc);
}