package com.artemsolovev.repository;

import com.artemsolovev.model.WorkDaySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkDayScheduleRepository extends JpaRepository<WorkDaySchedule, Long> {
    Optional<WorkDaySchedule> findByDateAndTeacher_Id(LocalDate date, long idTeacher);
    List<WorkDaySchedule> findAllByTeacher_Id(long idTeacher);
}
