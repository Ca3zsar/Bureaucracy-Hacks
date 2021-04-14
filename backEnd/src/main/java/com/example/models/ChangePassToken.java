package com.example.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "change_pass_tokens", schema = "public")
public class ChangePassToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "change_pass_tokens_token_id_seq")
    @Column(name = "token_id")
    private int id;
    @Column(name = "token_value", nullable = false)
    private String token;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;
    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;
    @Column(name = "email")
    private String email;

    public ChangePassToken(String token, String email, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.email = email;
    }
}
