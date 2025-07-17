package vn.diemdanh.hethong.controller.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.diemdanh.hethong.dto.login.LoginRequest;
import vn.diemdanh.hethong.dto.login.UserLoginResponse;
import vn.diemdanh.hethong.dto.login.AdminLoginResponse;
import vn.diemdanh.hethong.security.CustomUserDetails;
import vn.diemdanh.hethong.security.CustomAdminDetails;
import vn.diemdanh.hethong.security.JwtTokenProvider;
import vn.diemdanh.hethong.service.UserService;
import vn.diemdanh.hethong.service.AdminService;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/sinhvien/login")
    public ResponseEntity<?> authenticateSinhVien(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            log.info("Attempting user login for email: {}", loginRequest.getEmail());
            
            // Kiểm tra xem có phải email sinh viên không
            if (!loginRequest.getEmail().endsWith("@student.stu.edu.vn")) {
                return ResponseEntity.badRequest().body("Email không hợp lệ. Vui lòng sử dụng email sinh viên (@student.stu.edu.vn)");
            }
            
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );

            // Get user details
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            
            // Generate JWT token
            String jwt = tokenProvider.generateToken(userDetails);
            
            // Create response
            UserLoginResponse response = new UserLoginResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUserRealUsername(),
                userDetails.getUsername(), // email
                userDetails.getRole()
            );

            log.info("User login successful for email: {}", loginRequest.getEmail());
            return ResponseEntity.ok(response);

        } catch (ClassCastException e) {
            log.error("Invalid account type for user login: {}", loginRequest.getEmail());
            return ResponseEntity.badRequest().body("Tài khoản không hợp lệ cho đăng nhập sinh viên");
        } catch (BadCredentialsException e) {
            log.error("Invalid user credentials for email: {}", loginRequest.getEmail());
            return ResponseEntity.badRequest().body("Email hoặc mật khẩu không chính xác");
        } catch (Exception e) {
            log.error("Error during user login for email: {}: {}", loginRequest.getEmail(), e.getMessage());
            return ResponseEntity.badRequest().body("Lỗi đăng nhập: " + e.getMessage());
        }
    }

    @PostMapping("/giangvien/login")
    public ResponseEntity<?> authenticateGiangVien(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            log.info("Attempting user login for email: {}", loginRequest.getEmail());


            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // Get user details
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            // Generate JWT token
            String jwt = tokenProvider.generateToken(userDetails);

            // Create response
            UserLoginResponse response = new UserLoginResponse(
                    jwt,
                    userDetails.getId(),
                    userDetails.getUserRealUsername(),
                    userDetails.getUsername(), // email
                    userDetails.getRole()
            );

            log.info("User login successful for email: {}", loginRequest.getEmail());
            return ResponseEntity.ok(response);

        } catch (ClassCastException e) {
            log.error("Invalid account type for user login: {}", loginRequest.getEmail());
            return ResponseEntity.badRequest().body("Tài khoản không hợp lệ cho đăng nhập giảng viên");
        } catch (BadCredentialsException e) {
            log.error("Invalid user credentials for email: {}", loginRequest.getEmail());
            return ResponseEntity.badRequest().body("Email hoặc mật khẩu không chính xác");
        } catch (Exception e) {
            log.error("Error during user login for email: {}: {}", loginRequest.getEmail(), e.getMessage());
            return ResponseEntity.badRequest().body("Lỗi đăng nhập: " + e.getMessage());
        }
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> authenticateAdmin(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            log.info("Attempting admin login for email: {}", loginRequest.getEmail());
            
            // Kiểm tra xem email có phải là email sinh viên không
            if (loginRequest.getEmail().endsWith("@student.stu.edu.vn")) {
                return ResponseEntity.badRequest().body("Tài khoản sinh viên không được phép đăng nhập vào trang quản trị");
            }
            
            // Authenticate admin
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );

            // Get admin details
            CustomAdminDetails adminDetails = (CustomAdminDetails) authentication.getPrincipal();
            
            // Generate JWT token
            String jwt = tokenProvider.generateTokenForAdmin(adminDetails);
            
            // Create response
            AdminLoginResponse response = new AdminLoginResponse(
                jwt,
                adminDetails.getId(),
                adminDetails.getUserRealUsername(),
                adminDetails.getUsername(), // email
                adminDetails.getFullName(),
                adminDetails.getRole()
            );

            log.info("Admin login successful for email: {}", loginRequest.getEmail());
            return ResponseEntity.ok(response);

        } catch (ClassCastException e) {
            log.error("Invalid account type for admin login: {}", loginRequest.getEmail());
            return ResponseEntity.badRequest().body("Tài khoản không có quyền truy cập trang quản trị");
        } catch (BadCredentialsException e) {
            log.error("Invalid admin credentials for email: {}", loginRequest.getEmail());
            return ResponseEntity.badRequest().body("Email hoặc mật khẩu không chính xác");
        } catch (Exception e) {
            log.error("Error during admin login for email: {}: {}", loginRequest.getEmail(), e.getMessage());
            return ResponseEntity.badRequest().body("Lỗi đăng nhập: " + e.getMessage());
        }
    }
} 