package com.artemsolovev.controller;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.User;
import com.artemsolovev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseResult<User>> add(@RequestBody User user) {
        try {
            this.userService.add(user);
            return new ResponseEntity<>(new ResponseResult<>(null, user), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{login}/{password}")
    public ResponseEntity<ResponseResult<User>> get(@PathVariable String login, @PathVariable String password) {
        try {
            User user = this.userService.get(login, password);
            return new ResponseEntity<>(new ResponseResult<>(null, user), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<User>> get(@PathVariable long id) {
        try {
            User user = this.userService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, user), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseResult<List<User>>> get() {
        List<User> list = this.userService.get();
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }
}
