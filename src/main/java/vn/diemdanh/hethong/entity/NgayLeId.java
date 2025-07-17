package vn.diemdanh.hethong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class NgayLeId implements Serializable {
    private static final long serialVersionUID = 3452357600217260390L;
    @NotNull
    @Column(name = "ngay", nullable = false)
    private Integer ngay;

    @NotNull
    @Column(name = "thang", nullable = false)
    private Integer thang;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        NgayLeId entity = (NgayLeId) o;
        return Objects.equals(this.ngay, entity.ngay) &&
                Objects.equals(this.thang, entity.thang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ngay, thang);
    }

}