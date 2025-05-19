package vn.diemdanh.hethong.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "personal_access_tokens")
public class PersonalAccessToken {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 191)
    @NotNull
    @Column(name = "name", nullable = false, length = 191)
    private String name;

    @Size(max = 64)
    @NotNull
    @Column(name = "token", nullable = false, length = 64)
    private String token;

    @Lob
    @Column(name = "abilities")
    private String abilities;

    @Column(name = "last_used_at")
    private Instant lastUsedAt;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Size(max = 191)
    @NotNull
    @Column(name = "tokenable_type", nullable = false, length = 191)
    private String tokenableType;

    @Size(max = 36)
    @NotNull
    @Column(name = "tokenable_id", nullable = false, length = 36)
    private String tokenableId;

}