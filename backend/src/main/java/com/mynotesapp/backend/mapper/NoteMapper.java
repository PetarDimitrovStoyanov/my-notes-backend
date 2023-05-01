package com.mynotesapp.backend.mapper;

import com.mynotesapp.backend.domain.entity.CategoryEntity;
import com.mynotesapp.backend.domain.entity.NoteEntity;
import com.mynotesapp.backend.dto.note.CreateNoteDto;
import com.mynotesapp.backend.dto.note.NoteDto;
import com.mynotesapp.backend.dto.note.UpdateNoteDto;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    List<NoteDto> toListDtos(List<NoteEntity> notes);

    @Mapping(target = "createdDate", expression = "java(getDateTime())")
    @Mapping(target = "orderNumber", expression = "java(getOrderNumber())")
    NoteEntity toEntity(CreateNoteDto createNoteDto);

    @Mapping(target = "owner", ignore = true)
    List<NoteEntity> toEntities(List<UpdateNoteDto> dtos);

    @Named("getDateTime")
    default LocalDateTime getDateTime() {
        return LocalDateTime.now();
    }

    @Named("getOrderNumber")
    default Long getOrderNumber() {
        return -1L;
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
}
