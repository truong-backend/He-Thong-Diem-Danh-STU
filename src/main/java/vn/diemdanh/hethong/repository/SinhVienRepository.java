package vn.diemdanh.hethong.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.diemdanh.hethong.entity.SinhVien;

public interface SinhVienRepository extends JpaRepository<SinhVien, String> {
    Page<SinhVien> findByMaSvContainingIgnoreCase(String maSv, Pageable pageable);
}