package com.artemsolovev.controller;

import com.artemsolovev.dto.ResponseResult;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.StringJoiner;

@ControllerAdvice
public class ExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseResult<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringJoiner errors = new StringJoiner(", ");
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errors.add(error.getDefaultMessage());
        });
        return new ResponseEntity<>(new ResponseResult<>(errors.toString(), null), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseResult<Object>> handleIllegalArgumentExceptions(IllegalArgumentException ex) {
        return new ResponseEntity<>(new ResponseResult<>(ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}
