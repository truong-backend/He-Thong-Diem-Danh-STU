package vn.diemdanh.hethong.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.diemdanh.hethong.entity.SinhVien;

import java.time.LocalDate;
import java.util.List;

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
                dd.diem_danh2,
                dd.ghi_chu,
                CASE 
                    WHEN dd.diem_danh1 IS NOT NULL and dd.diem_danh2 IS NOT NULL THEN 'Đã điểm danh lần 2'
                    WHEN dd.diem_danh1 IS NOT NULL THEN 'Đã điểm danh lần 1'
                    ELSE 'Vắng'
                END as trang_thai_diem_danh,
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
            WHERE t.ma_tkb = :maTkb
            ORDER BY sv.ten_sv
            """, nativeQuery = true)
    List<Object[]> findStudentsForAttendance(@Param("maTkb") Integer maTkb);
}