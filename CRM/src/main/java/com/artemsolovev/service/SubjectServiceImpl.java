package com.artemsolovev.service;

import com.artemsolovev.model.Subject;
import com.artemsolovev.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    private SubjectRepository subjectRepository;
    private TeacherService teacherService;

    @Autowired
    public void setSubjectRepository(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    
    @Override
    public void add(long idTeacher, Subject subject) {
        subject.setTeacher(teacherService.get(idTeacher));
        this.subjectRepository.save(subject);
    }

    @Override
    public List<Subject> getAll(long idTeacher) {
        return this.subjectRepository.findAll();
    }

    @Override
    public Subject get(long id) {
        return this.subjectRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Subject does not exists!"));
    }

    @Override
    public Subject delete(long id) {
        Subject subject = get(id);
        this.subjectRepository.deleteById(id);
        return subject;
    }
}
