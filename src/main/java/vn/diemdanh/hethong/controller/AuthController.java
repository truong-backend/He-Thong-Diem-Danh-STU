package vn.diemdanh.hethong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.login.LoginRequest;
import vn.diemdanh.hethong.dto.login.UserLoginResponse;
import vn.diemdanh.hethong.security.CustomUserDetails;
import vn.diemdanh.hethong.security.JwtTokenProvider;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Xác thực từ email và password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // Nếu không xảy ra exception tức là thông tin hợp lệ
            // Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Lấy thông tin user từ authentication
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            // Trả về jwt cùng với thông tin user
            String jwt = tokenProvider.generateToken(userDetails);

            // Tạo response với đầy đủ thông tin
            UserLoginResponse userLoginResponse = new UserLoginResponse(
                    jwt,
                    userDetails.getId(),
                    userDetails.getUserRealUsername(),
                    userDetails.getUsername(), // Đây là email
                    userDetails.getRole()
            );

            return ResponseEntity.ok(userLoginResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Thông tin đăng nhập không chính xác");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi đăng nhập: " + e.getMessage());
        }
    }

    // Api /api/random yêu cầu phải xác thực mới có thể request
    @GetMapping("/random")
    public ResponseEntity<?> randomStuff(){
        try {
            // Lấy thông tin user hiện tại từ SecurityContext
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            return ResponseEntity.ok(new RandomStuff(
                    "JWT Hợp lệ mới có thể thấy được message này. Xin chào " + userDetails.getUserRealUsername()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi xác thực: " + e.getMessage());
        }
    }

    // Inner class cho RandomStuff response
    public static class RandomStuff {
        private String message;

        public RandomStuff(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}