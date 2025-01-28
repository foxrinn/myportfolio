package com.artemsolovev.service;

import com.artemsolovev.model.Trainer;
import com.artemsolovev.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {

    private TrainerRepository trainerRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setTrainerRepository(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void add(Trainer trainer) {
        try {
            trainer.setPassword(this.passwordEncoder.encode(trainer.getPassword()));
            this.trainerRepository.save(trainer);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Trainer has already added!");
        }
    }

    @Override
    public Trainer get(long id) {
        return this.trainerRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Trainer does not exists!"));
    }

    @Override
    public List<Trainer> get() {
        return this.trainerRepository.findAll();
    }

    @Override
    public Trainer delete(long id) {
        Trainer trainer = get(id);
        this.trainerRepository.deleteById(id);
        return trainer;
    }

    @Override
    public Trainer update(Trainer trainer) {
        Trainer base = this.get(trainer.getId());
        base.setLogin(trainer.getLogin());
        base.setPassword(this.passwordEncoder.encode(trainer.getPassword()));
        base.setName(trainer.getName());
        base.setSurname(trainer.getSurname());
        base.setPatronymic(trainer.getPatronymic());
        base.setRegDate(trainer.getRegDate());
        base.setExperience(trainer.getExperience());
        base.setEmail(trainer.getEmail());
        try {
            this.trainerRepository.save(base);
            return base;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Trainer is already exists!");
        }
    }
}
