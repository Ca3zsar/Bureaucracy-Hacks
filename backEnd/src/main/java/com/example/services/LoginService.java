package com.example.services;

import com.example.models.Institution;
import com.example.requests.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

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
                juser.put("name", userService.getUserInfo(loginRequest.getEmail()).getName() + " " + userService.getUserInfo(loginRequest.getEmail()).getSurname());
                juser.put("is_admin", userService.getUserInfo(loginRequest.getEmail()).getIsAdmin());
                System.out.println(userService.getUserInfo(loginRequest.getEmail()).getInstitutions());
                List<Institution> institutions = userService.getUserInfo(loginRequest.getEmail()).getInstitutions();
                JSONArray jsonArray = new JSONArray();
                for (Institution institution : institutions) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", institution.getName());
                    jsonObject.put("id", institution.getId());
                    jsonArray.put(jsonObject);
                }
                juser.put("institutions", jsonArray);
                jo.put("message", "You are logged in");
                jo.put("user", juser);

                return jo.toString().replaceAll("\\\\\"","\"");
            } else {
//                JSONObject jo = new JSONObject();
//                jo.put("message","Error: Username and password do not match.");
//                return jo.toString();
                throw new IllegalStateException(/*"{\"message\" : \*/"Username and password do not match.");
            }
        } else {
//            JSONObject jo = new JSONObject();
//            jo.put("message","Error: Account not verified.");
//            return jo.toString();
            throw new IllegalStateException(/*"{\"message\" : \"*/"Account not verified.");
        }
    }
}
