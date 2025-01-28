package com.artemsolovev.service;

import com.artemsolovev.model.Admin;
import com.artemsolovev.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setAdminRepository(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void add(Admin admin) {
        try {
            admin.setPassword(this.passwordEncoder.encode(admin.getPassword()));
            this.adminRepository.save(admin);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Admin has already added!");
        }
    }

    @Override
    public Admin get(long id) {
        return this.adminRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Admin does not exists!"));
    }

    @Override
    public Admin delete(long id) {
        Admin admin = get(id);
        this.adminRepository.deleteById(id);
        return admin;
    }

    @Override
    public Admin update(Admin admin) {
        Admin base = this.get(admin.getId());
        base.setLogin(admin.getLogin());
        base.setPassword(this.passwordEncoder.encode(admin.getPassword()));
        base.setName(admin.getName());
        base.setSurname(admin.getSurname());
        base.setPatronymic(admin.getPatronymic());
        base.setRegDate(admin.getRegDate());
        base.setEmail(admin.getEmail());
        base.setSalary(admin.getSalary());
        try {
            this.adminRepository.save(base);
            return base;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Admin is already exists!");
        }
    }


}
