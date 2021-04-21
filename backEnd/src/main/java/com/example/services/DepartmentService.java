package com.example.services;

import com.example.models.Department;
import com.example.repositories.DepartmentRepository;
import com.example.requests.DepartmentRequest;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
@JsonSerialize
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DepartmentService {
    DepartmentRepository departmentRepository;

    public String getDepartmentsList(DepartmentRequest departmentRequest) {
        List<Department> departments = departmentRepository.getDepartmentsList(departmentRequest.getInstitutionName());
        JSONArray jdepartments = new JSONArray();
        for (Department department : departments) {
            JSONObject jo = new JSONObject();
//            jo.put("institution", department.getInstitution().getName());
            jo.put("name", department.getName());
            JSONObject program = new JSONObject(department.getProgram());
            jo.put("program", program);
            jdepartments.put(jo);
        }
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
//        return  objectMapper.writeValueAsString(jdepartments);
        String response = jdepartments.toString().replaceAll("\\\\n", " ");
        response = response.replaceAll("\\\\\"", "\"");
        return response;
    }
}
