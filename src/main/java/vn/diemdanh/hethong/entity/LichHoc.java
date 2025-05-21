package vn.diemdanh.hethong.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "lich_hoc")
public class LichHoc {
    @EmbeddedId
    private LichHocId id;

    @MapsId("maSv")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ma_sv", nullable = false)
    private vn.diemdanh.hethong.entity.SinhVien maSv;

    @MapsId("maGd")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ma_gd", nullable = false)
    private LichGd maGd;

}