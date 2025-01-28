package com.artemsolovev.controllers;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.Compliment;
import com.artemsolovev.model.UserDetailsImpl;
import com.artemsolovev.service.ComplimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/compliment")
public class WordController {

    private ComplimentService complimentService;

    @Autowired
    public void setComplimentService(ComplimentService complimentService) {
        this.complimentService = complimentService;
    }

    @GetMapping
    public ResponseEntity<ResponseResult<List<Compliment>>> get() {
        List<Compliment> list = this.complimentService.get();
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Compliment>> get(@PathVariable int id) {
        try {
            Compliment compliment = this.complimentService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, compliment), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/random")
    public ResponseEntity<ResponseResult<Compliment>> getRandom(Authentication authentication) {
        try {
            if(authentication != null && authentication.isAuthenticated()) {
                long idUser = ((UserDetailsImpl) authentication.getPrincipal()).getId();
                Compliment compliment = this.complimentService.getRandom(idUser);
                return new ResponseEntity<>(new ResponseResult<>(null, compliment), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseResult<>(null, null), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
