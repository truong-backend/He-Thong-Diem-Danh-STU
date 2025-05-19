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
@Table(name = "lich_gd")
public class LichGd {
    @Id
    @Column(name = "ma_gd", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ma_gv", nullable = false)
    private GiaoVien maGv;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ma_mh", nullable = false)
    private vn.diemdanh.hethong.entity.MonHoc maMh;

    @NotNull
    @Column(name = "nmh", nullable = false)
    private Integer nmh;

    @Size(max = 10)
    @NotNull
    @Column(name = "phong_hoc", nullable = false, length = 10)
    private String phongHoc;

    @NotNull
    @Column(name = "ngay_bd", nullable = false)
    private LocalDate ngayBd;

    @NotNull
    @Column(name = "ngay_kt", nullable = false)
    private LocalDate ngayKt;

    @NotNull
    @Column(name = "st_bd", nullable = false)
    private Integer stBd;

    @NotNull
    @Column(name = "st_kt", nullable = false)
    private Integer stKt;

    @NotNull
    @Column(name = "hoc_ky", nullable = false)
    private Integer hocKy;

}