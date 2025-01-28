package com.artemsolovev.repository;

import com.artemsolovev.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByCategory_Id(long idCategory);
}
