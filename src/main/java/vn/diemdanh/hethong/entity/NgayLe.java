package vn.diemdanh.hethong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ngay_le")
public class NgayLe {
    @EmbeddedId
    private NgayLeId id;

    @Column(name = "so_ngay_nghi")
    private Integer soNgayNghi;

}