package com.example.controllers;

import com.example.resources.JSONParserFiles;
import com.example.models.Department;
import com.example.models.Institution;
import com.example.repositories.AnexeRepository;
import com.example.repositories.DepartmentRepository;
import com.example.repositories.InstitutionsRepository;
import com.example.repositories.UserRepository;
import com.example.requests.*;
import com.example.services.CommentService;
import com.example.services.ReviewService;
import com.example.services.UserService;
import lombok.AllArgsConstructor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/admin", method = {RequestMethod.POST, RequestMethod.GET}/*, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE*/)
@AllArgsConstructor
public class AdminController {
    private JSONParserFiles jsonParserFiles;
    private CommentService commentService;
    private ReviewService reviewService;
    private InstitutionsRepository institutionsRepository;
    private DepartmentRepository departmentRepository;
    private UserService userService;
    private AnexeRepository anexeRepository;
    private UserRepository userRepository;

    @PostMapping(path = "updateinstitutions")
    public String updateInstitutions(@RequestBody List<Institution> institutionRequest) {
        return jsonParserFiles.updateInstitutionList(institutionRequest);
    }

    @GetMapping(path = "updateinstitutionsrequest")
    public String updateInstitutionsRequest() {
        List<Institution> institutions = institutionsRepository.getInstitutionsList();
        JSONArray jsonArray = new JSONArray();
        for (Institution institution : institutions) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", institution.getName());
            jsonObject.put("phone", institution.getPhone());
            jsonObject.put("site", institution.getSite());
            jsonObject.put("address", institution.getAddress());
            jsonObject.put("email", institution.getEmail());
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }

    @PostMapping(path = "updatedepartments")
    public String updateDepartments(@RequestBody List<UpdateDepartmentRequest> departments) {
        return jsonParserFiles.updateDepartmentList(departments);
    }

    @GetMapping(path = "updatedepartmentsrequest")
    public String updateDepartmentsRequest() {
        List<Institution> institutions = institutionsRepository.getInstitutionsList();
        JSONArray jsonArray = new JSONArray();
        for (Institution institution : institutions) {
            List<Department> departments = departmentRepository.getDepartmentsList(institution.getName());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("institutie", institution.getName());
            JSONArray jsonArray1 = new JSONArray();
            for (Department department : departments) {
                jsonArray1.add(department.getName());
            }
            jsonObject.put("departamente", jsonArray1);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }

    @PostMapping(path = "updateprocessesrequest")
    public String updateProcessesRequest(@RequestBody UpdateProcessRequest processRequest) {
        return jsonParserFiles.getProcessForUpdate(processRequest);
    }

    @PostMapping(path = "updateprocesses")
    public String updateProcesses(@RequestBody UpdateProcesses processesRequests) {
        return jsonParserFiles.updateProcessList(processesRequests);
    }

    @PostMapping(path = "updateprograms")
    public String updatePrograms(@RequestBody String updateProgramRequest) {
        JSONObject jsonObject = JSONObject.fromObject(updateProgramRequest);
        institutionsRepository.updateInstitutionProgram(jsonObject.getJSONObject("orar").getJSONArray("programe").toString(), institutionsRepository.findByName(jsonObject.getJSONObject("orar").getString("institutie")).get().getId());
        return jsonParserFiles.updateProgram(jsonObject.getJSONObject("orar").getJSONArray("programe").toString());
    }

    @PostMapping(path = "updateprogramsrequest")
    public String updateProgramsRequest(@RequestBody String institution) {
        JSONObject jsonObject = JSONObject.fromObject(institution);
        JSONObject jo = new JSONObject();
        JSONObject jo2 = new JSONObject();
        jo2.put("institutie", jsonObject.getString("institution"));
        jo2.put("programe", institutionsRepository.getPrograms(jsonObject.getString("institution")));
        jo.put("orar", jo2);
        if (institutionsRepository.findByName(jsonObject.getString("institution")).isPresent()) {
            return jo.toString();
        } else
            throw new IllegalStateException("Required institution does not exist.");
    }

    @PostMapping(path = "deletecomment")
    public String deleteComment(@RequestBody DeleteCommentRequest deleteCommentRequest) {
        return commentService.deleteComment(deleteCommentRequest);
    }

    @PostMapping(path = "deleteReview")
    public String deleteReview(@RequestBody DeleteCommentRequest deleteCommentRequest) {
        return reviewService.deleteReview(deleteCommentRequest);
    }

    @PostMapping(path = "showcomment")
    public String showComment(@RequestBody DeleteCommentRequest deleteCommentRequest) {
        return commentService.showComment(deleteCommentRequest);
    }

    @PostMapping(path = "addAdmin")
    public String addAdmin(@RequestBody AddAdminRequest addAdminRequest) {
        int modifiedRows = userService.makeAdmin(addAdminRequest.getEmail());
        JSONObject jsonObject = new JSONObject();
        if (modifiedRows == 1)
            jsonObject.put("message", "User altered with success.");
        else throw new IllegalStateException("User not found");
        return jsonObject.toString();
    }

    @PostMapping(path = "deleteAdmin")
    public String deleteAdmin(@RequestBody AddAdminRequest addAdminRequest) {
        int modifiedRows = userService.makeNotAdmin(addAdminRequest.getEmail());
        JSONObject jsonObject = new JSONObject();
        if (modifiedRows == 1)
            jsonObject.put("message", "User altered with success.");
        else throw new IllegalStateException("User not found");
        return jsonObject.toString();
    }

    @PostMapping(path = "addInstitutionAdmin")
    public String addInstitutionAdmin(@RequestBody AddAdminInstitutionRequest addAdminRequest) {
        if (!userRepository.findByEmail(addAdminRequest.getEmail()).isPresent())
            throw new IllegalStateException("User not found");

        userService.makeAdmin(addAdminRequest.getEmail());
        userService.makeInstitutionAdmin(addAdminRequest.getInstitution(), userService.getUserInfo(addAdminRequest.getEmail()).getRegistrationId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "User altered with success.");

        return jsonObject.toString();
    }

    @PostMapping(path = "deleteInstitutionAdmin")
    public String deleteInstitutionAdmin(@RequestBody AddAdminInstitutionRequest addAdminRequest) {
        if (!userRepository.findByEmail(addAdminRequest.getEmail()).isPresent())
            throw new IllegalStateException("User not found");
        int modifiedRows = userService.makeNotInstitutionAdmin(addAdminRequest.getInstitution(), userService.getUserInfo(addAdminRequest.getEmail()).getRegistrationId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "User altered with success.");
        return jsonObject.toString();
    }

    @PostMapping(path = "getfilelink")
    public String getFileLink(@RequestBody String fileName) {
        org.json.JSONObject jsonObject = new org.json.JSONObject(fileName);
        return anexeRepository.getFileLink(jsonObject.getString("fileName"));
    }
}
