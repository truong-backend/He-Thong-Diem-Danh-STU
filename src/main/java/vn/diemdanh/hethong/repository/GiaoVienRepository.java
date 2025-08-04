package vn.diemdanh.hethong.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.diemdanh.hethong.entity.GiaoVien;

import java.util.List;

public interface GiaoVienRepository extends JpaRepository<GiaoVien, String> {
    Page<GiaoVien> findByMaGvContainingIgnoreCase(String maGv, Pageable pageable);

    @Query("SELECT gv.maGv FROM GiaoVien gv WHERE gv.email = :email")
    String findMaGvByEmail(@Param("email") String email);
    //lấy danh sach giao vien theo mon hoc hoc ky va nam
    @Query(value = """
        SELECT DISTINCT
            gv.ma_gv,
            gv.ten_gv
        FROM giao_vien gv
        JOIN lich_gd lgd ON gv.ma_gv = lgd.ma_gv
        WHERE lgd.hoc_ky = :hocKy AND YEAR(lgd.ngay_bd) = :namHoc AND lgd.ma_mh = :maMh
        """, nativeQuery = true)
    List<Object[]> findGiaoVienByHocKyNamAndMonHoc(@Param("hocKy") Integer hocKy,
                                                   @Param("namHoc") Integer namHoc,
                                                   @Param("maMh") String maMh);

    boolean existsBySdt(String sdt);

    boolean existsBySdtAndMaGvNot(String sdt, String maGv);

    boolean existsByEmail(String email);

    boolean existsByEmailAndMaGvNot(String email, String maGv);
}