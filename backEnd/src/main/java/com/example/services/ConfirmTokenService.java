package com.example.services;

import com.example.models.ChangePassToken;
import com.example.models.ConfirmToken;
import com.example.models.User;
import com.example.repositories.ChangePasswordRepository;
import com.example.repositories.ConfirmTokenRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Getter
@AllArgsConstructor
public class ConfirmTokenService {

    private final ConfirmTokenRepository confirmTokenRepository;
    private final ChangePasswordRepository changePasswordRepository;
    private final EmailSenderService emailSender;

    public void saveConfirmationToken(ConfirmToken token) {
        confirmTokenRepository.save(token);
    }

    public void saveConfirmationToken(ChangePassToken token) {
        changePasswordRepository.save(token);
    }

    public Optional<ConfirmToken> getToken(String token) {
        return confirmTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }

    public int setChangePassConfirmedAt(String token) {
        return changePasswordRepository.updateConfirmedAt(token, LocalDateTime.now());
    }

    public Optional<ChangePassToken> getPassToken(String token) {
        return changePasswordRepository.findByToken(token);
    }
}
