package com.example.controllers;

import com.example.services.BureaucraticProcessService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/", method = {RequestMethod.POST, RequestMethod.GET}/*, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE*/)
@AllArgsConstructor
public class BureaucraticProcessController {
    BureaucraticProcessService bureaucraticProcessService;
    @GetMapping(path = "processeslist")
    public String getProcessesList() {
        return bureaucraticProcessService.getProcessesList();
    }
}
