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
public class GetFileLinkRequest {
    @JsonProperty("filename")
    String fileName;

    @JsonCreator
    public GetFileLinkRequest(String fileName) {
        this.fileName = fileName;
    }
}
