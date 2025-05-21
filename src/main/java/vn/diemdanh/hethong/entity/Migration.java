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
@Table(name = "migrations")
public class Migration {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "migration", nullable = false, length = 191)
    private String migration;

    @NotNull
    @Column(name = "batch", nullable = false)
    private Integer batch;

}