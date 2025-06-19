package vn.diemdanh.hethong.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "diem_danh")
public class DiemDanh {
    @Id
    @Column(name = "ma_dd", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ma_tkb", nullable = false)
    private vn.diemdanh.hethong.entity.Tkb maTkb;

    @Size(max = 20)
    @NotNull
    @Column(name = "ma_sv", nullable = false, length = 20)
    private String maSv;

    @NotNull
    @Column(name = "ngay_hoc", nullable = false)
    private LocalDate ngayHoc;

    @Column(name = "diem_danh1")
    private Instant diemDanh1;

    @Column(name = "diem_danh2")
    private Instant diemDanh2;

    @NotNull
    @Lob
    @Column(name = "ghi_chu", nullable = false)
    private String ghiChu;

}