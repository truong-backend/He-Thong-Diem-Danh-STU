package vn.diemdanh.hethong.controller;

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
import vn.diemdanh.hethong.service.user_man_and_login.UserService;
import vn.diemdanh.hethong.service.user_man_and_login.AdminService;

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

    @PostMapping("/user/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
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

        } catch (BadCredentialsException e) {
            log.error("Invalid user credentials for email: {}", loginRequest.getEmail());
            return ResponseEntity.badRequest().body("Invalid email or password");
        } catch (Exception e) {
            log.error("Error during user login for email: {}: {}", loginRequest.getEmail(), e.getMessage());
            return ResponseEntity.badRequest().body("Error during login: " + e.getMessage());
        }
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> authenticateAdmin(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            log.info("Attempting admin login for email: {}", loginRequest.getEmail());
            
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

        } catch (BadCredentialsException e) {
            log.error("Invalid admin credentials for email: {}", loginRequest.getEmail());
            return ResponseEntity.badRequest().body("Invalid email or password");
        } catch (Exception e) {
            log.error("Error during admin login for email: {}: {}", loginRequest.getEmail(), e.getMessage());
            return ResponseEntity.badRequest().body("Error during login: " + e.getMessage());
        }
    }
} 