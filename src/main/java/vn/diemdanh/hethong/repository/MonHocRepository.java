package vn.diemdanh.hethong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.diemdanh.hethong.dto.diemdanh.MonHocDto;
import vn.diemdanh.hethong.entity.MonHoc;

import java.util.List;

public interface MonHocRepository extends JpaRepository<MonHoc, String> {
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