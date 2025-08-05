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
import java.time.LocalDateTime;
import java.util.List;

public interface DiemDanhRepository extends JpaRepository<DiemDanh, Long> {

    //Xóa điểm danh thủ công và đã điềm danh
    @Modifying
    @Transactional
    @Query(value = """
            UPDATE diem_danh 
            SET diem_danh1 = NULL, ghi_chu = :ghiChu
            WHERE ma_sv = :maSv AND ma_tkb = :maTkb AND ngay_hoc = :ngayHoc
            """, nativeQuery = true)
    int deleteDiemDanhByMaSvAndMaTkbAndNgayHoc(
            @Param("maSv") String maSv,
            @Param("maTkb") Integer maTkb,
            @Param("ngayHoc") LocalDate ngayHoc,
            @Param("ghiChu") String ghiChu
    );

    // 6. ĐIỂM DANH THỦ CÔNG - QR - INSERT/UPDATE
    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO diem_danh (ma_tkb, ma_sv, ngay_hoc, diem_danh1,diem_danh2, ghi_chu)
            VALUES (:maTkb, :maSv, :ngayHoc, NOW(),Null, :ghiChu)
            ON DUPLICATE KEY UPDATE 
                diem_danh2 = CASE 
                    WHEN diem_danh1 IS NOT NULL THEN NOW()
                    ELSE diem_danh2
                END,
                ghi_chu = :ghiChu
            """, nativeQuery = true)
    int markAttendanceManual(@Param("maTkb") Integer maTkb,
                             @Param("maSv") String maSv,
                             @Param("ngayHoc") LocalDate ngayHoc,
                             @Param("ghiChu") String ghiChu);

    //lấy danh sách điểm danh của sinh viên theo mã sinh viên và mã môn học
    @Query(value = """
                SELECT 
                    dd.ma_dd, mh.ma_mh, mh.ten_mh, tkb.ngay_hoc, 
                    dd.diem_danh1, dd.ghi_chu
                FROM diem_danh dd
                JOIN tkb ON dd.ma_tkb = tkb.ma_tkb
                JOIN lich_gd lgd ON tkb.ma_gd = lgd.ma_gd
                JOIN mon_hoc mh ON lgd.ma_mh = mh.ma_mh
                WHERE dd.ma_sv = :maSv AND mh.ma_mh = :maMh
                ORDER BY tkb.ngay_hoc 
            """, nativeQuery = true)
    List<Object[]> findDiemDanhByMaSvAndMaMh(@Param("maSv") String maSv, @Param("maMh") String maMh);

    //Điểm danh lần X ghi đè cho record điểm danh trên
    @Modifying
    @Transactional
    @Query(value = """
                update diem_danh
                set diem_danh1 = now(),  
                    ghi_chu = concat(ifnull(ghi_chu,''), :ghiChu)   
                where ma_tkb = :maTkb and ma_sv = :maSv and ngay_hoc = :ngayHoc
                    and diem_danh1 IS NOT NULL 
            """, nativeQuery = true)
    int diemDanhQuetMaQRSinhVienLanX(@Param("maTkb") int maTkb, @Param("maSv") String maSv, @Param("ngayHoc") LocalDate ngayHoc, @Param("ghiChu") String ghiChu);

    @Query(value = """
            select d.diem_danh1 from diem_danh d
            where d.ma_tkb = :maTkb and d.ma_sv = :maSv
                and d.ngay_hoc = :ngayHoc
            """, nativeQuery = true)
    LocalDateTime getDiemDanhLan1(@Param("maTkb") int maTkb, @Param("maSv") String maSv, @Param("ngayHoc") LocalDate ngayHoc);

    @Query(value = """
                select * from diem_danh 
                    where ma_sv = :maSv and ma_tkb = :maTkb and ngay_hoc = :ngayHoc
                    limit 1
            """, nativeQuery = true)
    List<DiemDanh> findDiemDanhExist(@Param("maTkb") int maTkb, @Param("maSv") String maSv, @Param("ngayHoc") LocalDate ngayHoc);

    //KẾT QUẢ ĐIỂM DANH
    @Query(value = """
                select t.ngay_hoc,gd.st_bd,gd.st_kt,
                    count(dl.lan_diem_danh) as svSoLanDD,
                    gvdd.gv_so_lan_dd as gvSoLanDD,
                    case when
                        count(dl.lan_diem_danh) = gvdd.gv_so_lan_dd then 'Có mặt'
                        else 'Vắng'
                    end as trangThai
                    from diem_danh_log dl join diem_danh dd on dd.ma_dd = dl.ma_dd
                    join tkb t on t.ma_tkb = dd.ma_tkb
                    join lich_gd gd on gd.ma_gd = t.ma_gd
                    join mon_hoc mh ON mh.ma_mh = gd.ma_mh
                    join(
                            select dd.ma_tkb,max(dl.lan_diem_danh) as gv_so_lan_dd from diem_danh_log dl 
                                join diem_danh dd on dd.ma_dd = dl.ma_dd
                                group by dd.ma_tkb 
                    ) gvdd on gvdd.ma_tkb = dd.ma_tkb
                where dd.ma_sv = :maSv and mh.ma_mh = :maMH and gd.nmh = :nmh
                group by t.ngay_hoc, gd.st_bd, gd.st_kt, gvdd.gv_so_lan_dd
            """, nativeQuery = true)
    List<Object[]> getKetQuaDDByMaSVAndMaMonHoc(@Param("maSv") String maSv, @Param("maMH") String maMH,@Param("nmh") Integer nmh);

    //danh sách điểm danh cho theo hoc ky va nam hoc trang quan trị vien
    @Query(value = """
            SELECT
                sv.ma_sv,
                sv.ten_sv,
                l.ten_lop,
                COUNT(DISTINCT tkb.ma_tkb),
                COUNT(DISTINCT dd.ma_dd),
                COUNT(DISTINCT tkb.ma_tkb) - COUNT(DISTINCT dd.ma_dd)
            FROM sinh_vien sv
            JOIN lop l ON sv.ma_lop = l.ma_lop
            JOIN lich_hoc lh ON sv.ma_sv = lh.ma_sv
            JOIN lich_gd lgd ON lh.ma_gd = lgd.ma_gd
            JOIN tkb ON lgd.ma_gd = tkb.ma_gd
            LEFT JOIN diem_danh dd ON dd.ma_tkb = tkb.ma_tkb AND dd.ma_sv = sv.ma_sv
            WHERE lgd.hoc_ky = :hocKy AND YEAR(lgd.ngay_bd) = :namHoc
            GROUP BY sv.ma_sv, sv.ten_sv, l.ten_lop
            """, nativeQuery = true)
    List<Object[]> findAttendanceReportByHocKyAndNam(@Param("hocKy") Integer hocKy,
                                                     @Param("namHoc") Integer namHoc);

    //lay danh sach diem danh theo giao vien cua mon hoc do trong hoc ky do cua nam do
    @Query(value = """
            SELECT
                sv.ma_sv,
                sv.ten_sv,
                l.ten_lop,
                COUNT(DISTINCT tkb.ma_tkb),
                COUNT(DISTINCT dd.ma_dd),
                COUNT(DISTINCT tkb.ma_tkb) - COUNT(DISTINCT dd.ma_dd)
            FROM sinh_vien sv
            JOIN lop l ON sv.ma_lop = l.ma_lop
            JOIN lich_hoc lh ON sv.ma_sv = lh.ma_sv
            JOIN lich_gd lgd ON lh.ma_gd = lgd.ma_gd
            JOIN tkb ON lgd.ma_gd = tkb.ma_gd
            LEFT JOIN diem_danh dd ON dd.ma_tkb = tkb.ma_tkb AND dd.ma_sv = sv.ma_sv
            WHERE lgd.hoc_ky = :hocKy 
                AND YEAR(lgd.ngay_bd) = :namHoc 
                AND lgd.ma_mh = :maMh 
                AND lgd.ma_gv = :maGv
            GROUP BY sv.ma_sv, sv.ten_sv, l.ten_lop
            """, nativeQuery = true)
    List<Object[]> findAttendanceReportByAllParams(@Param("hocKy") Integer hocKy,
                                                   @Param("namHoc") Integer namHoc,
                                                   @Param("maMh") String maMh,
                                                   @Param("maGv") String maGv);

    //lay danh sách diem danh theo mon hoc va hoc ky cho quan tri vien
    @Query(value = """
            SELECT
                sv.ma_sv,
                sv.ten_sv,
                l.ten_lop,
                COUNT(DISTINCT tkb.ma_tkb),
                sum(case when dd.diem_danh1 is not null and dd.diem_danh2 is not null then 1 else 0 end),
                COUNT(DISTINCT tkb.ma_tkb) - sum(case when dd.diem_danh1 is not null and dd.diem_danh2 is not null then 1 else 0 end)
            FROM sinh_vien sv
            JOIN lop l ON sv.ma_lop = l.ma_lop
            JOIN lich_hoc lh ON sv.ma_sv = lh.ma_sv
            JOIN lich_gd lgd ON lh.ma_gd = lgd.ma_gd
            JOIN tkb ON lgd.ma_gd = tkb.ma_gd
            LEFT JOIN diem_danh dd ON dd.ma_tkb = tkb.ma_tkb AND dd.ma_sv = sv.ma_sv
            WHERE lgd.hoc_ky = :hocKy AND YEAR(lgd.ngay_bd) = :namHoc AND lgd.ma_mh = :maMh
            GROUP BY sv.ma_sv, sv.ten_sv, l.ten_lop
            """, nativeQuery = true)
    List<Object[]> findAttendanceReportByHocKyNamAndMonHoc(@Param("hocKy") Integer hocKy,
                                                           @Param("namHoc") Integer namHoc,
                                                           @Param("maMh") String maMh);

    // Thống kê kết quả điểm danh của sinh viên
    @Query(value = """

