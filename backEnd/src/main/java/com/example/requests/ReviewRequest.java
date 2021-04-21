package com.example.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ReviewRequest {
    private String username;
    private int rating;
    private String comment;
}
