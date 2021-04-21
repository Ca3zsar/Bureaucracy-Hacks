package com.example.controllers;

import com.example.services.InstitutionsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/", method = {RequestMethod.POST, RequestMethod.GET}/*, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE*/)
@AllArgsConstructor
public class InstitutionController {
    InstitutionsService institutionsService;

    @GetMapping(path = "institutionslist")
    public String getInstitutionsList() {
        return institutionsService.getInstitutionsList();
    }
    @GetMapping(path = "getlat")
    public Double getLatitude() {
        String institutionName = "ANAF";
        return institutionsService.getLatitude(institutionName);
    }


    public Double getLongitude(String institutionName) {
        return institutionsService.getLongitude(institutionName);
    }
}
