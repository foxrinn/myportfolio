package com.artemsolovev.service;

import com.artemsolovev.model.Apprentice;
import com.artemsolovev.repository.ApprenticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprenticeServiceImpl implements ApprenticeService{

    private ApprenticeRepository apprenticeRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setApprenticeRepository(ApprenticeRepository apprenticeRepository){
        this.apprenticeRepository = apprenticeRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void add(Apprentice apprentice) {
        try {
            apprentice.setPassword(this.passwordEncoder.encode(apprentice.getPassword()));
            this.apprenticeRepository.save(apprentice);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Apprentice has already added!");
        }
    }

    @Override
    public Apprentice get(long id) {
        return this.apprenticeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Apprentice does not exists!"));
    }

    @Override
    public List<Apprentice> get() {
        return this.apprenticeRepository.findAll();
    }

    @Override
    public Apprentice delete(long id) {
        Apprentice apprentice = get(id);
        this.apprenticeRepository.deleteById(id);
        return apprentice;
    }

    @Override
    public Apprentice update(Apprentice apprentice) {
        Apprentice base = this.get(apprentice.getId());
        base.setLogin(apprentice.getLogin());
        base.setPassword(this.passwordEncoder.encode(apprentice.getPassword()));
        base.setName(apprentice.getName());
        base.setSurname(apprentice.getSurname());
        base.setPatronymic(apprentice.getPatronymic());
        base.setRegDate(apprentice.getRegDate());
        base.setPhoneNumber(apprentice.getPhoneNumber());
        try {
            this.apprenticeRepository.save(base);
            return base;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Apprentice is already exists!");
        }
    }
}
