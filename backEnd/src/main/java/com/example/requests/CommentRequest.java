package com.example.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CommentRequest {
    private String username;
//    private String process;
    private String comment;
    private Boolean q1;
    private Integer q2;
    private String q3;
    private Integer q4;
//    private int idRepliedComment;
}
