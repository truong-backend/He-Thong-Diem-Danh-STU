package vn.diemdanh.hethong.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.diemdanh.hethong.dto.diemdanh.MonHocDto;
import vn.diemdanh.hethong.entity.GiaoVien;
import vn.diemdanh.hethong.entity.LichGd;
import vn.diemdanh.hethong.entity.MonHoc;

import java.util.List;

public interface LichGdRepository extends JpaRepository<LichGd, Long> {
    Page<LichGd> findByMaGv(GiaoVien giaoVien, Pageable pageable);
    Page<LichGd> findByMaMh(MonHoc monHoc, Pageable pageable);
    Page<LichGd> findByHocKy(Integer hocKy, Pageable pageable);

    @Query(value = """
        SELECT DISTINCT mh.ma_mh AS maMh, mh.ten_mh AS tenMh
        FROM lich_gd lg
        JOIN mon_hoc mh ON lg.ma_mh = mh.ma_mh
        WHERE lg.ma_gv = :maGv AND lg.hoc_ky = :hocKy
        """, nativeQuery = true)
    List<MonHocDto> findDistinctMonHocByGiaoVienAndHocKy(
            @Param("maGv") String maGv,
            @Param("hocKy") int hocKy
    );
} 