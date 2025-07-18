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
    SINHVIEN_NOTEXIST_TKB(462,"SINH VIÊN KHÔNG CÓ THỜI KHÓA BIỂU TRONG BUỔI HỌC NÀY"),
    SINHVIEN_NOTEXIST(463, "KHÔNG TÌM THẤY SINH VIÊN"),
    LICHGD_NOTEXIST(464, "KHÔNG TÌM THẤY LỊCH GIẢNG DẠY"),
    DUPLICATE_ENTRY(465, "SINH VIÊN ĐÃ ĐƯỢC THÊM VÀO LỊCH HỌC NÀY"),
    INTERNAL_SERVER_ERROR(500, "LỖI HỆ THỐNG");

    final int statusCode;
    final String message;
}
