package com.example.requests;

import lombok.*;
import org.json.JSONObject;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserSettingsRequest {
    private Integer userId;
    private String surname;
    private String name;
    private String email;
    private String address;
    private String serieBuletin;
    private String numarBuletin;
    private String cnp;
    private String phone;
    private String judet;
    private String localitate;
    private String dataNastere;
}
