package com.artemsolovev.service;

import com.artemsolovev.model.Category;
import com.artemsolovev.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;
    private UserService userService;


    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void add(long idUser, Category category) {
        try {
            category.setUser(this.userService.get(idUser));
            this.categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Category has already added!");
        }
    }

    @Override
    public List<Category> getByIdUser(long idUser) {
        return this.categoryRepository.findByUser_Id(idUser);
    }

    @Override
    public Category get(long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category does not found!"));
    }

    @Override
    public Category update(Category category) {
        try {
            Category old = this.get(category.getId());
            old.setName(category.getName());
            this.categoryRepository.save(old);
            return old;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Category has already added!");
        }
    }

    @Override
    public Category delete(long id) {
        Category category = this.get(id);
        this.categoryRepository.deleteById(id);
        return category;
    }

}
