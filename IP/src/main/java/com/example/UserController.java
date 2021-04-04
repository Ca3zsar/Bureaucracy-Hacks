package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class UserController {


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<User> getDataForLogin(@RequestBody Login login) {

        return null;
    }

}
