package com.mynotesapp.backend.mapper;

import com.mynotesapp.backend.domain.entity.CategoryEntity;
import com.mynotesapp.backend.domain.entity.NoteEntity;
import com.mynotesapp.backend.dto.note.CreateNoteDto;
import com.mynotesapp.backend.dto.note.NoteDto;
import com.mynotesapp.backend.dto.note.UpdateNoteDto;
import com.mynotesapp.backend.dto.note.UpdateOnDragDto;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    List<NoteDto> toListDtos(List<NoteEntity> notes);

    @Mapping(target = "createdDate", expression = "java(getDateTime())")
    @Mapping(target = "orderDateTime", expression = "java(getDateTime())")
    NoteEntity toEntity(CreateNoteDto createNoteDto);

    @Named("getDateTime")
    default LocalDateTime getDateTime() {
        return LocalDateTime.now();
    }

    NoteEntity toEntity(UpdateNoteDto updateNoteDto);

    NoteDto toDto(NoteEntity entity);

    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "category", expression = "java(getCategory(noteDto))")
    void updateNoteFromDto(UpdateNoteDto noteDto, @MappingTarget NoteEntity note);

    @Named("getCategory")
    default CategoryEntity getCategory(UpdateNoteDto dto) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(dto.getCategory().getName());
        categoryEntity.setId(dto.getCategory().getId());

        return categoryEntity;
    }

    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "category", expression = "java(getCategoryDrag(updateDto))")
    @Mapping(target = "orderDateTime", expression = "java(getIncrementTime(updateDto))")
    void updateNoteFromDragDto(UpdateOnDragDto updateDto, @MappingTarget NoteEntity noteEntity);

    @Named("getCategoryDrag")
    default CategoryEntity getCategoryDrag(UpdateOnDragDto updateDto) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(updateDto.getCategory().getName());
        categoryEntity.setId(updateDto.getCategory().getId());

        return categoryEntity;
    }

    @Named("getIncrementTime")
    default LocalDateTime getIncrementTime(UpdateOnDragDto updateDto) {

        return updateDto.getIsIncrement()
                ? updateDto.getOrderDateTime().plusSeconds(1)
                : updateDto.getOrderDateTime().minusSeconds(1);
    }
}
