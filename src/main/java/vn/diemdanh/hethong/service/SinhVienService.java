package vn.diemdanh.hethong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.dto.sinhvien.CreateSinhVienRequest;
import vn.diemdanh.hethong.entity.Lop;
import vn.diemdanh.hethong.entity.SinhVien;
import vn.diemdanh.hethong.entity.User;
import vn.diemdanh.hethong.repository.LopRepository;
import vn.diemdanh.hethong.repository.SinhVienRepository;
import vn.diemdanh.hethong.repository.UserRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SinhVienService {

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LopRepository lopRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createSinhVien(CreateSinhVienRequest request) {
        // Kiểm tra mã sinh viên
        if (sinhVienRepository.findById(request.getMaSv()).isPresent()) {
            throw new RuntimeException("Mã sinh viên đã tồn tại");
        }

        // Kiểm tra email
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã tồn tại");
        }

        // Kiểm tra lớp
        Lop lop = lopRepository.findById(request.getMaLop())
                .orElseThrow(() -> new RuntimeException("Lớp không tồn tại"));

        // Tạo sinh viên
        SinhVien sinhVien = new SinhVien();
        sinhVien.setMaSv(request.getMaSv());
        sinhVien.setTenSv(request.getTenSv());
        sinhVien.setNgaySinh(request.getNgaySinh());
        sinhVien.setPhai(request.getPhai());
        sinhVien.setDiaChi(request.getDiaChi());
        sinhVien.setSdt(request.getSdt());
        sinhVien.setEmail(request.getEmail());
        sinhVien.setMaLop(lop);

        // Lưu sinh viên
        sinhVienRepository.save(sinhVien);

        User user = new User();
        user.setUsername(request.getMaSv());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getMaSv()));
        user.setRole("student");
        user.setMaSv(sinhVien);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        userRepository.save(user);
    }

    public List<SinhVien> findAll() {
        return sinhVienRepository.findAll();
    }

    //Lấy danh sách sinh viên theo ngày giảng dạy của nhóm môn học đó của môn học đó của giảng viên đó trong học kỳ đó

    public List<Map<String, Object>> getSinhVienTheoLich(
            String maGv,
            String maMh,
            int nhom,
            String hocKy,
            LocalDate ngayHoc
    ) {
        List<Object[]> rawResults = sinhVienRepository.findSinhVienTheoLichGiangDay(maGv, maMh, nhom, hocKy, ngayHoc);

        return rawResults.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("ma_sv", row[0]);
            map.put("ten_sv", row[1]);
            map.put("email", row[2]);
            map.put("ma_lop", row[3]);
            return map;
        }).collect(Collectors.toList());
    }

}
