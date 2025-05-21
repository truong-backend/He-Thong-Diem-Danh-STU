package vn.diemdanh.hethong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mon_hoc")
public class MonHoc {
    @Id
    @Size(max = 20)
    @Column(name = "ma_mh", nullable = false, length = 20)
    private String maMh;

    @Size(max = 50)
    @NotNull
    @Column(name = "ten_mh", nullable = false, length = 50)
    private String tenMh;

    @NotNull
    @Column(name = "so_tiet", nullable = false)
    private Integer soTiet;

}