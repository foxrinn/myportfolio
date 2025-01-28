package com.artemsolovev.controller;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.Apprentice;
import com.artemsolovev.model.User;
import com.artemsolovev.security.jwt.UserDetailsImpl;
import com.artemsolovev.service.ApprenticeService;
import com.artemsolovev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apprentice")
public class ApprenticeController {
    private ApprenticeService apprenticeService;
    private UserService userService;

    @Autowired
    public void setApprenticeService(ApprenticeService apprenticeService) {
        this.apprenticeService = apprenticeService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseResult<Apprentice>> add(@RequestBody Apprentice apprentice) {
        this.apprenticeService.add(apprentice);
        return new ResponseEntity<>(new ResponseResult<>(null, apprentice), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseResult<List<Apprentice>>> get() {
        List<Apprentice> list = this.apprenticeService.get();
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Apprentice>> get(@PathVariable long id) {
        Apprentice apprentice = this.apprenticeService.get(id);
        return new ResponseEntity<>(new ResponseResult<>(null, apprentice), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Apprentice>> delete(@PathVariable long id, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            long idUser = ((UserDetailsImpl) authentication.getPrincipal()).getId();
            User user = userService.get(idUser);
            if (id == idUser || user.getRole().equals("Admin")) {
                Apprentice apprentice = this.apprenticeService.delete(id);
                return new ResponseEntity<>(new ResponseResult<>(null, apprentice), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseResult<>("You don't have permissions to delete this user.", null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseResult<>("Not authenticated", null), HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<ResponseResult<Apprentice>> update(@RequestBody Apprentice apprentice, Authentication authentication) {
        if (apprentice.getId() <= 0) {
            return new ResponseEntity<>(new ResponseResult<>("Incorrect format id", null),
                    HttpStatus.BAD_REQUEST);
        }
        if (authentication != null && authentication.isAuthenticated()) {
            long idUser = ((UserDetailsImpl) authentication.getPrincipal()).getId();
            User user = userService.get(idUser);
            if (apprentice.getId() == idUser || user.getRole().equals("Admin")) {
                Apprentice apprenticeNew = this.apprenticeService.update(apprentice);
                return new ResponseEntity<>(new ResponseResult<>(null, apprenticeNew), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseResult<>("You don't have permissions to update this user.", null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseResult<>("Not authenticated", null), HttpStatus.BAD_REQUEST);
    }
}
