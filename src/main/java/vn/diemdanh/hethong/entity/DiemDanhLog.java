package vn.diemdanh.hethong.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "diem_danh_log")
public class DiemDanhLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ma_dd", nullable = false)
    private DiemDanh maDd;

    @Column(name = "lan_diem_danh", columnDefinition = "int UNSIGNED not null")
    private Integer lanDiemDanh;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "thoi_gian_diem_danh")
    private Instant thoiGianDiemDanh;

}