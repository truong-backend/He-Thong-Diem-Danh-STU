package vn.diemdanh.hethong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class LichHocId implements Serializable {
    private static final long serialVersionUID = 7210889739353104571L;
    @Size(max = 10)
    @NotNull
    @Column(name = "ma_sv", nullable = false, length = 10)
    private String maSv;

    @Column(name = "ma_gd", columnDefinition = "int UNSIGNED not null")
    private Long maGd;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LichHocId entity = (LichHocId) o;
        return Objects.equals(this.maGd, entity.maGd) &&
                Objects.equals(this.maSv, entity.maSv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maGd, maSv);
    }

}