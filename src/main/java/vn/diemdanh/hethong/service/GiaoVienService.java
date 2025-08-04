package vn.diemdanh.hethong.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.dto.giaovien.CreateGiaoVienRequest;
import vn.diemdanh.hethong.dto.giaovien.GiaoVienDTO_Profile;
import vn.diemdanh.hethong.dto.giaovien.GiaoVienDto;
import vn.diemdanh.hethong.dto.giaovien.GiaoVienInfo;
import vn.diemdanh.hethong.dto.giaovien.UpdateGiaoVienRequest;
import vn.diemdanh.hethong.dto.user.UserDto;
import vn.diemdanh.hethong.entity.GiaoVien;
import vn.diemdanh.hethong.entity.User;
import vn.diemdanh.hethong.repository.GiaoVienRepository;
import vn.diemdanh.hethong.repository.UserRepository;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiaoVienService {

    @Autowired
    private GiaoVienRepository giaoVienRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public GiaoVienDTO_Profile getGiaoVienByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("Không tìm thấy user hoặc email"));
        GiaoVien gv = user.getMaGv();
        return mapToDTO(gv);
    }

    public GiaoVienDTO_Profile updateProfileGiaoVien(String email, GiaoVienDTO_Profile gvDTO) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("Không tìm thấy user hoặc email"));
        GiaoVien updateGV = user.getMaGv();
        updateGV.setDiaChi(gvDTO.getDiaChi());
        updateGV.setSdt(gvDTO.getSdt());
        GiaoVien saved = giaoVienRepository.save(updateGV);
        return mapToDTO(saved);
    }

    public void createGiaoVien(CreateGiaoVienRequest request) {
        if (giaoVienRepository.existsById(request.getMaGv())) {
            throw new RuntimeException("Mã giáo viên đã tồn tại");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã tồn tại");
        }

        GiaoVien giaoVien = new GiaoVien();
        giaoVien.setMaGv(request.getMaGv());
        giaoVien.setTenGv(request.getTenGv());
        giaoVien.setNgaySinh(request.getNgaySinh());
        giaoVien.setPhai(request.getPhai());
        giaoVien.setDiaChi(request.getDiaChi());
        giaoVien.setSdt(request.getSdt());
        giaoVien.setEmail(request.getEmail());
        giaoVien = giaoVienRepository.save(giaoVien);

        UserDto userDto = new UserDto();
        userDto.setUsername(request.getMaGv());
        userDto.setEmail(request.getEmail());
        userDto.setPassword(request.getMaGv());
        userDto.setRole("teacher");

        User user = userService.createUser(userDto);
        user.setMaGv(giaoVien);
        userRepository.save(user);
    }

    public Page<GiaoVienDto> getAllGiaoVien(int page, int size, String[] sort, String search) {
        String sortField = sort[0];
        String sortDirection = sort.length > 1 ? sort[1] : "asc";

        if (!isValidSortField(sortField)) {
            throw new IllegalArgumentException(
                    "Trường sắp xếp không hợp lệ. Các trường hợp lệ: maGv, tenGv, ngaySinh, email");
        }

        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        Page<GiaoVien> giaoViens;
        if (search != null && !search.trim().isEmpty()) {
            giaoViens = giaoVienRepository.findByMaGvContainingIgnoreCase(search.trim(), pageable);
        } else {
            giaoViens = giaoVienRepository.findAll(pageable);
        }

        return giaoViens.map(this::convertToDto);
    }

    public GiaoVienDto getGiaoVienByMaGv(String maGv) {
        GiaoVien giaoVien = giaoVienRepository.findById(maGv)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giáo viên"));
        GiaoVienDto dto = convertToDto(giaoVien);
        Optional<User> userOpt = userRepository.findByEmail(giaoVien.getEmail());
        dto.setHasAccount(userOpt.isPresent());
        return dto;
    }

    public void updateGiaoVien(String maGv, UpdateGiaoVienRequest request) {
        GiaoVien giaoVien = giaoVienRepository.findById(maGv)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giáo viên"));

        giaoVien.setTenGv(request.getTenGv());
        giaoVien.setNgaySinh(request.getNgaySinh());
        giaoVien.setPhai(request.getPhai());
        giaoVien.setDiaChi(request.getDiaChi());
        giaoVien.setSdt(request.getSdt());
        giaoVien.setEmail(request.getEmail());

        giaoVienRepository.save(giaoVien);

        Optional<User> userOpt = userRepository.findByEmail(giaoVien.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setEmail(request.getEmail());
            user.setUpdatedAt(Instant.now());
            userRepository.save(user);
        }
    }

    public void deleteGiaoVien(String maGv) {
        GiaoVien giaoVien = giaoVienRepository.findById(maGv)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giáo viên"));

        userRepository.findByEmail(giaoVien.getEmail()).ifPresent(userRepository::delete);
        giaoVienRepository.delete(giaoVien);
    }

    public List<GiaoVienDto> getAllGiaoVienWithoutPaging() {
        List<GiaoVien> giaoViens = giaoVienRepository.findAll(Sort.by("maGv").ascending());
        return giaoViens.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<GiaoVienInfo> getGiaoVienByHocKyNamAndMonHoc(Integer hocKy, Integer namHoc, String maMh) {
        List<Object[]> results = giaoVienRepository.findGiaoVienByHocKyNamAndMonHoc(hocKy, namHoc, maMh);
        return results.stream()
                .map(row -> new GiaoVienInfo(
                        (String) row[0],
                        (String) row[1]
                ))
                .collect(Collectors.toList());
    }

    private boolean isValidSortField(String field) {
        return Arrays.asList("maGv", "tenGv", "ngaySinh", "email").contains(field);
    }

    private GiaoVienDto convertToDto(GiaoVien giaoVien) {
        GiaoVienDto dto = new GiaoVienDto();
        dto.setMaGv(giaoVien.getMaGv());
        dto.setTenGv(giaoVien.getTenGv());
        dto.setNgaySinh(giaoVien.getNgaySinh());
        dto.setPhai(giaoVien.getPhai());
        dto.setDiaChi(giaoVien.getDiaChi());
        dto.setSdt(giaoVien.getSdt());
        dto.setEmail(giaoVien.getEmail());
        dto.setAvatar(giaoVien.getAvatar());
        dto.setHasAccount(userRepository.findByEmail(giaoVien.getEmail()).isPresent());
        return dto;
    }

    private GiaoVienDTO_Profile mapToDTO(GiaoVien gv) {
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
}