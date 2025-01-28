package com.artemsolovev.controller;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.User;
import com.artemsolovev.security.jwt.JwtTokenProvider;
import com.artemsolovev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;
    private UserService userService;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<User>> get(@PathVariable long id) {
        User user = this.userService.get(id);
        return new ResponseEntity<>(new ResponseResult<>(null, user), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseResult<String>> login(@RequestParam String login, @RequestParam String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        User user = userService.get(login);

        String token = jwtTokenProvider.createToken(user.getId(), user.getLogin(), user.getClass().getSimpleName());

        return new ResponseEntity<>(new ResponseResult<>(null, token), HttpStatus.OK);
    }
}
