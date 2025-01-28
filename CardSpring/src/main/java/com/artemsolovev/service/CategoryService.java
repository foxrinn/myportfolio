package com.artemsolovev.service;

import com.artemsolovev.model.Category;

import java.util.List;

public interface CategoryService {
    void add(long idUser, Category category);
    List<Category> getByIdUser(long idUser);
    Category get(long id);
    Category update(Category category);
    Category delete(long id);
}
