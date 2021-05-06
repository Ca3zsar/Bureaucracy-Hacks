package com.example.Resources;

import com.example.models.BureaucraticProcess;
import com.example.models.Department;
import com.example.models.Institution;
import com.example.repositories.BureaucraticProcessRepository;
import com.example.repositories.DepartmentRepository;
import com.example.repositories.InstitutionsRepository;
import com.example.requests.UpdateDepartmentRequest;
import com.example.requests.UpdateProcessRequest;
import com.example.requests.UpdateProcesses;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class JSONParserFiles {
    InstitutionsRepository institutionsRepository;
    DepartmentRepository departmentRepository;
    BureaucraticProcessRepository bureaucraticProcessRepository;

    @Transactional
    @Modifying
    public String updateInstitutionList(List<Institution> fileContent) {

        JSONArray jsonArray = new JSONArray(fileContent);
//        System.out.println(jsonArray.getJSONObject(0));
        int i = 0;
        while (i < jsonArray.length()) {
            Institution institution = new Institution();
            institution.setName(jsonArray.getJSONObject(i).getString("name"));
            institution.setPhone(jsonArray.getJSONObject(i).getString("phone"));
            institution.setEmail(jsonArray.getJSONObject(i).getString("email"));
            institution.setAddress(jsonArray.getJSONObject(i).getString("address"));
            institution.setSite(jsonArray.getJSONObject(i).getString("site"));
//            System.out.println(jsonArray.getJSONObject(i));
            if (!institutionsRepository.findByName(jsonArray.getJSONObject(i).getString("name")).isPresent())
                institutionsRepository.save(institution);
            else {
                System.out.println(jsonArray.getJSONObject(i));
                institutionsRepository.updateInstitution(jsonArray.getJSONObject(i).getString("name"), jsonArray.getJSONObject(i).getString("phone"), jsonArray.getJSONObject(i).getString("email"), institutionsRepository.findByName(jsonArray.getJSONObject(i).getString("name")).get().getId(), jsonArray.getJSONObject(i).getString("site"), jsonArray.getJSONObject(i).getString("address"));
            }
            i++;
        }
        JSONObject jo = new JSONObject();
        jo.put("message", "Institutions have been updated");
        return jo.toString();
    }

    @Transactional
    @Modifying
    public String updateDepartmentList(List<UpdateDepartmentRequest> fileContent) {

        JSONArray jsonArray = new JSONArray(fileContent);
//        System.out.println(fileContent);
        int i = 0;
        while (i < jsonArray.length()) {

//            System.out.println(jsonArray.getJSONObject(i));
            JSONArray departamente = jsonArray.getJSONObject(i).getJSONArray("departamente");
            int j = 0;
            while (j < departamente.length()) {
                Department department = new Department();
                department.setName(departamente.getString(j));
                System.out.println(institutionsRepository.findByName(jsonArray.getJSONObject(i).getString("institutie")).get());
                department.setInstitution(institutionsRepository.findByName(jsonArray.getJSONObject(i).getString("institutie")).get());
                department.setProgram("{}");
                if (departmentRepository.findByName(department.getName()).orElse(null) == null) {
                    departmentRepository.save(department);
                }
                j++;
            }
            i++;
        }
        JSONObject jo = new JSONObject();
        jo.put("message", "Departments have been updated");
        return jo.toString();
    }

    @Transactional
    @Modifying
    public String updateProcessList(UpdateProcesses fileContent) {

        BureaucraticProcess bureaucraticProcess = new BureaucraticProcess();
        bureaucraticProcess.setId(fileContent.getId());
        bureaucraticProcess.setName(fileContent.getProcess());
        bureaucraticProcess.setInstitution(institutionsRepository.findByName(fileContent.getInstitution()).get().getId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("departaments", new JSONArray(fileContent.getDepartaments()));
        jsonObject.put("cases", new JSONArray(fileContent.getCases()));
        jsonObject.put("forms", new JSONArray(fileContent.getForms()));
        jsonObject.put("necessary", new JSONArray(fileContent.getNecessary()));
        jsonObject.put("generalInfo", new JSONArray(fileContent.getGeneralInfo()));
        jsonObject.put("prices", new JSONArray(fileContent.getPrices()));
        jsonObject.put("files", new JSONArray(fileContent.getFiles()));
        bureaucraticProcess.setUsefulInformation(jsonObject.toString());
        if (!bureaucraticProcessRepository.findByName(bureaucraticProcess.getName()).isPresent())
            bureaucraticProcessRepository.save(bureaucraticProcess);
        else {
            if (!bureaucraticProcessRepository.findById(bureaucraticProcess.getId()).isPresent())
                bureaucraticProcessRepository.save(bureaucraticProcess);
            else
                bureaucraticProcessRepository.updateBureaucraticProcess(bureaucraticProcess.getName(), bureaucraticProcess.getInstitution(), bureaucraticProcess.getUsefulInformation(), bureaucraticProcess.getName());
        }
        JSONObject jo = new JSONObject();
        jo.put("message", "Process \"" + bureaucraticProcess.getName() + "\" has been updated");
        return jo.toString();
    }

    @Transactional
    @Modifying
    public String updateProgram(String programsList) {
        JSONArray programe = new JSONArray(programsList);
        List<String> departments = new ArrayList<>();
        List<JSONObject> programs = new ArrayList<>();
        int i = 0;
        while (i < programe.length()) {
            departments.add(programe.getJSONObject(i).getString("titlu"));
            programs.add(programe.getJSONObject(i).getJSONObject("program"));
            if (departmentRepository.findByName(departments.get(i)).isPresent()) {

                departmentRepository.updateDepartmentProgram(programs.get(i).toString(), departmentRepository.findByName(departments.get(i)).get().getId());
            }
            i++;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Departments have been updated with success.");
        return jsonObject.toString();
    }

    public String getProcessForUpdate(UpdateProcessRequest updateProcessRequest) {

        BureaucraticProcess bureaucraticProcess = bureaucraticProcessRepository.findByName(updateProcessRequest.getProcess()).get();

        if (bureaucraticProcess != null) {
            JSONObject jo = new JSONObject();
            jo.put("id", bureaucraticProcess.getId());
            jo.put("process", bureaucraticProcess.getName());
            jo.put("institution", institutionsRepository.findById(bureaucraticProcess.getInstitution()).get().getName());
            JSONObject usefulInformation = new JSONObject(bureaucraticProcess.getUsefulInformation());
            jo.put("departaments", usefulInformation.getJSONArray("departaments"));
            jo.put("cases", usefulInformation.getJSONArray("cases"));
            jo.put("forms", usefulInformation.getJSONArray("forms"));
            jo.put("necessary", usefulInformation.getJSONArray("necessary"));
            jo.put("generalInfo", usefulInformation.getJSONArray("generalInfo"));
            jo.put("prices", usefulInformation.getJSONArray("prices"));
            jo.put("files", usefulInformation.getJSONArray("files"));
            return jo.toString();
        } else throw new IllegalStateException("Required process doesn't exist");
    }

}
