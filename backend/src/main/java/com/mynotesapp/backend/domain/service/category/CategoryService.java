package com.mynotesapp.backend.domain.service.category;

import com.mynotesapp.backend.domain.entity.CategoryEntity;
import com.mynotesapp.backend.dto.category.CategoryDto;
import com.mynotesapp.backend.dto.category.CrateCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto create(CrateCategoryDto categoryDto);

    CategoryDto getById(String id);

    List<CategoryDto> getAll();

    CategoryEntity findById(String id);
}
