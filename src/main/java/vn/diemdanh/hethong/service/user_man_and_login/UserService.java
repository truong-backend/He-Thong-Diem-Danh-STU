package vn.diemdanh.hethong.service.user_man_and_login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.entity.User;
import vn.diemdanh.hethong.repository.user_man_and_login.UserRepository;
import vn.diemdanh.hethong.security.CustomUserDetails;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        // Kiểm tra xem user có tồn tại trong database không?
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElseThrow(() ->
                new UsernameNotFoundException("Không tìm thấy người dùng với email: " + email)
        );

        return new CustomUserDetails(user);
    }

    // Thêm các phương thức để lấy thông tin người dùng hiện tại
    public User getCurrentUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Không tìm thấy người dùng với email: " + email)
                );
    }
    // Phương thức mới để lấy user theo ID (cần cho JwtAuthenticationFilter)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Không tìm thấy người dùng với ID: " + userId)
                );
    }

    // Phương thức để load user details theo ID
    public UserDetails loadUserById(Long userId) {
        User user = getUserById(userId);
        return new CustomUserDetails(user);
    }
}