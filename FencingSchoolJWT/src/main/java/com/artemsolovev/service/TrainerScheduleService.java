package com.artemsolovev.service;

import com.artemsolovev.model.TrainerSchedule;

import java.time.LocalTime;

public interface TrainerScheduleService {
    TrainerSchedule add(long idTrainer, String day, LocalTime start, LocalTime end);
    TrainerSchedule delete(long idTrainer, String day);
    TrainerSchedule get(long idTrainer);
}
