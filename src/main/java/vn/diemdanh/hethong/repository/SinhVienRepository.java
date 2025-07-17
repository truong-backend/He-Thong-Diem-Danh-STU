package vn.diemdanh.hethong.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.diemdanh.hethong.dto.sinhvien.SinhVienDto;
import vn.diemdanh.hethong.entity.SinhVien;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface SinhVienRepository extends JpaRepository<SinhVien, String> {
    Page<SinhVien> findByMaSvContainingIgnoreCase(String maSv, Pageable pageable);

    // 5. LẤY DANH SÁCH SINH VIÊN CỦA NGÀY GIẢNG DẠY (CHO ĐIỂM DANH)
    @Query(value = """
            SELECT 
                sv.ma_sv,
                sv.ten_sv,
                sv.email,
                l.ten_lop,
                k.ten_khoa,
                dd.diem_danh1,
                dd.ghi_chu,
                CASE 
                    WHEN dd.diem_danh1 IS NOT NULL THEN concat('Đã điểm danh lần') 
                    ELSE 'Chưa điểm danh'
                END as ghiChu,
                t.ngay_hoc,
                t.phong_hoc,
                CONCAT('Tiết ', t.st_bd, ' - ', t.st_kt) as ca_hoc
            FROM tkb t
            JOIN lich_gd lg ON t.ma_gd = lg.ma_gd
            JOIN lich_hoc lh ON lg.ma_gd = lh.ma_gd
            JOIN sinh_vien sv ON lh.ma_sv = sv.ma_sv
            JOIN lop l ON sv.ma_lop = l.ma_lop
            JOIN khoa k ON l.ma_khoa = k.ma_khoa
            LEFT JOIN diem_danh dd ON t.ma_tkb = dd.ma_tkb AND sv.ma_sv = dd.ma_sv
            LEFT JOIN diem_danh_log dl on dl.ma_dd = dd.ma_dd
            WHERE t.ma_tkb = :maTkb
                        GROUP BY
                            sv.ma_sv, sv.ten_sv, sv.email,
                            l.ten_lop, k.ten_khoa,
                            dd.diem_danh1, dd.ghi_chu,
                            t.ngay_hoc, t.phong_hoc, t.st_bd, t.st_kt, dd.ma_dd
            ORDER BY sv.ten_sv
            """, nativeQuery = true)
    List<Object[]> findStudentsForAttendance(@Param("maTkb") Integer maTkb);
    @Query(value = "SELECT ma_sv, ten_sv, email FROM sinh_vien", nativeQuery = true)
    List<Object[]> findAllSinhVienRaw();

    @Query(value = """
        SELECT * FROM sinh_vien sv
        WHERE NOT EXISTS (
            SELECT 1
            FROM lich_hoc lh
            JOIN lich_gd lgd ON lh.ma_gd = lgd.ma_gd
            WHERE lh.ma_sv = sv.ma_sv
              AND lgd.ma_mh = :maMh
              AND lgd.nmh = :nmh
        )
    """, nativeQuery = true)
    List<SinhVienDto> findSinhVienChuaHocMonHocNative(
            @Param("maMh") String maMh,
            @Param("nmh") int nmh
    );
    // Lấy mã sinh viên thông qua email đăng nhập (do dùng email đăng nhập)
    @Query("SELECT sv.maSv FROM SinhVien sv WHERE sv.email = :email")
    String findMaSvByEmail(@Param("email") String email);
}