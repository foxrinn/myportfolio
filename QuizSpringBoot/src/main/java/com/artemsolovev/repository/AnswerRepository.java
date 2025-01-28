package com.artemsolovev.repository;

import com.artemsolovev.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByQuizUser_Id(long idUser);

    Optional<Answer> findTopByResult_Id(long idResult);
}
