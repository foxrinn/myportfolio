package com.artemsolovev.repository;

import com.artemsolovev.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findAllByDateAndTrainer_Id(LocalDate localDate, long idTrainer);
    List<Training> findAllByNumberGymAndDate(int numberGym, LocalDate localDate);
    List<Training> findAllByApprentice_Id(long idApprentice);
    List<Training> findAllByTrainer_Id(long idTrainer);
}
