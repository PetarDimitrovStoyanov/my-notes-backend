package com.mynotesapp.backend.mapper;

import com.mynotesapp.backend.domain.entity.CategoryEntity;
import com.mynotesapp.backend.dto.category.CategoryDto;
import com.mynotesapp.backend.dto.category.CrateCategoryDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryEntity toEntity(CrateCategoryDto categoryDto);

    CategoryDto toDto(CategoryEntity entity);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    List<CategoryDto> toDtoList(List<CategoryEntity> entities);
}
