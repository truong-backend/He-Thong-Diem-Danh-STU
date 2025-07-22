package vn.diemdanh.hethong.controller.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.diemdanh.hethong.dto.tkb.NgayGiangDayDTO;
import vn.diemdanh.hethong.dto.tkb.TkbDto;
import vn.diemdanh.hethong.dto.tkb.TkbGiaoVienDto;
import vn.diemdanh.hethong.entity.LichGd;
import vn.diemdanh.hethong.entity.Tkb;
import vn.diemdanh.hethong.repository.LichGdRepository;
import vn.diemdanh.hethong.repository.NgayLeRepository;
import vn.diemdanh.hethong.repository.TkbRepository;
import vn.diemdanh.hethong.service.TkbService;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/tkb")
public class TkbController {

    @Autowired
    private TkbRepository tkbRepository;

    @Autowired
    private LichGdRepository lichGdRepository;

    @Autowired
    private TkbService tkbService;

    @Autowired
    private NgayLeRepository ngayLeRepository;

    // CREATE - Thêm thời khóa biểu mới
    @PostMapping("/create")
    public ResponseEntity<?> createTkb(
            @RequestParam Long maGd,
            @RequestParam int thu
    ) {
        try {
            LichGd lichGd = lichGdRepository.findById(maGd)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch giảng dạy"));

            LocalDate startDate = lichGd.getNgayBd();
            LocalDate endDate = lichGd.getNgayKt();

            List<Tkb> tkbList = new ArrayList<>();

            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                // Kiểm tra ngày có phải ngày lễ không
                    Long  isHoliday = ngayLeRepository.existsByDateIsHoliday(date);
                if (isHoliday != null && isHoliday == 1L) {
                    // isHoliday == 1 nghĩa là có ngày lễ
                    continue; // bỏ qua ngày lễ
                }

                // Kiểm tra thứ trong tuần (1 = Monday, ..., 7 = Sunday)
                int dayOfWeek = date.getDayOfWeek().getValue();
                if (dayOfWeek != thu) {
                    continue; // Bỏ qua ngày không đúng thứ
                }

                // Tạo đối tượng Tkb
                Tkb tkb = new Tkb();
                tkb.setMaGd(lichGd);
                tkb.setNgayHoc(date);
                tkb.setPhongHoc(lichGd.getPhongHoc());
                tkb.setStBd(lichGd.getStBd());
                tkb.setStKt(lichGd.getStKt());
                // tkb.setGhiChu(lichGd.getGhiChu()); // Nếu có ghi chú

                tkbList.add(tkb);
            }

            // Lưu tất cả bản ghi
            List<Tkb> savedTkbs = tkbRepository.saveAll(tkbList);

            // Chuyển sang DTO để trả về
            List<TkbDto> dtos = savedTkbs.stream()
                    .map(this::convertToDto)
                    .toList();

            return ResponseEntity.ok(dtos);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm thời khóa biểu: " + e.getMessage());
        }
    }

    // READ - Lấy danh sách thời khóa biểu có phân trang và sắp xếp
