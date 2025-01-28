package com.artemsolovev.controllers;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.Request;
import com.artemsolovev.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestController {
    private RequestService requestService;

    @Autowired
    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping(path = "/{idUser}")
    public ResponseEntity<ResponseResult<Request>> add(@RequestBody Request request, @PathVariable long idUser) {
        try {
            this.requestService.add(idUser, request);
            return new ResponseEntity<>(new ResponseResult<>(null, request), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping()
    public ResponseEntity<ResponseResult<Request>> makeDecision(@RequestParam long id, @RequestParam boolean decision, @RequestParam String comment) {
        try {
            Request res = this.requestService.makeDecision(id, decision, comment);
            return new ResponseEntity<>(new ResponseResult<>(null, res), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping
    public ResponseEntity<ResponseResult<List<Request>>> get() {
        List<Request> list = this.requestService.get();
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }

    @GetMapping(path = "/{idUser}")
    public ResponseEntity<ResponseResult<List<Request>>> getByIdUser(@PathVariable long idUser) {
        List<Request> list = this.requestService.get(idUser);
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }
}
