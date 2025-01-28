package com.artemsolovev.service;

import com.artemsolovev.model.Admin;
import com.artemsolovev.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;

    @Autowired
    public void setAdminRepository(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public void add(Admin admin) {
        try {
            this.adminRepository.save(admin);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Admin has already added!");
        }
    }

    @Override
    public Admin getByLoginAndPassword(String login, String password) {
        return this.adminRepository.findAdminByLoginAndPassword(login, password).orElseThrow(() -> new IllegalArgumentException("Admin not found"));
    }
}
