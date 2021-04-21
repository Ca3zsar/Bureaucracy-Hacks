package com.example.services;

import com.example.repositories.InstitutionsRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Setter
@Controller
public class InstitutionsService {
private InstitutionsRepository institutionsRepository;
    public String getInstitutionsList() {
        List<String> institutions = institutionsRepository.getInstitutionsList();
        JSONArray jinstitutions = new JSONArray();
        for (String institution : institutions) {
            JSONObject jo = new JSONObject();
            jo.put("name", institution);
            jinstitutions.put(jo);
        }
        return jinstitutions.toString();
    }
    public Double getLatitude(String institutionName){
        return institutionsRepository.getLatitude(institutionName);
    }
    public Double getLongitude(String institutionName){
        return institutionsRepository.getLongitude(institutionName);
    }
}
