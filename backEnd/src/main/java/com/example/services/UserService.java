package com.example.services;

import com.example.models.*;
import com.example.repositories.InstitutionAdminRepository;
import com.example.repositories.InstitutionsRepository;
import com.example.repositories.PersonalDataUsersRepository;
import com.example.repositories.UserRepository;
import com.example.requests.UserSettingsRequest;
import com.example.resources.EncryptDecrypt;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import net.sf.json.JSONObject;
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
    private final PersonalDataUsersRepository personalDataUsersRepository;
    private final static String USER_NOT_FOUND = "user with email %s not found";
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmTokenService confirmTokenService;
    private final InstitutionAdminRepository institutionAdminRepository;
    private final InstitutionsRepository institutionsRepository;
    private final EncryptDecrypt encryptDecrypt;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s).orElseThrow(() -> new UsernameNotFoundException(/*"{\"message\" : \"" + */String.format(USER_NOT_FOUND, s)/* + "\"}"*/));
        return user;
    }

    public String signUpUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
//            JSONObject jo = new JSONObject();
//            jo.put("message","Error: Username is already taken.");
            throw new IllegalStateException(/*"{\"message\" : \*/"Email is already taken.");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException(/*"{\"message\" : \*/"Username is already taken.");
        }
        user.setHashPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        /**
         * Create token for confirmation account via email
         */
        String token = UUID.randomUUID().toString();
        ConfirmToken confirmToken = new ConfirmToken(token, user, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15));

        confirmTokenService.saveConfirmationToken(confirmToken);
        return token;
    }

    public String changePassUser(String email) {
        if (!userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException(/*"{\"message\" : \*/"Email doesn't correspond to any account.");
        }
        String token = UUID.randomUUID().toString();
        ChangePassToken confirmToken = new ChangePassToken(token, email, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15));

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

    public int makeAdmin(String email) {
        return userRepository.makeAdmin(email);
    }

    public int makeNotAdmin(String email) {
        return userRepository.makeNotAdmin(email);
    }

    public void makeInstitutionAdmin(Integer institution, Integer user) {
        if (!institutionsRepository.findById(institution).isPresent())
            throw new IllegalStateException("Institution not found");
        if (institutionAdminRepository.findByIdInstitution(institution).isPresent() && institutionAdminRepository.findByIdUser(user).isPresent())
            throw new IllegalStateException("User-institution already present");
        if (institutionAdminRepository.findByIdInstitution(institution).isPresent())
            throw new IllegalStateException("Institution already has an admin");
        if (institutionAdminRepository.findByIdUser(user).isPresent())
            throw new IllegalStateException("User is already an institution admin");

        institutionAdminRepository.save(new InstitutionAdmin(institution, user));
    }

    public int makeNotInstitutionAdmin(Integer institution, Integer user) {
        if (!institutionsRepository.findById(institution).isPresent())
            throw new IllegalStateException("Institution not found");
        if (!institutionAdminRepository.findByIdInstitution(institution).isPresent() || !institutionAdminRepository.findByIdUser(user).isPresent())
            throw new IllegalStateException("User-institution combination not found.");
        return institutionAdminRepository.makeNotInstitutionAdmin(institution, user);
    }

    public String addPersonalData(UserSettingsRequest userSettingsRequest){
        userRepository.updateSettings(userSettingsRequest.getName(),userSettingsRequest.getSurname(),userSettingsRequest.getEmail(), userSettingsRequest.getUserId());

//        SET i.address=?1, i.phone=?2,i.ziNastere=?3, i.lunaNastere=?5, i.anNastere=?6,
//        i.cnp = ?7, i.judet=?8, i.localitate=?9, i.numarBuletin=?10, i.serieBuletin=?11
        if(personalDataUsersRepository.findByUser(userSettingsRequest.getUserId()) != 0) {
            String encryptedCNP = encryptDecrypt.encrypt(userSettingsRequest.getCnp(), personalDataUsersRepository.getKey(userSettingsRequest.getUserId()));
            String encryptedSerieBuletin = encryptDecrypt.encrypt(userSettingsRequest.getSerieBuletin(), personalDataUsersRepository.getKey(userSettingsRequest.getUserId()));
            String encryptedNumarBuletin = encryptDecrypt.encrypt(userSettingsRequest.getNumarBuletin(), personalDataUsersRepository.getKey(userSettingsRequest.getUserId()));
            personalDataUsersRepository.updatePersonalData(
                    userSettingsRequest.getAddress(),
                    userSettingsRequest.getPhone(),
                    userSettingsRequest.getDataNastere(),
                    userSettingsRequest.getUserId(),
                    encryptedCNP,
                    userSettingsRequest.getJudet(),
                    userSettingsRequest.getLocalitate(),
                    encryptedNumarBuletin,
                    encryptedSerieBuletin
            );
        }
        else
        {
//            Integer userId, String address, String serieBuletin, String numarBuletin, String cnp, String phone,
//            String judet, String localitate, String ziNastere, String lunaNastere, String anNastere, String key
            String key = UUID.randomUUID().toString();
            String encryptedCNP = encryptDecrypt.encrypt(userSettingsRequest.getCnp(), key);
            String encryptedSerieBuletin = encryptDecrypt.encrypt(userSettingsRequest.getSerieBuletin(), key);
            String encryptedNumarBuletin = encryptDecrypt.encrypt(userSettingsRequest.getNumarBuletin(), key);
            System.out.println(userSettingsRequest.getDataNastere());
            personalDataUsersRepository.save( new PersonalDataUsers(
                    userSettingsRequest.getUserId(),
                    userSettingsRequest.getAddress(),
                    encryptedSerieBuletin,
                    encryptedNumarBuletin,
                    encryptedCNP,
                    userSettingsRequest.getPhone(),
                    userSettingsRequest.getJudet(),
                    userSettingsRequest.getLocalitate(),
                    userSettingsRequest.getDataNastere(),
                    key
                    ));

        }
        JSONObject response = new JSONObject();
        response.put("message", "Update successful");
        return response.toString();
    }
}
