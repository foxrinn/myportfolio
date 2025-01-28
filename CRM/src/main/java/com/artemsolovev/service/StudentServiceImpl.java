package com.artemsolovev.service;

import com.artemsolovev.model.Student;
import com.artemsolovev.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private TeacherService teacherService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void add(Student student, long idTeacher) {
        try {
            student.setTeacher(this.teacherService.get(idTeacher));
            student.setPassword(this.passwordEncoder.encode(student.getPassword()));
            this.studentRepository.save(student);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Could not add this student");
        }
    }

    @Override
    public Student get(long id) {
        return this.studentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Student does not exists!"));
    }

    @Override
    public List<Student> getByTeacher(long idTeacher) {
        return this.studentRepository.getStudentsByTeacher_Id(idTeacher);
    }

    @Override
    public List<Student> get() {
        return this.studentRepository.findAll();
    }

    @Override
    public Student update(Student student) {
        Student base = this.get(student.getId());
        base.setFio(student.getFio());
        base.setLogin(student.getLogin());
        base.setPassword(this.passwordEncoder.encode(student.getPassword()));
        base.setPhone(student.getPhone());
        try {
            this.studentRepository.save(base);
            return base;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Student is already exists!");
        }
    }


}
