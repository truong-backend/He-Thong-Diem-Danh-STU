package vn.diemdanh.hethong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.diemdanh.hethong.entity.User;
import vn.diemdanh.hethong.repository.UserRepository;
import vn.diemdanh.hethong.security.CustomUserDetails;
import vn.diemdanh.hethong.dto.user.UserDto;
import vn.diemdanh.hethong.exception.forgot_password.ResourceNotFoundException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    // Deprecated method - kept for backward compatibility
    @Deprecated
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // New paginated method
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    // New method to search users with pagination
    public Page<User> searchUsers(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            return userRepository.findByUsernameContainingOrEmailContaining(keyword, keyword, pageable);
        }
        return userRepository.findAll(pageable);
    }

    public User createUser(UserDto userDto) {
        // Check if email already exists
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email đã tồn tại trong hệ thống");
        }

        // Check if username already exists
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username đã tồn tại trong hệ thống");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // Encrypt password
        user.setRole(userDto.getRole());
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        return userRepository.save(user);
    }

    public User updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với ID: " + id));

        // Check if email is being changed and if new email already exists
        if (!user.getEmail().equals(userDto.getEmail()) &&
                userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email đã tồn tại trong hệ thống");
        }

        // Check if username is being changed and if new username already exists
        if (!user.getUsername().equals(userDto.getUsername()) &&
                userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username đã tồn tại trong hệ thống");
        }

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        user.setRole(userDto.getRole());
        user.setUpdatedAt(Instant.now());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với ID: " + id));
        userRepository.delete(user);
    }

    // Sửa phương thức getUsersByRole để hỗ trợ phân trang
    public Page<User> getUsersByRole(String role, Pageable pageable) {
        return userRepository.findByRole(role, pageable);
    }

    // Thêm phương thức searchUsersByRole tương tự như searchUsers
    public Page<User> searchUsersByRole(String role, String keyword, Pageable pageable) {
        if (keyword == null || keyword.isEmpty()) {
            return getUsersByRole(role, pageable);
        }
        return userRepository.findByRoleAndUsernameContainingOrRoleAndEmailContaining(
                role, keyword,
                role, keyword,
                pageable
        );
    }
}