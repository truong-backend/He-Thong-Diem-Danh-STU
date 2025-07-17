package vn.diemdanh.hethong.security.forgot_password;

public class OtpExpiredException extends RuntimeException {
    public OtpExpiredException(String message) {
        super(message);
    }
}