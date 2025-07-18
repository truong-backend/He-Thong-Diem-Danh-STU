package vn.diemdanh.hethong.exception.forgot_password;

public class OtpInvalidException extends RuntimeException {
    public OtpInvalidException(String message) {
        super(message);
    }
}

