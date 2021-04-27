package com.example.services;

import com.example.models.BureaucraticProcess;
import com.example.models.Institution;
import com.example.repositories.BureaucraticProcessRepository;
import com.example.repositories.InstitutionsRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Setter
@Controller
public class BureaucraticProcessService {
    private BureaucraticProcessRepository bureaucraticProcessRepository;
    private InstitutionsRepository institutionsRepository;

    public String getProcessesList() {
        List<BureaucraticProcess> processes = bureaucraticProcessRepository.getProcessesList();
        JSONArray jprocesses = new JSONArray();
        for (BureaucraticProcess process : processes) {
            JSONObject jo = new JSONObject();
            jo.put("name", process.getName());
            jprocesses.put(jo);
        }
        return jprocesses.toString();
    }

    public String getProcessesList(String institutionName) {
        List<BureaucraticProcess> processes = bureaucraticProcessRepository.getProcessesList();
        JSONArray jprocesses = new JSONArray();
        for (BureaucraticProcess process : processes) {
            String institution = institutionsRepository.findById(process.getInstitution()).get().getName();
            if(Objects.equals(institution,institutionName)) {
                JSONObject jo = new JSONObject();
                jo.put("name", process.getName());
                jprocesses.put(jo);
            }
        }
        return jprocesses.toString();
    }
}
