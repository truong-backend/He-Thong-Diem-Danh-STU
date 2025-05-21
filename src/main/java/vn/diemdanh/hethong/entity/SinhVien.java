package vn.diemdanh.hethong.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "sinh_vien")
public class SinhVien {
    @Id
    @Size(max = 10)
    @Column(name = "ma_sv", nullable = false, length = 10)
    private String maSv;

    @Size(max = 150)
    @NotNull
    @Column(name = "ten_sv", nullable = false, length = 150)
    private String tenSv;

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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ma_lop", nullable = false)
    private Lop maLop;

    @Size(max = 100)
    @Column(name = "avatar", length = 100)
    private String avatar;

}