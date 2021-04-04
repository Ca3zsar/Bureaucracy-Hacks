package com.example;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class RegistrationController {

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<User> getDataForRegister(@RequestBody Registration registration) {

        return null;
    }


}
