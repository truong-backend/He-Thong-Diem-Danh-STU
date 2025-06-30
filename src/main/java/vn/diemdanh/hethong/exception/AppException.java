package vn.diemdanh.hethong.exception;

public class AppException extends RuntimeException{
    private final ErrorCode error;

    public AppException(ErrorCode error) {
        super(error.getMessage());
        this.error = error;
    }

    public ErrorCode getError() {
        return error;
    }
    public int getCodeStatus(){
        return error.getStatusCode();
    }
}
