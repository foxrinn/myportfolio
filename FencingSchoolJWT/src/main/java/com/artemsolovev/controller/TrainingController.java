package com.artemsolovev.controller;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.Training;
import com.artemsolovev.model.User;
import com.artemsolovev.security.jwt.UserDetailsImpl;
import com.artemsolovev.service.TrainingService;
import com.artemsolovev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/training")
public class TrainingController {
    private TrainingService trainingService;
    private UserService userService;
    @Autowired
    public void setTrainingService(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseResult<Training>> add(@RequestBody Training training,
                                                        @RequestParam long idTrainer, @RequestParam long idApprentice,
                                                        Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            long idUser = ((UserDetailsImpl) authentication.getPrincipal()).getId();
            User user = userService.get(idUser);
            if (idTrainer == idUser || idApprentice == idUser || user.getRole().equals("Admin")) {
                this.trainingService.add(training, idTrainer, idApprentice);
                return new ResponseEntity<>(new ResponseResult<>(null, training), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseResult<>("You don't have permissions to add this training.", null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseResult<>("Not authenticated", null), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Training>> get(@PathVariable long id) {
        Training training = this.trainingService.get(id);
        return new ResponseEntity<>(new ResponseResult<>(null, training), HttpStatus.OK);
    }

    @GetMapping(path = "/apprentice/{id}")
    public ResponseEntity<ResponseResult<List<Training>>> getByApprentice(@PathVariable long id) {
        List<Training> list = this.trainingService.getByApprentice(id);
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseResult<List<Training>>> getByTrainer(@RequestParam long idTrainer) {
        List<Training> list = this.trainingService.getByTrainer(idTrainer);
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }

    @GetMapping(path = "/date")
    public ResponseEntity<ResponseResult<List<LocalTime>>> getByDate(@RequestParam long idTrainer,
                                                                     @RequestParam
                                                                     @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate localDate, @RequestParam int numberGym) {
        List<LocalTime> localTimeList = this.trainingService.getByDate(idTrainer, localDate, numberGym);
        return new ResponseEntity<>(new ResponseResult<>(null, localTimeList), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Training>> delete(@PathVariable long id, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            long idUser = ((UserDetailsImpl) authentication.getPrincipal()).getId();
            User user = userService.get(idUser);
            Training trainingById = trainingService.get(id);
            if (trainingById.getTrainer().getId() == idUser || trainingById.getApprentice().getId() == idUser || user.getRole().equals("Admin")) {
                Training training = this.trainingService.delete(id);
                return new ResponseEntity<>(new ResponseResult<>(null, training), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseResult<>("You don't have permissions to add this training.", null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseResult<>("Not authenticated", null), HttpStatus.BAD_REQUEST);
    }
}
