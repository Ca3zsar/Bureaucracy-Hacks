package com.example.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class UpdateProcessRequest {
    private String institution;
    private String process;
}
