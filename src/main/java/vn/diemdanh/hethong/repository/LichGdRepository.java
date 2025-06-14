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


} 