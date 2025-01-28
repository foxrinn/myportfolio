package com.artemsolovev.controller;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.TrainerSchedule;
import com.artemsolovev.model.User;
import com.artemsolovev.security.jwt.UserDetailsImpl;
import com.artemsolovev.service.TrainerScheduleService;
import com.artemsolovev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/trainerschedule")
public class TrainerScheduleController {
    private TrainerScheduleService trainerScheduleService;
    private UserService userService;

    @Autowired
    public void setTrainerScheduleService(TrainerScheduleService trainerScheduleService) {
        this.trainerScheduleService = trainerScheduleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseResult<TrainerSchedule>> add(@RequestParam long idTrainer, @RequestParam String day,
                                                               @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime start,
                                                               @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime end,
                                                               Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            long idUser = ((UserDetailsImpl) authentication.getPrincipal()).getId();
            User user = userService.get(idUser);
            if (idTrainer == idUser || user.getRole().equals("Admin")) {
                TrainerSchedule trainerSchedule = this.trainerScheduleService.add(idTrainer, day, start, end);
                return new ResponseEntity<>(new ResponseResult<>(null, trainerSchedule), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseResult<>("You don't have permissions to add this schedule.", null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseResult<>("Not authenticated", null), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<TrainerSchedule>> get(@PathVariable long id) {
        TrainerSchedule trainerSchedule = this.trainerScheduleService.get(id);
        return new ResponseEntity<>(new ResponseResult<>(null, trainerSchedule), HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<ResponseResult<TrainerSchedule>> delete(@RequestParam long idTrainer, @RequestParam String day,
                                                                  Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            long idUser = ((UserDetailsImpl) authentication.getPrincipal()).getId();
            User user = userService.get(idUser);
            if (idTrainer == idUser || user.getRole().equals("Admin")) {
                TrainerSchedule trainerSchedule = this.trainerScheduleService.delete(idTrainer, day);
                return new ResponseEntity<>(new ResponseResult<>(null, trainerSchedule), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseResult<>("You don't have permissions to delete this schedule.", null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseResult<>("Not authenticated", null), HttpStatus.BAD_REQUEST);
    }
}
