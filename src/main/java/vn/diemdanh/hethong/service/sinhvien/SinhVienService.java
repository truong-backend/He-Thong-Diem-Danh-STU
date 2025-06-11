package vn.diemdanh.hethong.service.sinhvien;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.dto.sinhvien.SinhVienDTOProfile;
import vn.diemdanh.hethong.entity.Lop;
import vn.diemdanh.hethong.entity.SinhVien;
import vn.diemdanh.hethong.entity.User;
import vn.diemdanh.hethong.repository.user_man_and_login.SinhVienRepository;
import vn.diemdanh.hethong.repository.user_man_and_login.UserRepository;

import java.util.List;

@Service
public class SinhVienService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    SinhVienRepository sinhVienRepository;
    public SinhVienDTOProfile getSinhVienProfile(String email){
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("Không tìm thấy sinh viên có email " + email));
        SinhVien sv = user.getMaSv();
        SinhVienDTOProfile svProfile = new SinhVienDTOProfile();
        svProfile.setMaSv(sv.getMaSv());
        svProfile.setTenSv(sv.getTenSv());
        svProfile.setTenLop(sv.getMaLop().getTenLop());
        svProfile.setNgaySinh(sv.getNgaySinh());
        svProfile.setPhai(sv.getPhai());
        svProfile.setDiaChi(sv.getDiaChi());
        svProfile.setEmail(sv.getEmail());
        svProfile.setSdt(sv.getSdt());
        svProfile.setAvatar(sv.getAvatar());

        return svProfile;
    }
    public SinhVienDTOProfile maptoDTO(SinhVien sv){
        return new SinhVienDTOProfile(
                sv.getMaSv(),
                sv.getTenSv(),
                sv.getMaLop().getTenLop(),
                sv.getNgaySinh(),
                sv.getPhai(),
                sv.getDiaChi(),
                sv.getEmail(),
                sv.getSdt(),
                sv.getAvatar()
        );
    }
    public SinhVienDTOProfile updateProfileSinhVien(String email,SinhVienDTOProfile profile){
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("Không tìm thấy sinh viên có email " + email));
        SinhVien updateSV = user.getMaSv();
        if(updateSV == null) {
            throw new EntityNotFoundException("Sinh viên không tồn tại");
        }
        updateSV.setDiaChi(profile.getDiaChi());
        updateSV.setSdt(profile.getSdt());
        updateSV.setAvatar(profile.getAvatar());

        SinhVien saved = sinhVienRepository.save(updateSV);
        return maptoDTO(saved);
    }
}
