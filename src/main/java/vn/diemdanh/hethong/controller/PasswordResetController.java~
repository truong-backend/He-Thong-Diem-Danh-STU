package vn.diemdanh.hethong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.forgot_password.YeuCauOTP;
import vn.diemdanh.hethong.dto.forgot_password.YeuCauXacThucOPT;
import vn.diemdanh.hethong.exception.forgot_password.OtpExpiredException;
import vn.diemdanh.hethong.exception.forgot_password.OtpInvalidException;
import vn.diemdanh.hethong.exception.forgot_password.UserNotFoundException;
import vn.diemdanh.hethong.service.forgot_password.AdminPasswordResetService;
import vn.diemdanh.hethong.service.forgot_password.PasswordResetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/password-reset")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private AdminPasswordResetService adminPasswordResetService;

    /**
     * Endpoint gửi OTP qua email cho User
     */
    @PostMapping("/user/request-otp")
    public ResponseEntity<?> sendOtpForUser(@Valid @RequestBody YeuCauOTP yeuCauOTP) {
        try {
            passwordResetService.createAndSendOtp(yeuCauOTP.getEmail());
            return ResponseEntity.ok(new MessageResponse("Mã OTP đã được gửi đến email của bạn. Vui lòng kiểm tra hộp thư."));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Lỗi khi gửi OTP: " + e.getMessage()));
        }
    }

    /**
     * Endpoint xác thực OTP và đặt lại mật khẩu cho User
     */
    @PostMapping("/user/verify-otp")
    public ResponseEntity<?> resetPasswordForUser(@Valid @RequestBody YeuCauXacThucOPT yeuCauXacThuc) {
        try {
            passwordResetService.verifyOtpAndResetPassword(
                    yeuCauXacThuc.getEmail(),
                    yeuCauXacThuc.getOtp(),
                    yeuCauXacThuc.getNewPassword()
            );
            return ResponseEntity.ok(new MessageResponse("Mật khẩu đã được đặt lại thành công."));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        } catch (OtpInvalidException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        } catch (OtpExpiredException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Lỗi khi đặt lại mật khẩu: " + e.getMessage()));
        }
    }

    /**
     * Endpoint gửi OTP qua email cho Admin
     */
    @PostMapping("/admin/request-otp")
    public ResponseEntity<?> sendOtpForAdmin(@Valid @RequestBody YeuCauOTP yeuCauOTP) {
        try {
            adminPasswordResetService.createAndSendOtp(yeuCauOTP.getEmail());
            return ResponseEntity.ok(new MessageResponse("Mã OTP đã được gửi đến email admin của bạn. Vui lòng kiểm tra hộp thư."));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Lỗi khi gửi OTP admin: " + e.getMessage()));
        }
    }

    /**
     * Endpoint xác thực OTP và đặt lại mật khẩu cho Admin
     */
    @PostMapping("/admin/verify-otp")
    public ResponseEntity<?> resetPasswordForAdmin(@Valid @RequestBody YeuCauXacThucOPT yeuCauXacThuc) {
        try {
            adminPasswordResetService.verifyOtpAndResetPassword(
                    yeuCauXacThuc.getEmail(),
                    yeuCauXacThuc.getOtp(),
                    yeuCauXacThuc.getNewPassword()
            );
            return ResponseEntity.ok(new MessageResponse("Mật khẩu admin đã được đặt lại thành công."));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        } catch (OtpInvalidException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        } catch (OtpExpiredException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Lỗi khi đặt lại mật khẩu admin: " + e.getMessage()));
        }
    }

    /**
     * Inner class for message responses
     */
    private static class MessageResponse {
        private String message;

        public MessageResponse(String message) {
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