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
    //Lấy danh sách sinh viên theo ngày giảng dạy của nhóm môn học đó của môn học đó của giảng viên đó trong học kỳ đó
    @Query(
            value = """
            SELECT sv.ma_sv, sv.ten_sv, sv.email, sv.ma_lop
            FROM sinh_vien sv
            JOIN lich_hoc lh ON sv.ma_sv = lh.ma_sv
            JOIN lich_gd lgd ON lh.ma_gd = lgd.ma_gd
            JOIN tkb ON tkb.ma_gd = lgd.ma_gd
            WHERE lgd.ma_gv = :maGv
              AND lgd.ma_mh = :maMh
              AND lgd.nmh = :nhom
              AND lgd.hoc_ky = :hocKy
              AND tkb.ngay_hoc = :ngayHoc
            """,
            nativeQuery = true
    )
    List<Object[]> findSinhVienTheoLichGiangDay(
            @Param("maGv") String maGv,
            @Param("maMh") String maMh,
            @Param("nhom") int nhom,
            @Param("hocKy") int hocKy,
            @Param("ngayHoc") LocalDate ngayHoc
    );
}