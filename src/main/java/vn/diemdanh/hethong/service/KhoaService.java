package vn.diemdanh.hethong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.dto.khoa.KhoaDto;
import vn.diemdanh.hethong.entity.Khoa;
import vn.diemdanh.hethong.repository.KhoaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KhoaService {

    @Autowired
    private KhoaRepository khoaRepository;

    // ============================== CREATE ==============================

    public KhoaDto createKhoa(KhoaDto request) {
        if (khoaRepository.existsById(request.getMaKhoa())) {
            throw new RuntimeException("Mã khoa đã tồn tại");
        }

        Khoa khoa = new Khoa();
        khoa.setMaKhoa(request.getMaKhoa());
        khoa.setTenKhoa(request.getTenKhoa());

        Khoa saved = khoaRepository.save(khoa);
        return toDto(saved);
    }

    // ============================== READ ==============================

    public Page<KhoaDto> getKhoaList(int page, int size, String sortBy, String sortDir) {
        if (!isValidSortField(sortBy)) {
            throw new RuntimeException("Trường sắp xếp không hợp lệ");
        }

        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Khoa> result = khoaRepository.findAll(pageable);
        return result.map(this::toDto);
    }

    public List<KhoaDto> getAllKhoa() {
        List<Khoa> khoas = khoaRepository.findAll();
        return khoas.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Khoa  getKhoaById(String maKhoa) {
        Khoa khoa = khoaRepository.findById(maKhoa)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa"));
        return (khoa);
    }

    // ============================== UPDATE ==============================

    public KhoaDto updateKhoa(String maKhoa, KhoaDto request) {
        Khoa khoa = khoaRepository.findById(maKhoa)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa"));

        khoa.setTenKhoa(request.getTenKhoa());

        Khoa updated = khoaRepository.save(khoa);
        return toDto(updated);
    }

    // ============================== DELETE ==============================

    public void deleteKhoa(String maKhoa) {
        Khoa khoa = khoaRepository.findById(maKhoa)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa"));

        khoaRepository.deleteById(maKhoa);
    }

    // ============================== PRIVATE METHODS ==============================

    private KhoaDto toDto(Khoa khoa) {
        KhoaDto dto = new KhoaDto();
        dto.setMaKhoa(khoa.getMaKhoa());
        dto.setTenKhoa(khoa.getTenKhoa());
        return dto;
    }

    private boolean isValidSortField(String field) {
        return Arrays.asList("maKhoa", "tenKhoa").contains(field);
    }
}