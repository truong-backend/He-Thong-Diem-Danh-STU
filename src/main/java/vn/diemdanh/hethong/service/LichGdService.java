package vn.diemdanh.hethong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.diemdanh.hethong.dto.hocky.HocKyDTO;
import vn.diemdanh.hethong.dto.lichgd.LichGdDto;
import vn.diemdanh.hethong.entity.GiaoVien;
import vn.diemdanh.hethong.entity.LichGd;
import vn.diemdanh.hethong.entity.MonHoc;
import vn.diemdanh.hethong.repository.GiaoVienRepository;
import vn.diemdanh.hethong.repository.LichGdRepository;
import vn.diemdanh.hethong.repository.MonHocRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LichGdService {

    @Autowired
    private LichGdRepository lichGdRepository;

    @Autowired
    private GiaoVienRepository giaoVienRepository;

    @Autowired
    private MonHocRepository monHocRepository;

    // -------------------- CREATE --------------------
    public LichGdDto createLichGd(LichGdDto request) {
        // Validate business rules
        validateLichGdRequest(request);

        // Get entities
        GiaoVien giaoVien = findGiaoVienByMa(request.getMaGv());
        MonHoc monHoc = findMonHocByMa(request.getMaMh());

        // Create and save entity
        LichGd lichGd = createNewLichGd(request, giaoVien, monHoc);
        lichGd = lichGdRepository.save(lichGd);

        return convertToDto(lichGd);
    }

    // -------------------- READ --------------------
    public Page<LichGdDto> getLichGdList(int page, int size, String sortBy, String sortDir,
                                         String maGv, String maMh, Integer hocKy) {
        // Validate sort field
        if (!isValidSortField(sortBy)) {
            throw new IllegalArgumentException("Trường sắp xếp không hợp lệ");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<LichGd> lichGds = filterLichGd(maGv, maMh, hocKy, pageable);

        return lichGds.map(this::convertToDto);
    }

    public LichGdDto getLichGdById(Long id) {
        LichGd lichGd = findLichGdById(id);
        return convertToDto(lichGd);
    }

    public List<LichGdDto> getAllLichGd() {
        return lichGdRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // -------------------- UPDATE --------------------
    public LichGdDto updateLichGd(Long id, LichGdDto request) {
        // Validate business rules
        validateLichGdRequest(request);

        LichGd lichGd = findLichGdById(id);

        // Update teacher if changed
        if (!lichGd.getMaGv().getMaGv().equals(request.getMaGv())) {
            lichGd.setMaGv(findGiaoVienByMa(request.getMaGv()));
        }

        // Update subject if changed
        if (!lichGd.getMaMh().getMaMh().equals(request.getMaMh())) {
            lichGd.setMaMh(findMonHocByMa(request.getMaMh()));
        }

        // Update other fields
        updateLichGdFields(lichGd, request);

        lichGd = lichGdRepository.save(lichGd);
        return convertToDto(lichGd);
    }

    // -------------------- DELETE --------------------
    public void deleteLichGd(Long id) {
        LichGd lichGd = findLichGdById(id);
        lichGdRepository.delete(lichGd);
    }

    // -------------------- HỌC KỲ --------------------
    public List<HocKyDTO> getAllSemesters(String maGv) {
        List<Object[]> results = lichGdRepository.findAllSemesters(maGv);

        return results.stream()
                .map(row -> HocKyDTO.builder()
                        .hocKy((Integer) row[0])
                        .hocKyDisplay((String) row[1])
                        .namHoc((Integer) row[2])
                        .build())
                .collect(Collectors.toList());
    }

    public List<HocKyDTO> getAllHocKy() {
        List<Object[]> results = lichGdRepository.findAllHocKy();
        return results.stream()
                .map(row -> new HocKyDTO(
                        (Integer) row[0],
                        (String) row[1],
                        (Integer) row[2]
                ))
                .collect(Collectors.toList());
    }

    // -------------------- MÃ GIẢNG DẠY --------------------
    public Integer getMaGd(int hocKy, String maMh, String maGv, int nhom) {
        return lichGdRepository.findMaGd(hocKy, maMh, maGv, nhom);
    }

    // -------------------- LẤY LỊCH GIẢNG DẠY THEO MÃ GIÁO VIÊN --------------------
    public List<LichGdDto> getLichGdByMaGv(String maGv) {
        List<Object[]> rows = lichGdRepository.findLichGdByMaGv(maGv);
        return rows.stream().map(row -> {
            LichGdDto dto = new LichGdDto();
            dto.setId(((Number) row[0]).longValue());
            dto.setMaGv((String) row[1]);
            dto.setTenGv((String) row[2]);
            dto.setMaMh((String) row[3]);
            dto.setTenMh((String) row[4]);
            dto.setNmh((Integer) row[5]);
            dto.setPhongHoc((String) row[6]);
            dto.setNgayBd(row[7] instanceof java.sql.Date
                    ? ((java.sql.Date) row[7]).toLocalDate()
                    : (LocalDate) row[7]);
            dto.setNgayKt(row[8] instanceof java.sql.Date
                    ? ((java.sql.Date) row[8]).toLocalDate()
                    : (LocalDate) row[8]);
            dto.setStBd((Integer) row[9]);
            dto.setStKt((Integer) row[10]);
            dto.setHocKy(String.valueOf(row[11]));
            return dto;
        }).collect(Collectors.toList());
    }

    // -------------------- PRIVATE HELPER METHODS --------------------
    private void validateLichGdRequest(LichGdDto request) {
        if (request.getNgayKt().isBefore(request.getNgayBd())) {
            throw new IllegalArgumentException("Ngày kết thúc phải sau ngày bắt đầu");
        }

        if (request.getStKt() <= request.getStBd()) {
            throw new IllegalArgumentException("Tiết kết thúc phải sau tiết bắt đầu");
        }
    }

    private LichGd findLichGdById(Long id) {
        return lichGdRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch giảng dạy"));
    }

    private GiaoVien findGiaoVienByMa(String maGv) {
        return giaoVienRepository.findById(maGv)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giáo viên"));
    }

    private MonHoc findMonHocByMa(String maMh) {
        return monHocRepository.findById(maMh)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));
    }

    private LichGd createNewLichGd(LichGdDto request, GiaoVien gv, MonHoc mh) {
        LichGd lichGd = new LichGd();
        lichGd.setMaGv(gv);
        lichGd.setMaMh(mh);
        updateLichGdFields(lichGd, request);
        return lichGd;
    }

    private void updateLichGdFields(LichGd lichGd, LichGdDto request) {
        lichGd.setNmh(request.getNmh());
        lichGd.setPhongHoc(request.getPhongHoc());
        lichGd.setNgayBd(request.getNgayBd());
        lichGd.setNgayKt(request.getNgayKt());
        lichGd.setStBd(request.getStBd());
        lichGd.setStKt(request.getStKt());
        lichGd.setHocKy(request.getHocKy());
    }

    private Page<LichGd> filterLichGd(String maGv, String maMh, Integer hocKy, Pageable pageable) {
        if (maGv != null && !maGv.isEmpty()) {
            return lichGdRepository.findByMaGv(findGiaoVienByMa(maGv), pageable);
        } else if (maMh != null && !maMh.isEmpty()) {
            return lichGdRepository.findByMaMh(findMonHocByMa(maMh), pageable);
        } else if (hocKy != null) {
            return lichGdRepository.findByHocKy(hocKy, pageable);
        } else {
            return lichGdRepository.findAll(pageable);
        }
    }

    private LichGdDto convertToDto(LichGd lichGd) {
        LichGdDto dto = new LichGdDto();
        dto.setId(lichGd.getId());
        dto.setMaGv(lichGd.getMaGv().getMaGv());
        dto.setTenGv(lichGd.getMaGv().getTenGv());
        dto.setMaMh(lichGd.getMaMh().getMaMh());
        dto.setTenMh(lichGd.getMaMh().getTenMh());
        dto.setNmh(lichGd.getNmh());
        dto.setPhongHoc(lichGd.getPhongHoc());
        dto.setNgayBd(lichGd.getNgayBd());
        dto.setNgayKt(lichGd.getNgayKt());
        dto.setStBd(lichGd.getStBd());
        dto.setStKt(lichGd.getStKt());
        dto.setHocKy(lichGd.getHocKy());
        return dto;
    }

    private boolean isValidSortField(String field) {
        return Arrays.asList("id", "nmh", "phongHoc", "ngayBd", "ngayKt", "stBd", "stKt", "hocKy").contains(field);
    }
}