package vn.diemdanh.hethong.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.dto.giaovien.CreateGiaoVienRequest;
import vn.diemdanh.hethong.dto.giaovien.GiaoVienDTO_Profile;
import vn.diemdanh.hethong.dto.giaovien.CreateGiaoVienRequest;
import vn.diemdanh.hethong.entity.GiaoVien;
import vn.diemdanh.hethong.entity.Lop;
import vn.diemdanh.hethong.entity.GiaoVien;
import vn.diemdanh.hethong.entity.User;
import vn.diemdanh.hethong.repository.GiaoVienRepository;
import vn.diemdanh.hethong.repository.UserRepository;

import java.time.Instant;

@Service
public class GiaoVienService {
    @Autowired
    GiaoVienRepository GiaoVienRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private GiaoVienRepository giaoVienRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public GiaoVienDTO_Profile getGiaoVienByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("Không tìm thấy user hoặc email"));
        //Entity User có GiaoVien maGV => getGiaoVien();
        GiaoVien gv = user.getMaGv();

        GiaoVienDTO_Profile gvDTO = new GiaoVienDTO_Profile();
        gvDTO.setMaGv(gv.getMaGv());
        gvDTO.setTenGv(gv.getTenGv());
        gvDTO.setNgaySinh(gv.getNgaySinh());
        gvDTO.setPhai(gv.getPhai());
        gvDTO.setDiaChi(gv.getDiaChi());
        gvDTO.setSdt(gv.getSdt());
        gvDTO.setEmail(gv.getEmail());
        gvDTO.setAvatar(gv.getAvatar());
        return gvDTO;
    }

    private GiaoVienDTO_Profile maptoDTO(GiaoVien gv){
        return new GiaoVienDTO_Profile(
                gv.getMaGv(),
                gv.getTenGv(),
                gv.getNgaySinh(),
                gv.getPhai(),
                gv.getDiaChi(),
                gv.getSdt(),
                gv.getEmail(),
                gv.getAvatar()
        );
    }

    public GiaoVienDTO_Profile updateProfileGiaoVien(String email,GiaoVienDTO_Profile gvDTO) {
      User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy user hoặc email"));

      GiaoVien updateGV = user.getMaGv();
      updateGV.setDiaChi(gvDTO.getDiaChi());
      updateGV.setSdt(gvDTO.getSdt());

      GiaoVien saved = giaoVienRepository.save(updateGV);
      return maptoDTO(saved);
    }
    public void createGiaoVien(CreateGiaoVienRequest request) {
        // Kiểm tra mã sinh viên
        if (giaoVienRepository.findById(request.getMaGv()).isPresent()) {
            throw new RuntimeException("Mã sinh viên đã tồn tại");
        }

        // Kiểm tra email
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã tồn tại");
        }
        
        // Tạo sinh viên
        GiaoVien gv = new GiaoVien();
        gv.setMaGv(request.getMaGv());
        gv.setTenGv(request.getTenGv());
        gv.setNgaySinh(request.getNgaySinh());
        gv.setPhai(request.getPhai());
        gv.setDiaChi(request.getDiaChi());
        gv.setSdt(request.getSdt());
        gv.setEmail(request.getEmail());

        // Lưu sinh viên
        GiaoVienRepository.save(gv);

        User user = new User();
        user.setUsername(request.getMaGv());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getMaGv()));
        user.setRole("teacher");
        user.setMaGv(gv);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        userRepository.save(user);
    }
}
