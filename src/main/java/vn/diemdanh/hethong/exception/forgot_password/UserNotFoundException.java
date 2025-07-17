package vn.diemdanh.hethong.security.forgot_password;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}