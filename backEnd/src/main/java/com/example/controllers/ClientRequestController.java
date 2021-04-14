package com.example.controllers;

import com.example.requests.FeedbackRequest;
import com.example.requests.LoginRequest;
import com.example.services.FeedbackService;
import com.example.services.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/feedback", method = {RequestMethod.POST, RequestMethod.GET})
@AllArgsConstructor
public class ClientRequestController {
    private FeedbackService feedbackService;

    @PostMapping
    public String giveFeedback(@RequestBody FeedbackRequest request) {
        return feedbackService.giveFeedback(request);
    }
}