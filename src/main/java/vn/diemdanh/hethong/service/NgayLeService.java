package vn.diemdanh.hethong.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.diemdanh.hethong.entity.NgayLe;
import vn.diemdanh.hethong.repository.NgayLeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NgayLeService {

    private final NgayLeRepository ngayLeRepository;

    /**
     * Lấy danh sách tất cả ngày lễ
     */
    public List<NgayLe> getAllNgayLe() {
        log.info("Lấy danh sách tất cả ngày lễ");
        return ngayLeRepository.findAllOrderByNgay();
    }

    /**
     * Tìm ngày lễ theo ngày
     */
    public Optional<NgayLe> findByNgay(LocalDate ngay) {
        log.info("Tìm ngày lễ theo ngày: {}", ngay);
        return ngayLeRepository.findByNgay(ngay);
    }

    /**
     * Kiểm tra ngày có phải là ngày lễ không
     */
    public boolean isHoliday(LocalDate ngay) {
        log.info("Kiểm tra ngày lễ: {}", ngay);
        return ngayLeRepository.existsByNgay(ngay);
    }

    /**
     * Thêm ngày lễ mới
     */
    public NgayLe createNgayLe(LocalDate ngay, Integer soNgayNghi) {
        log.info("Tạo ngày lễ mới: {} với {} ngày nghỉ", ngay, soNgayNghi);

        // Kiểm tra ngày lễ đã tồn tại chưa
        if (ngayLeRepository.existsByNgay(ngay)) {
            throw new IllegalArgumentException("Ngày lễ " + ngay + " đã tồn tại");
        }

        NgayLe ngayLe = new NgayLe();
        ngayLe.setId(ngay);
        ngayLe.setSoNgayNghi(soNgayNghi);

        return ngayLeRepository.save(ngayLe);
    }

    /**
     * Cập nhật ngày lễ
     */
    public NgayLe updateNgayLe(LocalDate ngay, Integer soNgayNghi) {
        log.info("Cập nhật ngày lễ: {} với {} ngày nghỉ", ngay, soNgayNghi);

        Optional<NgayLe> existingNgayLe = ngayLeRepository.findByNgay(ngay);
        if (existingNgayLe.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy ngày lễ " + ngay);
        }

        NgayLe ngayLe = existingNgayLe.get();
        ngayLe.setSoNgayNghi(soNgayNghi);

        return ngayLeRepository.save(ngayLe);
    }

    /**
     * Xóa ngày lễ
     */
    public boolean deleteNgayLe(LocalDate ngay) {
        log.info("Xóa ngày lễ: {}", ngay);

        if (!ngayLeRepository.existsByNgay(ngay)) {
            throw new IllegalArgumentException("Không tìm thấy ngày lễ " + ngay);
        }

        int deletedRows = ngayLeRepository.deleteByNgay(ngay);
        return deletedRows > 0;
    }

    /**
     * Lấy danh sách ngày lễ theo năm
     */
    public List<NgayLe> getNgayLeByYear(int year) {
        log.info("Lấy danh sách ngày lễ năm: {}", year);
        return ngayLeRepository.findByYear(year);
    }

    /**
     * Lấy danh sách ngày lễ theo tháng và năm
     */
    public List<NgayLe> getNgayLeByYearAndMonth(int year, int month) {
        log.info("Lấy danh sách ngày lễ tháng {}/{}", month, year);
        return ngayLeRepository.findByYearAndMonth(year, month);
    }

    /**
     * Lấy danh sách ngày lễ trong khoảng thời gian
     */
    public List<NgayLe> getNgayLeBetween(LocalDate startDate, LocalDate endDate) {
        log.info("Lấy danh sách ngày lễ từ {} đến {}", startDate, endDate);
        return ngayLeRepository.findByNgayBetween(startDate, endDate);
    }

    /**
     * Tìm ngày lễ gần nhất từ hiện tại
     */
    public Optional<NgayLe> getNextHoliday() {
        LocalDate today = LocalDate.now();
        log.info("Tìm ngày lễ tiếp theo từ ngày: {}", today);
        return ngayLeRepository.findNextHoliday(today);
    }

    /**
     * Đếm số ngày lễ trong năm
     */
    public long countHolidaysByYear(int year) {
        log.info("Đếm số ngày lễ năm: {}", year);
        return ngayLeRepository.countByYear(year);
    }

    /**
     * Lấy danh sách ngày lễ có số ngày nghỉ tối thiểu
     */
    public List<NgayLe> getNgayLeWithMinDays(int minDays) {
        log.info("Lấy danh sách ngày lễ có tối thiểu {} ngày nghỉ", minDays);
        return ngayLeRepository.findByMinNgayNghi(minDays);
    }

    /**
     * Xóa nhiều ngày lễ trong khoảng thời gian
     */
    public int deleteNgayLeBetween(LocalDate startDate, LocalDate endDate) {
        log.info("Xóa ngày lễ từ {} đến {}", startDate, endDate);
        return ngayLeRepository.deleteByNgayBetween(startDate, endDate);
    }

    /**
     * Tạo hoặc cập nhật ngày lễ
     */
    public NgayLe saveOrUpdateNgayLe(LocalDate ngay, Integer soNgayNghi) {
        log.info("Tạo hoặc cập nhật ngày lễ: {} với {} ngày nghỉ", ngay, soNgayNghi);

        Optional<NgayLe> existingNgayLe = ngayLeRepository.findByNgay(ngay);

        NgayLe ngayLe;
        if (existingNgayLe.isPresent()) {
            ngayLe = existingNgayLe.get();
            ngayLe.setSoNgayNghi(soNgayNghi);
        } else {
            ngayLe = new NgayLe();
            ngayLe.setId(ngay);
            ngayLe.setSoNgayNghi(soNgayNghi);
        }

        return ngayLeRepository.save(ngayLe);
    }
}