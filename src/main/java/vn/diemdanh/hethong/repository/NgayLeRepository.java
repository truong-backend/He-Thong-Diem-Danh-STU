package vn.diemdanh.hethong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.diemdanh.hethong.entity.NgayLe;

import java.time.LocalDate;
import java.util.List;

public interface NgayLeRepository extends JpaRepository<NgayLe, LocalDate> {
  @Query(
          value = "SELECT EXISTS( " +
                  "SELECT 1 FROM ngay_le " +
                  "WHERE :date BETWEEN ngay AND DATE_ADD(ngay, INTERVAL so_ngay_nghi - 1 DAY) " +
                  ")",
          nativeQuery = true
  )
  Long  existsByDateIsHoliday(@Param("date") LocalDate date);
  }