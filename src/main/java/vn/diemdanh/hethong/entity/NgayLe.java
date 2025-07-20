package vn.diemdanh.hethong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ngay_le")
public class NgayLe {
    @Id
    @Column(name = "ngay", nullable = false)
    private LocalDate id;

    @Column(name = "so_ngay_nghi")
    private Integer soNgayNghi;

}