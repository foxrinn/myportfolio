package com.artemsolovev.repository;

import com.artemsolovev.model.InvestProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestPropertyRepository extends JpaRepository<InvestProperty, Long> {
}
