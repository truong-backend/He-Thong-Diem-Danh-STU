package vn.diemdanh.hethong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "password_resets")
public class PasswordReset {
    @Id
    @Size(max = 191)
    @Column(name = "email", nullable = false, length = 191)
    private String email;

    @Size(max = 191)
    @NotNull
    @Column(name = "token", nullable = false, length = 191)
    private String token;

    @Column(name = "created_at")
    private Instant createdAt;

}