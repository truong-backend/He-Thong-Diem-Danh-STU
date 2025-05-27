package vn.diemdanh.hethong.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    @NotNull
    @Column(name = "username", nullable = false, length = 10)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ma_sv")
    private SinhVien maSv;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ma_gv")
    private GiaoVien maGv;

    @Size(max = 191)
    @NotNull
    @Column(name = "email", nullable = false, length = 191)
    private String email;

    @Column(name = "email_verified_at")
    private Instant emailVerifiedAt;

    @Size(max = 191)
    @NotNull
    @Column(name = "password", nullable = false, length = 191)
    private String password;

    @NotNull
    @Lob
    @Column(name = "role", nullable = false)
    private String role;

    @Size(max = 100)
    @Column(name = "remember_token", length = 100)
    private String rememberToken;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

}