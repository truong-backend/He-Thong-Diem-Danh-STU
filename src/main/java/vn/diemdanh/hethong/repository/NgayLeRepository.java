package vn.diemdanh.hethong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.diemdanh.hethong.entity.NgayLe;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NgayLeRepository extends JpaRepository<NgayLe, LocalDate> {
  @Query(
          value = "SELECT EXISTS( " +
                  "SELECT 1 FROM ngay_le " +
                  "WHERE :date BETWEEN ngay AND DATE_ADD(ngay, INTERVAL so_ngay_nghi - 1 DAY) " +
                  ")",
          nativeQuery = true
  )
  Long  existsByDateIsHoliday(@Param("date") LocalDate date);
  // Lấy danh sách tất cả ngày lễ, sắp xếp theo ngày
  @Query(value = "SELECT * FROM ngay_le ORDER BY ngay ASC", nativeQuery = true)
  List<NgayLe> findAllOrderByNgay();

  // Tìm ngày lễ theo ngày cụ thể
  @Query(value = "SELECT * FROM ngay_le WHERE ngay = :ngay", nativeQuery = true)
  Optional<NgayLe> findByNgay(@Param("ngay") LocalDate ngay);

  // Tìm ngày lễ trong khoảng thời gian
  @Query(value = "SELECT * FROM ngay_le WHERE ngay BETWEEN :startDate AND :endDate ORDER BY ngay ASC",
          nativeQuery = true)
  List<NgayLe> findByNgayBetween(@Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate);

  // Tìm ngày lễ theo năm
  @Query(value = "SELECT * FROM ngay_le WHERE YEAR(ngay) = :year ORDER BY ngay ASC",
          nativeQuery = true)
  List<NgayLe> findByYear(@Param("year") int year);

  // Tìm ngày lễ theo tháng và năm
  @Query(value = "SELECT * FROM ngay_le WHERE YEAR(ngay) = :year AND MONTH(ngay) = :month ORDER BY ngay ASC",
          nativeQuery = true)
  List<NgayLe> findByYearAndMonth(@Param("year") int year, @Param("month") int month);

  // Kiểm tra ngày có phải là ngày lễ không
  @Query(value = "SELECT COUNT(*) > 0 FROM ngay_le WHERE ngay = :ngay", nativeQuery = true)
  boolean existsByNgay(@Param("ngay") LocalDate ngay);

  // Thêm ngày lễ mới
  @Modifying
  @Transactional
  @Query(value = "INSERT INTO ngay_le (ngay, so_ngay_nghi) VALUES (:ngay, :soNgayNghi)",
          nativeQuery = true)
  void insertNgayLe(@Param("ngay") LocalDate ngay, @Param("soNgayNghi") Integer soNgayNghi);

  // Cập nhật thông tin ngày lễ
  @Modifying
  @Transactional
  @Query(value = "UPDATE ngay_le SET so_ngay_nghi = :soNgayNghi WHERE ngay = :ngay",
          nativeQuery = true)
  int updateSoNgayNghi(@Param("ngay") LocalDate ngay, @Param("soNgayNghi") Integer soNgayNghi);

  // Xóa ngày lễ theo ngày
  @Modifying
  @Transactional
  @Query(value = "DELETE FROM ngay_le WHERE ngay = :ngay", nativeQuery = true)
  int deleteByNgay(@Param("ngay") LocalDate ngay);

  // Xóa các ngày lễ trong khoảng thời gian
  @Modifying
  @Transactional
  @Query(value = "DELETE FROM ngay_le WHERE ngay BETWEEN :startDate AND :endDate",
          nativeQuery = true)
  int deleteByNgayBetween(@Param("startDate") LocalDate startDate,
                          @Param("endDate") LocalDate endDate);

  // Đếm số ngày lễ trong năm
  @Query(value = "SELECT COUNT(*) FROM ngay_le WHERE YEAR(ngay) = :year", nativeQuery = true)
  long countByYear(@Param("year") int year);

  // Tìm ngày lễ gần nhất từ ngày hiện tại
  @Query(value = "SELECT * FROM ngay_le WHERE ngay >= :currentDate ORDER BY ngay ASC LIMIT 1",
          nativeQuery = true)
  Optional<NgayLe> findNextHoliday(@Param("currentDate") LocalDate currentDate);

  // Lấy danh sách ngày lễ có số ngày nghỉ lớn hơn một giá trị
  @Query(value = "SELECT * FROM ngay_le WHERE so_ngay_nghi > :minDays ORDER BY ngay ASC",
          nativeQuery = true)
  List<NgayLe> findByMinNgayNghi(@Param("minDays") int minDays);
  }