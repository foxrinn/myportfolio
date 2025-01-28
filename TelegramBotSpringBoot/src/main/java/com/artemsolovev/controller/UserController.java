package com.artemsolovev.controller;


import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.User;
import com.artemsolovev.service.UserService;
import com.artemsolovev.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ResponseResult<List<User>>> get() {
        List<User> list = this.userService.get();
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }
}