            SELECT
                sv.ma_sv,
                sv.ten_sv,
                l.ten_lop,
                (SELECT COUNT(ngay_hoc) FROM tkb WHERE ma_gd = :maGd) AS so_buoi_hoc,
                sum(case when diem_danh1 is not null and diem_danh2 is not null then 1
                else 0 end) as so_buoi_diem_danh,
                ((SELECT COUNT(ngay_hoc) FROM tkb WHERE ma_gd = gd.ma_gd) -
                sum(case when diem_danh1 is not null and diem_danh2 is not null then 1
                else 0 end)) as so_buoi_vang
            FROM sinh_vien sv
            JOIN lop l ON sv.ma_lop = l.ma_lop
            JOIN lich_hoc lh ON sv.ma_sv = lh.ma_sv
            JOIN lich_gd gd ON gd.ma_gd = lh.ma_gd
            JOIN tkb t ON t.ma_gd = gd.ma_gd
            LEFT JOIN diem_danh d
                ON d.ma_tkb = t.ma_tkb AND d.ma_sv = sv.ma_sv
            WHERE gd.ma_mh = :maMh AND gd.nmh = :Nmh AND gd.ma_gd = :maGd
            GROUP BY sv.ma_sv, sv.ten_sv, l.ten_lop
            ORDER BY sv.ten_sv, l.ten_lop 
            """, nativeQuery = true)
    List<Object[]> thongKeDiemDanh(@Param("maMh") String maMh, @Param("Nmh") Integer Nmh, @Param("maGd") Integer maGd);

    @Query(value = """
            SELECT count(diem_danh1) ,count(diem_danh2) FROM diem_danh
            where ma_tkb = :maTkb and ma_sv = :maSv and ngay_hoc = :ngayHoc
            """, nativeQuery = true)
    Object[] getLanDiemDanh(@Param("maTkb") Integer maTkb, @Param("maSv") String maSv, @Param("ngayHoc") LocalDate ngayHoc);

}