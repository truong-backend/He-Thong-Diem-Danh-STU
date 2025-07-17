package vn.diemdanh.hethong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.diemdanh.hethong.entity.DiemDanhLog;

import java.time.LocalDate;

public interface DiemDanhLogRepository extends JpaRepository<DiemDanhLog, Integer> {
    //Số lần điểm danh ở service
    @Query(value = """
        select coalesce(max(dl.lan_diem_danh),0) from diem_danh d join diem_danh_log dl 
                            on d.ma_dd = dl.ma_dd
            where d.ma_tkb = :maTkb and d.ma_sv = :maSv
            and d.ngay_hoc = :ngayHoc
    """,nativeQuery = true)
    Integer getSoLanDiemDanh(@Param("maTkb")int maTkb, @Param("maSv")String maSv, @Param("ngayHoc") LocalDate ngayHoc);

}
