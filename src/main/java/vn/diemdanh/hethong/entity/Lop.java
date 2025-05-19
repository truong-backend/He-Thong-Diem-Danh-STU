package vn.diemdanh.hethong.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "lop")
public class Lop {
    @Id
    @Size(max = 20)
    @Column(name = "ma_lop", nullable = false, length = 20)
    private String maLop;

    @Size(max = 30)
    @NotNull
    @Column(name = "ten_lop", nullable = false, length = 30)
    private String tenLop;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ma_khoa", nullable = false)
    private Khoa maKhoa;

    @Size(max = 150)
    @Column(name = "gvcn", length = 150)
    private String gvcn;

    @Size(max = 11)
    @Column(name = "sdt_gvcn", length = 11)
    private String sdtGvcn;

}