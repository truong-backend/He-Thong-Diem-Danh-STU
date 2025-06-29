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
    //Xóa điểm danh thủ công và đã điềm danh
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM diem_danh WHERE ma_sv = :maSv AND ma_tkb = :maTkb AND ngay_hoc = :ngayHoc", nativeQuery = true)
    int deleteDiemDanhByMaSvAndMaTkbAndNgayHoc(
            @Param("maSv") String maSv,
            @Param("maTkb") Long maTkb,
            @Param("ngayHoc") LocalDate ngayHoc
    );
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

    //Điểm danh quét QR sinh viên
    @Modifying
    @Transactional
    @Query(value = """
            insert into diem_danh(ma_tkb, ma_sv, ngay_hoc, diem_danh1,diem_danh2, ghi_chu)
            select 
                  :ma_tkb,
                  :ma_sv,
                  :ngay_hoc,
                  Now(),
                  NULL,
                  'Quét QR sinh viên - điểm danh lần 1'
            from tkb t join lich_gd gd on gd.ma_gd = t.ma_gd
                       join lich_hoc lh on lh.ma_gd = gd.ma_gd
                       join sinh_vien sv on sv.ma_sv = lh.ma_sv
            where t.ma_tkb = :ma_tkb and gd.ma_gv = :ma_gv and lh.ma_sv = :ma_sv limit 1         
            """,nativeQuery = true)
    int diemDanhQuetMaQRSinhVien(@Param("ma_tkb")int ma_tkb,@Param("ma_sv")String ma_sv,@Param("ngay_hoc")LocalDate ngay_hoc,@Param("ma_gv")String ma_gv);

    //Điểm danh lần 2 ghi đè cho record điểm danh trên
    @Modifying
    @Transactional
    @Query(value = """
        update diem_danh
        set diem_danh2 = now(),
                ghi_chu = concat(ifnull(ghi_chu,''),' Quét QR sinh viên - điểm danh lần 2')
        where ma_tkb = :maTkb and ma_sv = :maSv and ngay_hoc = :ngayHoc
            and diem_danh1 IS NOT NULL 
            and diem_danh2 IS NULL
    """,nativeQuery = true)
    int diemDanhQuetMaQRSinhVienLan2(@Param("maTkb")int maTkb,@Param("maSv")String maSv,@Param("ngayHoc")LocalDate ngayHoc);

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
}