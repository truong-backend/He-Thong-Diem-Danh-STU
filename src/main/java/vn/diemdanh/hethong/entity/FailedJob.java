package vn.diemdanh.hethong.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "failed_jobs")
public class FailedJob {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "uuid", nullable = false, length = 191)
    private String uuid;

    @NotNull
    @Lob
    @Column(name = "connection", nullable = false)
    private String connection;

    @NotNull
    @Lob
    @Column(name = "queue", nullable = false)
    private String queue;

    @NotNull
    @Lob
    @Column(name = "payload", nullable = false)
    private String payload;

    @NotNull
    @Lob
    @Column(name = "exception", nullable = false)
    private String exception;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "failed_at", nullable = false)
    private Instant failedAt;

}