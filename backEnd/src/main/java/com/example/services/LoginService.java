package com.example.services;

import com.example.requests.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Setter
public class LoginService {

    private final UserService userService;

    public String login(LoginRequest loginRequest) {

        String hashedPassword = userService.loadUserByUsername(loginRequest.getEmail()).getPassword();
        if (userService.getEnabledUser(loginRequest.getEmail())) {
            if (BCrypt.checkpw(loginRequest.getPassword(), hashedPassword)) {

                JSONObject jo = new JSONObject();
                JSONObject juser = new JSONObject();
                juser.put("username", userService.loadUserByUsername(loginRequest.getEmail()).getUsername());
                juser.put("name", userService.getUserInfo(loginRequest.getEmail()).getName() + " " + userService.getUserInfo(loginRequest.getEmail()).getUsername());
                juser.put("is_admin", userService.getUserInfo(loginRequest.getEmail()).getIsAdmin());
                jo.put("message", "You are logged in");
                jo.put("user", juser.toString());
                return jo.toString();
            } else {
                JSONObject jo = new JSONObject();
                jo.put("message","Error: Username and password do not match.");
                return jo.toString();
//                throw new IllegalStateException(/*"{\"message\" : \*/"Username and password do not match.");
            }
        } else {
            JSONObject jo = new JSONObject();
            jo.put("message","Error: Account not verified.");
            return jo.toString();
//            throw new IllegalStateException(/*"{\"message\" : \"*/"Account not verified.");
        }
    }
}
