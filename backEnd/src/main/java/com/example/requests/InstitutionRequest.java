package com.example.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONObject;

import java.util.List;

@Getter
@Setter
//@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class InstitutionRequest {
    @JsonProperty("institutions")
    private List<JSONObject> institutions;

    @JsonCreator
    public InstitutionRequest(List<JSONObject> institutions) {
        this.institutions = institutions;
    }


}
