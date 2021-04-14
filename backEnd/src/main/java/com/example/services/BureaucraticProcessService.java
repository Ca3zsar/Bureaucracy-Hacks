package com.example.services;

import com.example.repositories.BureaucraticProcessRepository;
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
public class BureaucraticProcessService {
    private BureaucraticProcessRepository bureaucraticProcessRepository;

    public String getProcessesList() {
        List<String> processes = bureaucraticProcessRepository.getProcessesList();
        JSONArray jprocesses = new JSONArray();
        for (String process : processes) {
            JSONObject jo = new JSONObject();
            jo.put("process_name", process);
            jprocesses.put(jo);
        }
        return jprocesses.toString();
    }
}
