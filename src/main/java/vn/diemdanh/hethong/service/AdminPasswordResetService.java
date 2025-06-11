package vn.diemdanh.hethong.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.entity.Admin;
import vn.diemdanh.hethong.entity.AdminPasswordReset;
import vn.diemdanh.hethong.exception.forgot_password.OtpExpiredException;
import vn.diemdanh.hethong.exception.forgot_password.OtpInvalidException;
import vn.diemdanh.hethong.exception.forgot_password.UserNotFoundException;
import vn.diemdanh.hethong.repository.forgot_password.AdminPasswordResetRepository;
import vn.diemdanh.hethong.repository.user_man_and_login.AdminRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class AdminPasswordResetService {

    @Autowired
    private AdminPasswordResetRepository adminPasswordResetRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Thời gian hiệu lực của OTP (3 phút)
    private static final long OTP_EXPIRATION_MINUTES = 3;

    /**
     * Tạo OTP ngẫu nhiên 6 số và gửi qua email cho Admin
     */
    @Transactional
    public void createAndSendOtp(String email) throws UserNotFoundException {
        // Kiểm tra xem email admin có tồn tại không
        if (!adminRepository.findByEmail(email).isPresent()) {
            throw new UserNotFoundException("Không tìm thấy admin với email: " + email);
        }

        // Tạo OTP 6 số ngẫu nhiên
        String otp = generateRandomOtp();

        // Lưu OTP vào database
        saveOtp(email, otp);

        // Gửi OTP qua email
        sendOtpEmail(email, otp);
    }

    /**
     * Xác minh OTP và đặt lại mật khẩu cho Admin
     */
    @Transactional
    public void verifyOtpAndResetPassword(String email, String otp, String newPassword) {
        // Lấy thông tin OTP từ database
        Optional<AdminPasswordReset> passwordResetOptional = adminPasswordResetRepository.findById(email);

        if (!passwordResetOptional.isPresent()) {
            throw new OtpInvalidException("Không tìm thấy yêu cầu đặt lại mật khẩu cho email admin này");
        }

        AdminPasswordReset passwordReset = passwordResetOptional.get();

        // Kiểm tra xem OTP có hợp lệ không
        if (!passwordEncoder.matches(otp, passwordReset.getToken())) {
            throw new OtpInvalidException("Mã OTP không chính xác");
        }

        // Kiểm tra xem OTP có còn hiệu lực không
        if (isOtpExpired(passwordReset.getCreatedAt())) {
            throw new OtpExpiredException("Mã OTP đã hết hạn");
        }

        // Cập nhật mật khẩu mới cho admin
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);
        if (!adminOptional.isPresent()) {
            throw new UserNotFoundException("Không tìm thấy admin với email: " + email);
        }

        Admin admin = adminOptional.get();
        admin.setPassword(passwordEncoder.encode(newPassword));
        admin.setUpdatedAt(Instant.now());
        adminRepository.save(admin);

        // Xóa bản ghi OTP sau khi đặt lại mật khẩu thành công
        adminPasswordResetRepository.deleteById(email);
    }

    /**
     * Tạo OTP ngẫu nhiên 6 số
     */
    private String generateRandomOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Tạo số ngẫu nhiên từ 100000 đến 999999
        return String.valueOf(otp);
    }

    /**
     * Lưu OTP vào database cho admin
     */
    private void saveOtp(String email, String otp) {
        AdminPasswordReset passwordReset = new AdminPasswordReset();
        passwordReset.setEmail(email);
        passwordReset.setToken(passwordEncoder.encode(otp)); // Mã hóa OTP trước khi lưu
        passwordReset.setCreatedAt(Instant.now());

        adminPasswordResetRepository.save(passwordReset);
    }

    /**
     * Gửi OTP qua email cho admin
     */
    private void sendOtpEmail(String email, String otp) {
        String subject = "Mã OTP đặt lại mật khẩu Admin";
        String content = "Xin chào Admin,\n\n"
                + "Mã OTP để đặt lại mật khẩu admin của bạn là: " + otp + "\n"
                + "Mã này sẽ hết hạn sau " + OTP_EXPIRATION_MINUTES + " phút.\n\n"
                + "Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.\n\n"
                + "Trân trọng,\n"
                + "Hệ thống điểm danh";

        emailService.sendEmail(email, subject, content);
    }

    /**
     * Kiểm tra xem OTP có hết hạn không
     */
    private boolean isOtpExpired(Instant createdAt) {
        Instant now = Instant.now();
        Duration duration = Duration.between(createdAt, now);
        return duration.toMinutes() > OTP_EXPIRATION_MINUTES;
    }
}