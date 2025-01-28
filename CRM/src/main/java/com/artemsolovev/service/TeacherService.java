package com.artemsolovev.service;

import com.artemsolovev.model.Teacher;

import java.util.List;

public interface TeacherService {
    void add(Teacher teacher);
    Teacher get(long id);
    List<Teacher> get();
    Teacher update(Teacher teacher);
}
