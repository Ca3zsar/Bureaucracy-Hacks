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
//    private int idRepliedComment;
}
