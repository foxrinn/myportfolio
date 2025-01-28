package com.artemsolovev.service;

import com.artemsolovev.model.Training;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TrainingService {
    void add(Training training, long idTrainer, long idApprentice);
    Training get(long id);
    List<Training> getByApprentice(long idApprentice);
    List<Training> getByTrainer(long idTrainer);
    Training delete(long id);
    List<LocalTime> getByDate(long idTrainer, LocalDate localDate, int numberGym);
}
