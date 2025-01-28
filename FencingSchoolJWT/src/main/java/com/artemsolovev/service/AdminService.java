package com.artemsolovev.service;

import com.artemsolovev.model.Admin;

public interface AdminService {
    void add(Admin admin);
    Admin get(long id);
    Admin delete(long id);
    Admin update(Admin admin);
}
