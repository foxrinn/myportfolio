package com.artemsolovev.service;

import com.artemsolovev.model.Compliment;
import com.artemsolovev.model.ComplimentUser;
import com.artemsolovev.repository.ComplimentUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplimentUserServiceImpl implements ComplimentUserService {

    private ComplimentUserRepository complimentUserRepository;

    @Autowired
    public void setComplimentUserRepository(ComplimentUserRepository complimentUserRepository) {
        this.complimentUserRepository = complimentUserRepository;
    }

    @Override
    public void add(ComplimentUser complimentUser) {
        try {
            this.complimentUserRepository.save(complimentUser);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Compliment has already added!");
        }
    }

    @Override
    public List<ComplimentUser> get(long idUser) {
        return this.complimentUserRepository.findAll();
    }

    @Override
    public void delete(long idUser) {
        this.complimentUserRepository.deleteAllByUser_Id(idUser);
    }
}
