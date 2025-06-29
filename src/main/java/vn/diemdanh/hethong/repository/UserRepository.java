package vn.diemdanh.hethong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.diemdanh.hethong.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    
    // Search method with pagination
    Page<User> findByUsernameContainingOrEmailContaining(String username, String email, Pageable pageable);

    // Thêm phương thức tìm kiếm theo role có phân trang
    Page<User> findByRole(String role, Pageable pageable);

    // Thêm phương thức search theo role và keyword
    Page<User> findByRoleAndUsernameContainingOrRoleAndEmailContaining(
            String role1, String username,
            String role2, String email,
            Pageable pageable
    );
    List<User> findByRole(String role);

    // Tìm kiếm theo username, email, tên sinh viên hoặc tên giáo viên
    @Query("SELECT u FROM User u LEFT JOIN u.maSv sv LEFT JOIN u.maGv gv WHERE " +
            "(LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(sv.tenSv) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(gv.tenGv) LIKE LOWER(CONCAT('%', :keyword, '%')))" )
    Page<User> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // Tìm kiếm theo role và username, email, tên sinh viên hoặc tên giáo viên
    @Query("SELECT u FROM User u LEFT JOIN u.maSv sv LEFT JOIN u.maGv gv WHERE u.role = :role AND (" +
            "LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(sv.tenSv) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(gv.tenGv) LIKE LOWER(CONCAT('%', :keyword, '%')))" )
    Page<User> searchByRoleAndKeyword(@Param("role") String role, @Param("keyword") String keyword, Pageable pageable);
}