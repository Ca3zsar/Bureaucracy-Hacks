package com.example.controllers;


import com.example.requests.ChangePasswordRequest;
import com.example.services.ChangePasswordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/changepassword", method = {RequestMethod.POST, RequestMethod.GET}/*, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE*/)
@AllArgsConstructor

public class ChangePasswordController {
    private ChangePasswordService changePasswordService;

    @PostMapping
    public String changePassword(@RequestBody ChangePasswordRequest request) {
        return changePasswordService.changePassword(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return changePasswordService.confirmToken(token);
    }
}
