package vn.diemdanh.hethong.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.diemdanh.hethong.entity.GiaoVien;

public interface GiaoVienRepository extends JpaRepository<GiaoVien, String> {
    Page<GiaoVien> findByMaGvContainingIgnoreCase(String maGv, Pageable pageable);

    @Query("SELECT gv.maGv FROM GiaoVien gv WHERE gv.email = :email")
    String findMaGvByEmail(@Param("email") String email);
}