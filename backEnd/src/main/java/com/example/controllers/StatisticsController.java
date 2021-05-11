package com.example.controllers;

import com.example.resources.CalculateStatistics;
import com.example.requests.UpdateProcessRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
//import sun.misc.Cache;

@RestController
@RequestMapping(path = "/", method = {RequestMethod.POST, RequestMethod.GET}/*, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE*/)
@AllArgsConstructor
public class StatisticsController {

    private CalculateStatistics calculateStatistics;
    @RequestMapping(path = "/statistics", method = {RequestMethod.POST, RequestMethod.GET})
    public String calculateStaistics(@RequestBody UpdateProcessRequest updateProcessRequest){
        return calculateStatistics.calculateStatistics(updateProcessRequest.getProcess());
    }


}
