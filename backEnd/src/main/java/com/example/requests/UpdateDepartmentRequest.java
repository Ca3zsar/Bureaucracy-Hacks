package com.example.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.json.JSONArray;

import java.util.List;

@Getter
@Setter
//@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class UpdateDepartmentRequest {
//    @JsonProperty("institutionName")
    private String institutie;
    private List<String> departamente;

    public UpdateDepartmentRequest(String institutie, List<String> departamente) {
        this.institutie = institutie;
        this.departamente = departamente;
    }
}
