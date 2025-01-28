package com.artemsolovev.service;

import com.artemsolovev.model.Subject;

import java.util.List;

public interface SubjectService {

    void add(long idTeacher, Subject subject);
    List<Subject> getAll(long idTeacher);
    Subject get(long id);
    Subject delete(long id);
}
