package com.example.controllers;

import com.example.Resources.JSONParserFiles;
import com.example.models.Institution;
import com.example.requests.DeleteCommentRequest;
import com.example.requests.UpdateDepartmentRequest;
import com.example.requests.UpdateProcessRequest;
import com.example.requests.UpdateProcesses;
import com.example.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/admin", method = {RequestMethod.POST, RequestMethod.GET}/*, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE*/)
@AllArgsConstructor
public class AdminController {
    private JSONParserFiles jsonParserFiles;
    private CommentService commentService;

    @PostMapping(path = "updateinstitutions")
    public String updateInstitutions(@RequestBody List<Institution> institutionRequest) {
        return jsonParserFiles.updateInstitutionList(institutionRequest);
    }

    @PostMapping(path = "updatedepartments")
    public String updateDepartments(@RequestBody List<UpdateDepartmentRequest> departments) {
        return jsonParserFiles.updateDepartmentList(departments);
    }

    @PostMapping(path = "updateprocessesrequest")
    public String updateProcessesRequest(@RequestBody UpdateProcessRequest processRequest) {
        return jsonParserFiles.getProcessForUpdate(processRequest);
    }

    @PostMapping(path = "updateprocesses")
    public String updateProcesses(@RequestBody UpdateProcesses processesRequests) {
        return jsonParserFiles.updateProcessList(processesRequests);
    }

    @PostMapping(path = "deletecomment")
    public String deleteComment(@RequestBody DeleteCommentRequest deleteCommentRequest) {
        return commentService.deleteComment(deleteCommentRequest);
    }
    @PostMapping(path = "showcomment")
    public String showComment(@RequestBody DeleteCommentRequest deleteCommentRequest) {
        return commentService.showComment(deleteCommentRequest);
    }
}
