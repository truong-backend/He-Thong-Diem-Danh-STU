package vn.diemdanh.hethong.repository.user_man_and_login;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.diemdanh.hethong.dto.lichgd.LichGdDto;
import vn.diemdanh.hethong.entity.GiaoVien;
import vn.diemdanh.hethong.entity.LichGd;
import vn.diemdanh.hethong.entity.MonHoc;

import java.util.List;

public interface LichGdRepository extends JpaRepository<LichGd, Long> {
    @Query("Select new vn.diemdanh.hethong.dto.lichgd.LichGdDto(gd.id,gv.maGv,gv.tenGv,mh.maMh,mh.tenMh,gd.nmh,gd.phongHoc,gd.ngayBd,gd.ngayKt,gd.stBd,gd.stKt,gd.hocKy) "
        +"From LichGd gd join gd.maGv gv join gd.maMh mh where gd.maGv.maGv = :maGv "
    )
    List<LichGdDto> getLichGiangDayByIdGV(@Param("maGv")String maGv);
    Page<LichGd> findByMaGv(GiaoVien giaoVien, Pageable pageable);
    Page<LichGd> findByMaMh(MonHoc monHoc, Pageable pageable);
    Page<LichGd> findByHocKy(Integer hocKy, Pageable pageable);
} 