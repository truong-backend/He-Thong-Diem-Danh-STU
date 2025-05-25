package vn.diemdanh.hethong.repository.user_man_and_login;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.diemdanh.hethong.entity.User;

import java.util.*;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}