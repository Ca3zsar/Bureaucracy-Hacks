package com.example.services;

import com.example.models.ChangePassToken;
import com.example.models.ConfirmToken;
import com.example.models.User;
import com.example.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final static String USER_NOT_FOUND = "user with email %s not found";
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmTokenService confirmTokenService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s).orElseThrow(() -> new UsernameNotFoundException(/*"{\"message\" : \"" + */String.format(USER_NOT_FOUND, s)/* + "\"}"*/));
        return user;
    }

    public String signUpUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
//            JSONObject jo = new JSONObject();
//            jo.put("message","Error: Username is already taken.");
            throw new IllegalStateException(/*"{\"message\" : \*/"Username is already taken.");
        }

        user.setHashPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        /**
         * Create token for confirmation account via email
         */
        String token = UUID.randomUUID().toString();
        ConfirmToken confirmToken = new ConfirmToken(token, user, LocalDateTime.now(), LocalDateTime.now().plusSeconds(15));

        confirmTokenService.saveConfirmationToken(confirmToken);
        return token;
    }

    public String changePassUser(String email) {
        if (!userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException(/*"{\"message\" : \*/"Email doesn't correspond to any account.");
        }
        String token = UUID.randomUUID().toString();
        ChangePassToken confirmToken = new ChangePassToken(token, email, LocalDateTime.now(), LocalDateTime.now().plusSeconds(15));

        confirmTokenService.saveConfirmationToken(confirmToken);

        return token;

    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

    public User getUserInfo(String email) {
        return userRepository.getUserInfo(email);
    }

    public boolean getEnabledUser(String email) {
        return userRepository.getEnabledUser(email);
    }

}
