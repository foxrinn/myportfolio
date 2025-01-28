package com.artemsolovev.service;

import com.artemsolovev.model.Teacher;
import com.artemsolovev.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    private TeacherRepository teacherRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setTeacherRepository(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void add(Teacher teacher) {
        try {
            teacher.setPassword(this.passwordEncoder.encode(teacher.getPassword()));
            this.teacherRepository.save(teacher);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Could not add this teacher");
        }
    }

    @Override
    public Teacher get(long id) {
        return this.teacherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Teacher is not exists!"));
    }

    @Override
    public List<Teacher> get() {
        return this.teacherRepository.findAll();
    }

    @Override
    public Teacher update(Teacher teacher) {
        try {
            Teacher old = this.get(teacher.getId());
            old.setLogin(teacher.getLogin());
            old.setPassword(this.passwordEncoder.encode(teacher.getPassword()));
            old.setFio(teacher.getFio());
            old.setPhone(teacher.getPhone());
            old.setExperience(teacher.getExperience());
            this.teacherRepository.save(old);
            return old;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Teacher has already added!");
        }
    }
}
