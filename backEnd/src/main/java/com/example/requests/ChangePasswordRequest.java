package com.example.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ChangePasswordRequest {

    private final String email;
    private final String newPassword;
    private final String confirmNewPassword;

}
