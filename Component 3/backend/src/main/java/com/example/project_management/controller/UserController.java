package com.example.project_management.controller;

import com.example.project_management.model.User;
import com.example.project_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User returnedUser = userService.createUser(user);
        return new ResponseEntity<>(returnedUser, HttpStatus.CREATED);
    }

    @PostMapping("/sign")
    public ResponseEntity<User> signIn(@RequestBody User user){
        User status = userService.login(user);
        if(status != null) {
            return new ResponseEntity<>(status, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(status,HttpStatus.BAD_REQUEST);
        }
    }
}
