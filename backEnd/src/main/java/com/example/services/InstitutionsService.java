package com.example.services;

import com.example.models.Institution;
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
        List<Institution> institutions = institutionsRepository.getInstitutionsList();
        JSONArray jinstitutions = new JSONArray();
        for (Institution institution : institutions) {
            JSONObject jo = new JSONObject();
            jo.put("name", institution.getName());
            jo.put("id", institution.getId());
            jo.put("latitude", getLatitude(institution.getName()));
            jo.put("longitude", getLongitude(institution.getName()));
            jinstitutions.put(jo);
        }
        return jinstitutions.toString();
    }

    public Double getLatitude(String institutionName) {
        return institutionsRepository.getLatitude(institutionName);
    }

    public Double getLongitude(String institutionName) {
        return institutionsRepository.getLongitude(institutionName);
    }
}
