package com.example.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
//@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DeleteCommentRequest {
    @JsonProperty("id")
    private Integer id;

    @JsonCreator
    public DeleteCommentRequest(Integer id) {
        this.id = id;
    }
}
