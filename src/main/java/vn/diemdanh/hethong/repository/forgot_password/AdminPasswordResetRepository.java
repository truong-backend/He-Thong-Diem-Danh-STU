package vn.diemdanh.hethong.repository.forgot_password;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.diemdanh.hethong.entity.AdminPasswordReset;

public interface AdminPasswordResetRepository extends JpaRepository<AdminPasswordReset, String> {
}