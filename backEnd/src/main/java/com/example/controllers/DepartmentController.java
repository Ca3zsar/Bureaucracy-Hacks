package com.example.controllers;

import com.example.requests.DepartmentRequest;
import com.example.services.DepartmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/", method = {RequestMethod.POST, RequestMethod.GET}/*, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE*/)
@AllArgsConstructor
public class DepartmentController {
    DepartmentService departmentService;

    @GetMapping(path = "departmentslist")
    public String getDepartmentsList(@RequestBody DepartmentRequest departmentRequest){
        return departmentService.getDepartmentsList(departmentRequest);
    }
}
