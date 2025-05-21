package vn.diemdanh.hethong.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "qrcode")
public class Qrcode {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ma_tkb", nullable = false)
    private vn.diemdanh.hethong.entity.Tkb maTkb;

    @NotNull
    @Column(name = "thoi_gian_kt", nullable = false)
    private Instant thoiGianKt;

}