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
@Table(name = "khoa")
public class Khoa {
    @Id
    @Size(max = 20)
    @Column(name = "ma_khoa", nullable = false, length = 20)
    private String maKhoa;

    @Size(max = 150)
    @NotNull
    @Column(name = "ten_khoa", nullable = false, length = 150)
    private String tenKhoa;

}