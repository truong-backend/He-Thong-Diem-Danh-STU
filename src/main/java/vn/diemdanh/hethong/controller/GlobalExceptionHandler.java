package vn.diemdanh.hethong.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import vn.diemdanh.hethong.exception.AppException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> handle(AppException ex) {
       return ResponseEntity.
               status(ex.getCodeStatus()).
               body(Map.of("message", ex.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOther(Exception ex) {
        return ResponseEntity
                .status(500)
                .body(Map.of("message", "Lỗi hệ thống"));
    }
}
