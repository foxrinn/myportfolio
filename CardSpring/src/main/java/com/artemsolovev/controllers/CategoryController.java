package com.artemsolovev.controllers;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.Category;
import com.artemsolovev.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "category_methods", description = "Контроллер для управления категориями.")
@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(path = "/{idUser}")
    public ResponseEntity<ResponseResult<Category>> add(@RequestBody Category category, @PathVariable long idUser) {
        try {
            this.categoryService.add(idUser, category);
            return new ResponseEntity<>(new ResponseResult<>(null, category), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{idUser}")
    public ResponseEntity<ResponseResult<List<Category>>> getByIdUser(@PathVariable long idUser) {
        List<Category> list = this.categoryService.getByIdUser(idUser);
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }

    @GetMapping(path = "/search/{id}")
    public ResponseEntity<ResponseResult<Category>> get(@PathVariable long id) {
        try {
            Category category = this.categoryService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, category), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Category>> delete(@PathVariable long id) {
        try {
            Category category = this.categoryService.delete(id);
            return new ResponseEntity<>(new ResponseResult<>(null, category), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<ResponseResult<Category>> update(@RequestBody Category category) {
        try {
            if (category.getId() <= 0) {
                return new ResponseEntity<>(new ResponseResult<>("Incorrect format id", null),
                        HttpStatus.BAD_REQUEST);
            }
            Category categoryNew = this.categoryService.update(category);
            return new ResponseEntity<>(new ResponseResult<>(null, categoryNew), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
