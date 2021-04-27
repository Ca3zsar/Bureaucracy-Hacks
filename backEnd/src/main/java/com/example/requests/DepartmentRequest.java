package com.example.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONArray;

@Getter
@Setter
//@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DepartmentRequest {
        @JsonProperty("institutionName")
    private String institutionName;

    @JsonCreator
    public DepartmentRequest(String institutionName) {
        this.institutionName = institutionName;
    }
}
