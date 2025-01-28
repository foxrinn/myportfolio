package com.artemsolovev.repository;

import com.artemsolovev.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> getStudentsByTeacher_Id(long idTeacher);
    Optional<Student> findByLoginAndPassword(String login, String pass);
}
