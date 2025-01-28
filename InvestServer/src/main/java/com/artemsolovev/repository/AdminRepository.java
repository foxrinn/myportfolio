package com.artemsolovev.repository;

import com.artemsolovev.model.Admin;
import com.artemsolovev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findAdminByLoginAndPassword(String login, String password);
}
