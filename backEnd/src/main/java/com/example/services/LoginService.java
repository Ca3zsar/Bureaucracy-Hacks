package com.example.services;

import com.example.models.Institution;
import com.example.repositories.PersonalDataUsersRepository;
import com.example.repositories.UserRepository;
import com.example.requests.LoginRequest;
import com.example.resources.EncryptDecrypt;
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
    private final PersonalDataUsersRepository personalDataUsersRepository;
    private final EncryptDecrypt encryptDecrypt;

    public String login(LoginRequest loginRequest) {

        String hashedPassword = userService.loadUserByUsername(loginRequest.getEmail()).getPassword();
        if (userService.getEnabledUser(loginRequest.getEmail())) {
            if (BCrypt.checkpw(loginRequest.getPassword(), hashedPassword)) {

                JSONObject jo = new JSONObject();
                JSONObject juser = new JSONObject();
                juser.put("username", userService.loadUserByUsername(loginRequest.getEmail()).getUsername());
                juser.put("name", userService.getUserInfo(loginRequest.getEmail()).getName());
                juser.put("surname", userService.getUserInfo(loginRequest.getEmail()).getSurname());
                juser.put("email", userService.getUserInfo(loginRequest.getEmail()).getEmail());
                juser.put("is_admin", userService.getUserInfo(loginRequest.getEmail()).getIsAdmin());
//                System.out.println(userService.getUserInfo(loginRequest.getEmail()).getInstitutions());
                List<Institution> institutions = userService.getUserInfo(loginRequest.getEmail()).getInstitutions();
                JSONArray jsonArray = new JSONArray();
                for (Institution institution : institutions) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", institution.getName());
                    jsonObject.put("id", institution.getId());
                    jsonArray.put(jsonObject);
                }
                juser.put("institutions", jsonArray);
                Integer userId = userService.getUserInfo(loginRequest.getEmail()).getRegistrationId();
                juser.put("cnp", personalDataUsersRepository.findByUserId(userId) != null ? encryptDecrypt.decrypt(personalDataUsersRepository.findByUserId(userId).getCnp(), personalDataUsersRepository.getKey(userId)) : "");
                juser.put("serieBuletin", personalDataUsersRepository.findByUserId(userId) != null ? encryptDecrypt.decrypt(personalDataUsersRepository.findByUserId(userId).getSerieBuletin(), personalDataUsersRepository.getKey(userId)) : "");
                juser.put("numarBuletin", personalDataUsersRepository.findByUserId(userId) != null ? encryptDecrypt.decrypt(personalDataUsersRepository.findByUserId(userId).getNumarBuletin(), personalDataUsersRepository.getKey(userId)) : "");
                juser.put("address", personalDataUsersRepository.findByUserId(userId) != null ? personalDataUsersRepository.findByUserId(userId).getAddress() : "");
                juser.put("judet", personalDataUsersRepository.findByUserId(userId) != null ? personalDataUsersRepository.findByUserId(userId).getJudet() : "");
                juser.put("localitate", personalDataUsersRepository.findByUserId(userId) != null ? personalDataUsersRepository.findByUserId(userId).getLocalitate() : "");
                juser.put("phone", personalDataUsersRepository.findByUserId(userId) != null ? personalDataUsersRepository.findByUserId(userId).getPhone() : "");
                juser.put("dataNastere", personalDataUsersRepository.findByUserId(userId) != null ? personalDataUsersRepository.findByUserId(userId).getDataNastere() : "");
                juser.put("userId", userId);
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
