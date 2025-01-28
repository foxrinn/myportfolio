package com.artemsolovev.service;

import com.artemsolovev.model.Trainer;
import com.artemsolovev.model.TrainerSchedule;
import com.artemsolovev.repository.TrainerScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class TrainerScheduleServiceImpl implements TrainerScheduleService {
    private TrainerScheduleRepository trainerScheduleRepository;
    private TrainerService trainerService;

    @Autowired
    public void setTrainerScheduleRepository(TrainerScheduleRepository trainerScheduleRepository){
        this.trainerScheduleRepository = trainerScheduleRepository;
    }

    @Autowired
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Override
    public TrainerSchedule add(long idTrainer, String day, LocalTime start, LocalTime end) {
        try {
            Trainer trainer = this.trainerService.get(idTrainer);

            TrainerSchedule trainerSchedule = trainer.getTrainerSchedule();
            if (trainerSchedule == null) {
                trainerSchedule = new TrainerSchedule(trainer);
            }

            trainerSchedule.set(day, start, end);
            this.trainerScheduleRepository.save(trainerSchedule);
            return trainerSchedule;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Trainer Schedule has already added!");
        }
    }

    @Override
    public TrainerSchedule delete(long idTrainer, String day) {
        Trainer trainer = this.trainerService.get(idTrainer);

        TrainerSchedule trainerSchedule = trainer.getTrainerSchedule();
        trainerSchedule.set(day, null, null);
        this.trainerScheduleRepository.save(trainerSchedule);
        return trainerSchedule;
    }

    @Override
    public TrainerSchedule get(long idTrainer) {
        this.trainerService.get(idTrainer);
        return this.trainerScheduleRepository.findById(idTrainer)
                .orElseThrow(() -> new IllegalArgumentException("TrainerSchedule does not exists"));
    }
}
