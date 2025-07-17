package vn.diemdanh.hethong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.diemdanh.hethong.entity.NgayLe;
import vn.diemdanh.hethong.entity.NgayLeId;

import java.util.List;

public interface NgayLeRepository extends JpaRepository<NgayLe, NgayLeId> {
    List<NgayLe> findAll();
} 