package vn.diemdanh.hethong.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.diemdanh.hethong.entity.DiemDanh;
import vn.diemdanh.hethong.entity.Tkb;

import java.time.LocalDate;

public interface DiemDanhRepository extends JpaRepository<DiemDanh, Long> {

    Page<DiemDanh> findByMaTkb(Tkb tkb, Pageable pageable);
    Page<DiemDanh> findByMaSv(String maSv, Pageable pageable);
    Page<DiemDanh> findByMaTkbAndMaSv(Tkb tkb, String maSv, Pageable pageable);
    Page<DiemDanh> findByNgayHoc(LocalDate ngayHoc, Pageable pageable);
    Page<DiemDanh> findByNgayHocBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
    Page<DiemDanh> findByMaSvAndNgayHocBetween(String maSv, LocalDate startDate, LocalDate endDate, Pageable pageable);
} 