package com.mynotesapp.backend.controller;

import com.mynotesapp.backend.domain.service.category.CategoryService;
import com.mynotesapp.backend.dto.category.CategoryDto;
import com.mynotesapp.backend.dto.category.CrateCategoryDto;
import com.mynotesapp.backend.util.ControllerApi;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(ControllerApi.BACKEND + ControllerApi.CATEGORIES)
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(ControllerApi.CREATE)
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CrateCategoryDto categoryDto) {
        CategoryDto category = categoryService.create(categoryDto);

        return ResponseEntity.ok().body(category);
    }

    @GetMapping(ControllerApi.ID)
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String id) {
        CategoryDto category = categoryService.getById(id);

        return ResponseEntity.ok().body(category);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAll();

        return ResponseEntity.ok().body(categories);
    }

}
