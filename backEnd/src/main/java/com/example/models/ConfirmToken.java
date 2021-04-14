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
@Table(name = "confirm_tokens", schema = "public")
public class ConfirmToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "confirm_tokens_id_seq")
    @Column(name="id_token")
    private int id;
    @Column(name="token_value", nullable = false)
    private String token;
    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;
    @Column(name="expired_at",nullable = false)
    private LocalDateTime expiredAt;
    @Column(name="confirmed_at")
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "registration_id")
    private User user;

    public ConfirmToken(String token, User user, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this.user=user;
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }


}
