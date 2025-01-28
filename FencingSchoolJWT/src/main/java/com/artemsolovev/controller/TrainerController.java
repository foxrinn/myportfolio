package com.artemsolovev.controller;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.Trainer;
import com.artemsolovev.model.User;
import com.artemsolovev.security.jwt.UserDetailsImpl;
import com.artemsolovev.service.TrainerService;
import com.artemsolovev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainer")
public class TrainerController {
    private TrainerService trainerService;
    private UserService userService;

    @Autowired
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseResult<Trainer>> add(@RequestBody Trainer trainer) {
        this.trainerService.add(trainer);
        return new ResponseEntity<>(new ResponseResult<>(null, trainer), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseResult<List<Trainer>>> get() {
        List<Trainer> list = this.trainerService.get();
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Trainer>> get(@PathVariable long id, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            long idUser = ((UserDetailsImpl) authentication.getPrincipal()).getId();
            User user = userService.get(idUser);
            if (id == idUser || !user.getRole().equals("Trainer")) {
                Trainer trainer = this.trainerService.get(id);
                return new ResponseEntity<>(new ResponseResult<>(null, trainer), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseResult<>("You don't have permissions to get this user.", null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseResult<>("Not authenticated", null), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Trainer>> delete(@PathVariable long id, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            long idUser = ((UserDetailsImpl) authentication.getPrincipal()).getId();
            User user = userService.get(idUser);
            if (id == idUser || user.getRole().equals("Admin")) {
                Trainer trainer = this.trainerService.delete(id);
                return new ResponseEntity<>(new ResponseResult<>(null, trainer), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseResult<>("You don't have permissions to delete this user.", null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseResult<>("Not authenticated", null), HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<ResponseResult<Trainer>> update(@RequestBody Trainer trainer, Authentication authentication) {
        if (trainer.getId() <= 0) {
            return new ResponseEntity<>(new ResponseResult<>("Incorrect format id", null),
                    HttpStatus.BAD_REQUEST);
        }
        if (authentication != null && authentication.isAuthenticated()) {
            long idUser = ((UserDetailsImpl) authentication.getPrincipal()).getId();
            User user = userService.get(idUser);
            if (trainer.getId() == idUser || user.getRole().equals("Admin")) {
                Trainer trainerNew = this.trainerService.update(trainer);
                return new ResponseEntity<>(new ResponseResult<>(null, trainerNew), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseResult<>("You don't have permissions to update this user.", null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseResult<>("Not authenticated", null), HttpStatus.BAD_REQUEST);
    }
}
