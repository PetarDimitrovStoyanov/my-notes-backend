package com.mynotesapp.backend.domain.service.category;

import com.mynotesapp.backend.domain.entity.CategoryEntity;
import com.mynotesapp.backend.domain.repository.CategoryRepository;
import com.mynotesapp.backend.dto.category.CategoryDto;
import com.mynotesapp.backend.dto.category.CrateCategoryDto;
import com.mynotesapp.backend.exception.EntityNotFoundException;
import com.mynotesapp.backend.mapper.CategoryMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper mapper;

    @Override
    public CategoryDto create(CrateCategoryDto categoryDto) {
        CategoryEntity category = mapper.toEntity(categoryDto);

        return mapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto getById(String id) {
        CategoryEntity categoryEntity = findById(id);

        return mapper.toDto(categoryEntity);
    }

    @Override
    public CategoryEntity findById(String id) {

        return categoryRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException(id, CategoryEntity.class.getSimpleName()));
    }

    @Override
    public List<CategoryDto> getAll() {
        List<CategoryEntity> categories = categoryRepository.findAll();

        return mapper.toDtoList(categories);
    }

}
