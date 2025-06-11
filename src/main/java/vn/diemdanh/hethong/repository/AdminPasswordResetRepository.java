package vn.diemdanh.hethong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.diemdanh.hethong.entity.AdminPasswordReset;

public interface AdminPasswordResetRepository extends JpaRepository<AdminPasswordReset, String> {
}