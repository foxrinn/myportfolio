package com.artemsolovev.repository;

import com.artemsolovev.model.HistoryUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryUserRepository extends JpaRepository<HistoryUser, Long> {
}
