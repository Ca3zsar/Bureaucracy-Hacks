package com.example.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AddAdminInstitutionRequest {
    String email;
    Integer institution;
}
