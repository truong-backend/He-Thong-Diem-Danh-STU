package vn.diemdanh.hethong.controller.auth;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import vn.diemdanh.hethong.dto.login.LoginRequest;
import vn.diemdanh.hethong.dto.login.UserLoginResponse;
import vn.diemdanh.hethong.dto.login.AdminLoginResponse;
import vn.diemdanh.hethong.security.CustomUserDetails;
import vn.diemdanh.hethong.security.CustomAdminDetails;
import vn.diemdanh.hethong.security.JwtTokenProvider;
import vn.diemdanh.hethong.service.UserService;
import vn.diemdanh.hethong.service.AdminService;

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

    // ========================== SINH VIÊN LOGIN ==========================

    @PostMapping("/sinhvien/login")
    public ResponseEntity<?> authenticateSinhVien(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            log.info("Attempting user login for email: {}", loginRequest.getEmail());



            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String jwt = tokenProvider.generateToken(userDetails);

            UserLoginResponse response = new UserLoginResponse(
                    jwt,
                    userDetails.getId(),
                    userDetails.getUserRealUsername(),
                    userDetails.getUsername(),
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

    // ========================== GIẢNG VIÊN LOGIN ==========================

    @PostMapping("/giangvien/login")
    public ResponseEntity<?> authenticateGiangVien(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            log.info("Attempting user login for email: {}", loginRequest.getEmail());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String jwt = tokenProvider.generateToken(userDetails);

            UserLoginResponse response = new UserLoginResponse(
                    jwt,
                    userDetails.getId(),
                    userDetails.getUserRealUsername(),
                    userDetails.getUsername(),
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

    // ========================== ADMIN LOGIN ==========================

    @PostMapping("/admin/login")
    public ResponseEntity<?> authenticateAdmin(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            log.info("Attempting admin login for email: {}", loginRequest.getEmail());

            if (loginRequest.getEmail().endsWith("@student.stu.edu.vn")) {
                return ResponseEntity.badRequest()
                        .body("Tài khoản sinh viên không được phép đăng nhập vào trang quản trị");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            CustomAdminDetails adminDetails = (CustomAdminDetails) authentication.getPrincipal();
            String jwt = tokenProvider.generateTokenForAdmin(adminDetails);

            AdminLoginResponse response = new AdminLoginResponse(
                    jwt,
                    adminDetails.getId(),
                    adminDetails.getUserRealUsername(),
                    adminDetails.getUsername(),
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
