package com.example.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UpdatePasswordRequest {
    private final String password;
    private final String confirmPassword;
}
