package vn.diemdanh.hethong.exception.forgot_password;

public class OtpExpiredException extends RuntimeException {
    public OtpExpiredException(String message) {
        super(message);
    }
}