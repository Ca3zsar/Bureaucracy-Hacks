package com.example.services;

import com.example.models.ConfirmToken;
import com.example.models.User;
import com.example.requests.RegistrationRequest;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
//@AllArgsConstructor
@Setter
public class RegistrationService {

    private final UserService userService;
    private final ConfirmTokenService confirmTokenService;
    private final EmailSenderService emailSender;
    private String userEmail;
    private User user;

    public RegistrationService(UserService userService, ConfirmTokenService confirmTokenService, EmailSenderService emailSender) {
        this.userService = userService;
        this.confirmTokenService = confirmTokenService;
        this.emailSender = emailSender;
    }

    public String register(RegistrationRequest request) {

        this.userEmail = request.getEmail();
        this.user = new User(
                request.getUsername(),
                request.getPassword(),
                request.getName(),
                request.getSurname(),
                request.getEmail());
        String token = userService.signUpUser(user);

        emailSender.send(request.getEmail(), "enter the following link to activate your account " +
                " https://bureaucracyhackshostat.herokuapp.com/registration/confirm?token=" + token);
//            https://bureaucracyhackshostat.herokuapp.com
//            localhost:8081
        JSONObject jo = new JSONObject();
        jo.put("statusCode", 200);
        jo.put("message","Check your email to confirm your account.");
        return jo.toString();
    }

    @Modifying
    public String confirmToken(String token) {

        ConfirmToken confirmToken = confirmTokenService.getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException(/*"{\"message\": \*/"Token not found.}"));

        if (confirmToken.getConfirmedAt() != null) {
            throw new IllegalStateException(/*"{\"message\" : */"Email already confirmed.}");
        }

        LocalDateTime expiredAt = confirmToken.getExpiredAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            token = UUID.randomUUID().toString();
            confirmToken.setToken(token);
            confirmToken.setCreatedAt(LocalDateTime.now());
            confirmToken.setExpiredAt(LocalDateTime.now().plusMinutes(15));
            confirmTokenService.getConfirmTokenRepository().save(confirmToken);
            emailSender.send(userEmail, "The last token has expired, try enter this one " +
                    "https://bureaucracyhackshostat.herokuapp.com/registration/confirm?token=" + token);
//            https://bureaucracyhackshostat.herokuapp.com
//            localhost:8081
            JSONObject jo = new JSONObject();
            jo.put("message","Error: Token expired, check your email again for a new one.");
            return jo.toString();
        }

        confirmTokenService.setConfirmedAt(token);
        userService.enableUser(confirmToken.getUser().getEmail());
        JSONObject jo = new JSONObject();
        jo.put("message","Account confirmed with success.");
        return jo.toString();
    }

}
