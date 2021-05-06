package com.example.services;

import com.example.models.ChangePassToken;
import com.example.models.EmailSender;
import com.example.repositories.ChangePasswordRepository;
import com.example.requests.ChangePasswordRequest;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
//@AllArgsConstructor
@Setter
@Controller
public class ChangePasswordService {
    private final UserService userService;
    private final ConfirmTokenService changePasswordService;
    private final EmailSender emailSender;
    private final ChangePasswordRepository changePasswordRepository;
    private String password;
    private String newPassword;

    public ChangePasswordService(UserService userService, ConfirmTokenService changePasswordService, EmailSender emailSender, ChangePasswordRepository changePasswordRepository) {
        this.userService = userService;
        this.changePasswordService = changePasswordService;
        this.emailSender = emailSender;
        this.changePasswordRepository = changePasswordRepository;
    }

    public String changePassword(ChangePasswordRequest changePasswordRequest) {
        this.password = changePasswordRequest.getNewPassword();
        this.newPassword = changePasswordRequest.getConfirmNewPassword();
        String token = userService.changePassUser(changePasswordRequest.getEmail());
        emailSender.send(changePasswordRequest.getEmail(), "Enter the following link to confirm changing your password: <a href=\""
                + "https://bureaucracyhackshostat.herokuapp.com/changepassword/confirm?token=" +
                token + "\"> Click me </a>");
//            https://bureaucracyhackshostat.herokuapp.com
//            localhost:8081
        JSONObject jo = new JSONObject();
        jo.put("message", "Check your email to confirm your new password.");
        return jo.toString();
    }

    @Modifying
    @Transactional
    public void updatePassword(String token) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!Objects.equals(password, newPassword))
            throw new IllegalStateException(/*"{\"message\" : \*/"Passwords do not match.");
        else
            changePasswordRepository.updatePassword(changePasswordRepository.findConfirmedEmailByToken(token), passwordEncoder.encode(password), passwordEncoder.encode(newPassword));
//        return token;
    }

    @Transactional
    @RequestMapping("/")
    public String confirmToken(@RequestParam("token") String token) {

        ChangePassToken confirmToken = changePasswordService.getPassToken(token)
                .orElseThrow(() ->
                        new IllegalStateException(/*"{\"message\" : \*/"Token not found."));

        if (confirmToken.getConfirmedAt() != null) {
            throw new IllegalStateException(/*"{\"message\" : \*/"Code already used");
        }
        LocalDateTime expiredAt = confirmToken.getExpiredAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            token = UUID.randomUUID().toString();
            confirmToken.setToken(token);
            confirmToken.setCreatedAt(LocalDateTime.now());
            confirmToken.setExpiredAt(LocalDateTime.now().plusMinutes(15));
            changePasswordService.getChangePasswordRepository().save(confirmToken);
            emailSender.send(confirmToken.getEmail(), "The last token has expired, try enter this one <a href=\"" +
                    "https://bureaucracyhackshostat.herokuapp.com/changepassword/confirm?token=" + token + "\"> Click me </a>");
//            https://bureaucracyhackshostat.herokuapp.com
//            localhost:8081
            JSONObject jo = new JSONObject();
            jo.put("message", "Error: Token expired, check your email again for a new one.");
            return jo.toString();
        }

        changePasswordService.setChangePassConfirmedAt(token);

        updatePassword(token);
        JSONObject jo = new JSONObject();
        jo.put("message", "Password changed with success.");
        return jo.toString();
    }

}
