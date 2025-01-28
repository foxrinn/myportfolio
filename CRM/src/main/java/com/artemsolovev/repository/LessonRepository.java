package com.artemsolovev.repository;

import com.artemsolovev.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByStudent_Id(long idStudent);
    List<Lesson> findAllByTeacher_Id(long idTeacher);
}
