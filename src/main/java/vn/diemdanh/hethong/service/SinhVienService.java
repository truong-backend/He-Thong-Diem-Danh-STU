package vn.diemdanh.hethong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.dto.sinhvien.CreateSinhVienRequest;
import vn.diemdanh.hethong.entity.Lop;
import vn.diemdanh.hethong.entity.SinhVien;
import vn.diemdanh.hethong.entity.User;
import vn.diemdanh.hethong.repository.user_man_and_login.LopRepository;
import vn.diemdanh.hethong.repository.user_man_and_login.SinhVienRepository;
import vn.diemdanh.hethong.repository.user_man_and_login.UserRepository;

import java.time.Instant;

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
}
