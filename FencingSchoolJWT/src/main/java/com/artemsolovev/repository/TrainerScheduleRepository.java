package com.artemsolovev.repository;

import com.artemsolovev.model.TrainerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerScheduleRepository extends JpaRepository<TrainerSchedule, Long> {
}
