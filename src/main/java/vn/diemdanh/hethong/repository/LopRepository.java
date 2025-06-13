package vn.diemdanh.hethong.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.diemdanh.hethong.entity.Khoa;
import vn.diemdanh.hethong.entity.Lop;

public interface LopRepository extends JpaRepository<Lop, String> {
    Page<Lop> findByMaKhoa(Khoa maKhoa, Pageable pageable);
}