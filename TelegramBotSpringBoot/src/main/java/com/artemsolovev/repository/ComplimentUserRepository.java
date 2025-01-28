package com.artemsolovev.repository;

import com.artemsolovev.model.ComplimentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ComplimentUserRepository extends JpaRepository<ComplimentUser,Long> {
    void deleteAllByUser_Id(long idUser);
}
