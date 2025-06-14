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
@Table(name = "tkb")
public class Tkb {
    @Id
    @Column(name = "ma_tkb", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ma_gd", nullable = false)
    private LichGd maGd;

    @NotNull
    @Column(name = "ngay_hoc", nullable = false)
    private LocalDate ngayHoc;

    @Size(max = 10)
    @NotNull
    @Column(name = "phong_hoc", nullable = false, length = 10)
    private String phongHoc;

    @NotNull
    @Column(name = "st_bd", nullable = false)
    private Integer stBd;

    @NotNull
    @Column(name = "st_kt", nullable = false)
    private Integer stKt;

    @Lob
    @Column(name = "ghi_chu")
    private String ghiChu;


}