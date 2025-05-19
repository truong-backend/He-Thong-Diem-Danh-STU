package vn.diemdanh.hethong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "giao_vien")
public class GiaoVien {
    @Id
    @Size(max = 10)
    @Column(name = "ma_gv", nullable = false, length = 10)
    private String maGv;

    @Size(max = 150)
    @NotNull
    @Column(name = "ten_gv", nullable = false, length = 150)
    private String tenGv;

    @NotNull
    @Column(name = "ngay_sinh", nullable = false)
    private LocalDate ngaySinh;

    @NotNull
    @Column(name = "phai", nullable = false)
    private Byte phai;

    @Size(max = 300)
    @NotNull
    @Column(name = "dia_chi", nullable = false, length = 300)
    private String diaChi;

    @Size(max = 11)
    @NotNull
    @Column(name = "sdt", nullable = false, length = 11)
    private String sdt;

    @Size(max = 50)
    @NotNull
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Size(max = 100)
    @Column(name = "avatar", length = 100)
    private String avatar;

}