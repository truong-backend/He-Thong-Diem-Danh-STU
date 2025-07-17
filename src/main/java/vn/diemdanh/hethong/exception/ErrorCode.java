package vn.diemdanh.hethong.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@AllArgsConstructor
@Getter
public enum ErrorCode {
    PASSED_DIEMDANH_LAN1(460,"SINH VIÊN ĐÃ ĐIỂM DANH RỒI"),
    PASSED_DIEMDANH_ALL(461,"SINH VIÊN ĐÃ ĐIỂM DANH ĐỦ 2 LẦN TRONG BUỔI HỌC HÔM NAY"),
    SINHVIEN_NOTEXIST_TKB(462,"SINH VIÊN KHÔNG CÓ THỜI KHÓA BIỂU TRONG BUỔI HỌC NÀY");
    final int statusCode;
    final String message;
}