//    @GetMapping
//    public ResponseEntity<?> getTkbList(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "ngayHoc") String sortBy,
//            @RequestParam(defaultValue = "asc") String sortDir,
//            @RequestParam(required = false) Long maGd,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayHoc,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
//    ) {
//        try {
//            if (!isValidSortField(sortBy)) {
//                return ResponseEntity.badRequest().body("Trường sắp xếp không hợp lệ");
//            }
//
//            Sort.Direction direction = Sort.Direction.fromString(sortDir);
//            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
//
//            Page<Tkb> tkbs;
//
//            if (maGd != null) {
//                LichGd lichGd = lichGdRepository.findById(maGd)
//                        .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch giảng dạy"));
//                tkbs = tkbRepository.findByMaGd(lichGd, pageable);
//            } else if (ngayHoc != null) {
//                tkbs = tkbRepository.findByNgayHoc(ngayHoc, pageable);
//            } else if (startDate != null && endDate != null) {
//                if (endDate.isBefore(startDate)) {
//                    return ResponseEntity.badRequest().body("Ngày kết thúc phải sau ngày bắt đầu");
//                }
//                tkbs = tkbRepository.findByNgayHocBetween(startDate, endDate, pageable);
//            } else {
//                tkbs = tkbRepository.findAll(pageable);
//            }
//
//            Page<TkbDto> dtos = tkbs.map(this::convertToDto);
//            return ResponseEntity.ok(dtos);
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Lỗi khi lấy danh sách thời khóa biểu: " + e.getMessage());
//        }
//    }

    @GetMapping("/ma-gd/{maGd}")
    public List<TkbDto> getTkbByMaGd(@PathVariable Long maGd) {
        return tkbService.getTkbByMaGd(maGd);
    }
    // READ - Lấy thông tin một thời khóa biểu
    @GetMapping("/{id}")
    public ResponseEntity<?> getTkb(@PathVariable Long id) {
        try {
            Tkb tkb = tkbRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thời khóa biểu"));

            return ResponseEntity.ok(convertToDto(tkb));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy thông tin thời khóa biểu: " + e.getMessage());
        }
    }

    // UPDATE - Cập nhật thông tin thời khóa biểu
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTkb(
            @PathVariable Long id,
            @Valid @RequestBody TkbDto request
    ) {
        try {
            Tkb tkb = tkbRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thời khóa biểu"));

            // Nếu thay đổi lịch giảng dạy, kiểm tra lại và validate với lịch mới
            if (!tkb.getMaGd().getId().equals(request.getMaGd())) {
                LichGd lichGd = lichGdRepository.findById(request.getMaGd())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch giảng dạy"));

                if (request.getNgayHoc().isBefore(lichGd.getNgayBd()) || request.getNgayHoc().isAfter(lichGd.getNgayKt())) {
                    return ResponseEntity.badRequest().body("Ngày học phải nằm trong khoảng thời gian của lịch giảng dạy");
                }
                if (request.getStBd() < lichGd.getStBd() || request.getStKt() > lichGd.getStKt()) {
                    return ResponseEntity.badRequest().body("Tiết học phải nằm trong khoảng tiết của lịch giảng dạy");
                }

                tkb.setMaGd(lichGd);
            } else {
                // Validate với lịch giảng dạy hiện tại
                if (request.getNgayHoc().isBefore(tkb.getMaGd().getNgayBd()) || request.getNgayHoc().isAfter(tkb.getMaGd().getNgayKt())) {
                    return ResponseEntity.badRequest().body("Ngày học phải nằm trong khoảng thời gian của lịch giảng dạy");
                }
                if (request.getStBd() < tkb.getMaGd().getStBd() || request.getStKt() > tkb.getMaGd().getStKt()) {
                    return ResponseEntity.badRequest().body("Tiết học phải nằm trong khoảng tiết của lịch giảng dạy");
                }
            }

            if (request.getStKt() <= request.getStBd()) {
                return ResponseEntity.badRequest().body("Tiết kết thúc phải sau tiết bắt đầu");
            }

            // Cập nhật các trường còn lại
            tkb.setNgayHoc(request.getNgayHoc());
            tkb.setPhongHoc(request.getPhongHoc());
            tkb.setStBd(request.getStBd());
            tkb.setStKt(request.getStKt());
//            tkb.setGhiChu(request.getGhiChu());

            tkb = tkbRepository.save(tkb);
            return ResponseEntity.ok(convertToDto(tkb));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật thời khóa biểu: " + e.getMessage());
        }
    }

    // DELETE - Xóa thời khóa biểu
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTkb(@PathVariable Long id) {
        try {
            Tkb tkb = tkbRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thời khóa biểu"));

            tkbRepository.delete(tkb);
            return ResponseEntity.ok("Xóa thời khóa biểu thành công");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi xóa thời khóa biểu: " + e.getMessage());
        }
    }

    // Helper method chuyển đổi entity Tkb sang DTO
    private TkbDto convertToDto(Tkb tkb) {
        TkbDto dto = new TkbDto();
        dto.setId(tkb.getId());
        dto.setMaGd(tkb.getMaGd().getId());
        dto.setMaGv(tkb.getMaGd().getMaGv().getMaGv());
        dto.setTenGv(tkb.getMaGd().getMaGv().getTenGv());
        dto.setMaMh(tkb.getMaGd().getMaMh().getMaMh());
        dto.setTenMh(tkb.getMaGd().getMaMh().getTenMh());
        dto.setNgayHoc(tkb.getNgayHoc());
        dto.setPhongHoc(tkb.getPhongHoc());
        dto.setStBd(tkb.getStBd());
        dto.setStKt(tkb.getStKt());
//        dto.setGhiChu(tkb.getGhiChu());
        return dto;
    }

    // Kiểm tra trường sắp xếp hợp lệ
    private boolean isValidSortField(String field) {
        return Arrays.asList("id", "ngayHoc", "phongHoc", "stBd", "stKt").contains(field);
    }

    // Lấy danh sách ngày giảng dạy theo mã giảng dạy
    @GetMapping("/danh-sach-ngay-giang-day")
    public ResponseEntity<List<NgayGiangDayDTO>> getClassDates(@RequestParam Integer maGd) {
        List<NgayGiangDayDTO> classDates = tkbService.getClassDates(maGd);
        return ResponseEntity.ok(classDates);
    }

    // Lấy thời khóa biểu của sinh viên theo mã sinh viên, học kỳ và tuần
    @GetMapping("/tkb-sinh-vien")
    public List<TkbGiaoVienDto> getTkb(
            @RequestParam String maSv,
            @RequestParam Integer hocKy,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startOfWeek,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endOfWeek) {
        return tkbService.getTkb(maSv, hocKy, startOfWeek, endOfWeek);
    }

    //Tạo thời khóa biểu cho giảng viên



}
