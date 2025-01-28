package com.artemsolovev.repository;

import com.artemsolovev.model.Request;
import com.artemsolovev.model.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByUser_Id(long idUser);
    List<Request> findRequestBySolution(Solution solution);
    List<Request> findRequestBySolutionAndUser_id(Solution solution, long idUser);
}
