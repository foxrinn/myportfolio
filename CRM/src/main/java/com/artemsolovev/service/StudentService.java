package com.artemsolovev.service;

import com.artemsolovev.model.Student;

import java.util.List;

public interface StudentService {
    void add(Student student, long idTeacher);
    Student get(long id);
    List<Student> getByTeacher(long idTeacher);
    List<Student> get();
    Student update(Student student);
}
