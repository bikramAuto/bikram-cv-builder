package com.c.project.openCV.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.c.project.openCV.dto.ResponseDTO;
import com.c.project.openCV.exception.UserNotFoundException;
import com.c.project.openCV.exception.UsernameAlreadyTakenException;
import com.c.project.openCV.service.UserService;
import com.c.project.openCV.user.User;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody User user) {
        try {
            ResponseDTO response = userService.registerUser(user);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (UsernameAlreadyTakenException e) {
            return new ResponseEntity<>(new ResponseDTO(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestBody User user) {
        try {
            ResponseDTO response = userService.loginUser(user.getUsername(), user.getPassword());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException | IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseDTO(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }
}
