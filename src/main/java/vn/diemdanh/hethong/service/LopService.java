package vn.diemdanh.hethong.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.dto.lop.LopDto;
import vn.diemdanh.hethong.entity.Khoa;
import vn.diemdanh.hethong.entity.Lop;
import vn.diemdanh.hethong.repository.LopRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LopService {

    @Autowired
    private LopRepository lopRepository;

    @Autowired
    private KhoaService khoaService;

    public LopDto createLop(@Valid LopDto request) {
        if (lopRepository.findById(request.getMaLop()).isPresent()) {
            throw new RuntimeException("Mã lớp đã tồn tại");
        }

        Khoa khoa = khoaService.getKhoaById(request.getMaKhoa());

        Lop lop = new Lop();
        lop.setMaLop(request.getMaLop());
        lop.setTenLop(request.getTenLop());
        lop.setMaKhoa(khoa);
        lop.setGvcn(request.getGvcn());
        lop.setSdtGvcn(request.getSdtGvcn());

        lop = lopRepository.save(lop);

        return convertToDto(lop);
    }

    public Page<LopDto> getLopList(int page, int size, String sortBy, String sortDir, String maKhoa) {
        if (!isValidSortField(sortBy)) {
            throw new RuntimeException("Trường sắp xếp không hợp lệ");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));

        Page<Lop> lops;

        if (maKhoa != null && !maKhoa.isEmpty()) {
            Khoa khoa = khoaService.getKhoaById(maKhoa);
            lops = lopRepository.findByMaKhoa(khoa, pageable);
        } else {
            lops = lopRepository.findAll(pageable);
        }

        return lops.map(this::convertToDto);
    }

    public List<LopDto> getAllLops() {
        List<Lop> lops = lopRepository.findAll(Sort.by("maLop").ascending());
        return lops.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public LopDto getLopById(String maLop) {
        Lop lop = lopRepository.findById(maLop)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp"));
        return convertToDto(lop);
    }

    public LopDto updateLop(String maLop, LopDto request) {
        Lop lop = lopRepository.findById(maLop)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp"));

        if (!lop.getMaKhoa().getMaKhoa().equals(request.getMaKhoa())) {
            Khoa khoa = khoaService.getKhoaById(request.getMaKhoa());
            lop.setMaKhoa(khoa);
        }

        lop.setTenLop(request.getTenLop());
        lop.setGvcn(request.getGvcn());
        lop.setSdtGvcn(request.getSdtGvcn());

        lop = lopRepository.save(lop);
        return convertToDto(lop);
    }

    public void deleteLop(String maLop) {
        Lop lop = lopRepository.findById(maLop)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp"));
        lopRepository.delete(lop);
    }

    private LopDto convertToDto(Lop lop) {
        LopDto dto = new LopDto();
        dto.setMaLop(lop.getMaLop());
        dto.setTenLop(lop.getTenLop());
        dto.setMaKhoa(lop.getMaKhoa().getMaKhoa());
        dto.setTenKhoa(lop.getMaKhoa().getTenKhoa());
        dto.setGvcn(lop.getGvcn());
        dto.setSdtGvcn(lop.getSdtGvcn());
        return dto;
    }

    private boolean isValidSortField(String field) {
        return Arrays.asList("maLop", "tenLop", "gvcn", "sdtGvcn").contains(field);
    }
}
