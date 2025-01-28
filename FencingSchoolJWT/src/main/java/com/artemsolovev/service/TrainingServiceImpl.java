package com.artemsolovev.service;

import com.artemsolovev.model.Trainer;
import com.artemsolovev.model.TrainerSchedule;
import com.artemsolovev.model.Training;
import com.artemsolovev.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {
    private TrainingRepository trainingRepository;
    private TrainerService trainerService;
    private ApprenticeService apprenticeService;
    @Autowired
    public void setTrainingRepository(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Autowired
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Autowired
    public void setApprenticeService(ApprenticeService apprenticeService) {
        this.apprenticeService = apprenticeService;
    }

    public static boolean isOverlapping(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }

    public static boolean isIncluding(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return (start1.isAfter(start2) || start1.equals(start2)) && (end1.isBefore(end2) || end1.equals(end2));
    }

    public boolean has3Apprentice(LocalDate localDate, LocalTime localTime, long idTrainer) {
        List<Training> allByDate = this.trainingRepository.findAllByDateAndTrainer_Id(localDate, idTrainer);
        int count = 0;
        for (Training training : allByDate) {
            if (isOverlapping(training.getTimeStart(), training.getTimeStart().plusMinutes(90), localTime,
                    localTime.plusMinutes(90)))
                count++;
            if (count >= 3)
                return false;
        }
        return true;
    }

    public boolean has10ApprenticesInGym(int numberGym, LocalDate localDate, LocalTime localTime) {
        List<Training> allByNumberGymAndDate = this.trainingRepository.findAllByNumberGymAndDate(numberGym, localDate);
        int count = 0;
        for (Training training : allByNumberGymAndDate) {
            if (isOverlapping(training.getTimeStart(), training.getTimeStart().plusMinutes(90), localTime,
                    localTime.plusMinutes(90)))
                count++;
            if (count >= 10)
                return false;
        }
        return true;
    }

    public boolean isWorking(long idTrainer, String day, LocalTime localTime) {
        LocalTime[] localTimes = this.trainerService.get(idTrainer).getTrainerSchedule().get(day);
        return isIncluding(localTime, localTime.plusMinutes(90), localTimes[0], localTimes[1]);
    }

    @Override
    public void add(Training training, long idTrainer, long idApprentice) {
        try {
            if (!isWorking(idTrainer, training.getDate().getDayOfWeek().name().toLowerCase(), training.getTimeStart()))
                throw new IllegalArgumentException("Training couldn't be added!");
            if (!has3Apprentice(training.getDate(), training.getTimeStart(), idTrainer))
                throw new IllegalArgumentException("Training couldn't be added!");
            if (!has10ApprenticesInGym(training.getNumberGym(), training.getDate(), training.getTimeStart()))
                throw new IllegalArgumentException("Training couldn't be added!");
            training.setTrainer(this.trainerService.get(idTrainer));
            training.setApprentice(this.apprenticeService.get(idApprentice));
            this.trainingRepository.save(training);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Training has already added!");
        }
    }

    @Override
    public Training get(long id) {
        return this.trainingRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Training does not exists!"));
    }

    @Override
    public List<Training> getByApprentice(long idApprentice) {
        return this.trainingRepository.findAllByApprentice_Id(idApprentice);
    }

    @Override
    public List<Training> getByTrainer(long idTrainer) {
        return this.trainingRepository.findAllByTrainer_Id(idTrainer);
    }

    @Override
    public Training delete(long id) {
        Training training = get(id);
        this.trainingRepository.deleteById(id);
        return training;
    }

    @Override
    public List<LocalTime> getByDate(long idTrainer, LocalDate localDate, int numberGym) {
        Trainer trainer = this.trainerService.get(idTrainer);

        TrainerSchedule trainerSchedule = trainer.getTrainerSchedule();
        LocalTime[] localTimes = trainerSchedule.get(localDate.getDayOfWeek().name().toLowerCase());
        List<LocalTime> localTimeList = new ArrayList<>();
        for (LocalTime i = localTimes[0]; i.isBefore(localTimes[1].minusMinutes(89)); i = i.plusMinutes(30)) {
            if (this.has3Apprentice(localDate, i, idTrainer) && this.has10ApprenticesInGym(numberGym, localDate, i))
                localTimeList.add(i);
        }
        return localTimeList;
    }
}
