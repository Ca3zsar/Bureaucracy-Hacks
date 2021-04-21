package com.example.controllers;

import com.example.Resources.JSONParserFiles;
import com.example.requests.InstitutionRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/admin", method = {RequestMethod.POST, RequestMethod.GET}/*, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE*/)
@AllArgsConstructor
public class AdminController {
    JSONParserFiles jsonParserFiles;

    @GetMapping(path = "updateinstitutions")
    public String updateInstitutions(@RequestBody InstitutionRequest institutionRequest){
        return jsonParserFiles.saveInstitutionList(institutionRequest.getInstitutions());
    }
}
