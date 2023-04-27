package com.mynotesapp.backend.mapper;

import com.mynotesapp.backend.domain.entity.NoteEntity;
import com.mynotesapp.backend.dto.note.CreateNoteDto;
import com.mynotesapp.backend.dto.note.NoteDto;
import com.mynotesapp.backend.dto.note.UpdateNoteDto;
import org.mapstruct.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    List<NoteDto> toListDtos(List<NoteEntity> notes);

    @Mapping(target = "owner", ignore = true)
    NoteEntity toEntity(CreateNoteDto createNoteDto);

    @Mapping(target = "id", expression = "java(getIdFromString(updateNoteDto))")
    NoteEntity toEntity(UpdateNoteDto updateNoteDto);

    @Named("getIdFromString")
    default UUID getIdFromString(UpdateNoteDto updateNoteDto) {
        return UUID.fromString(updateNoteDto.getId());
    }

    @Mapping(target = "id", expression = "java(getId(entity))")
    @Mapping(target = "orderNumber", expression = "java(getOrderNumber(entity))")
    NoteDto toDto(NoteEntity entity);

    @Named("getId")
    default String getId(NoteEntity entity) {
        return Objects.nonNull(entity.getId()) ? entity.getId().toString() : null;
    }

    @Named("getOrderNumber")
    default Long getOrderNumber(NoteEntity entity) {
        return Objects.nonNull(entity.getOrderNumber()) ? entity.getOrderNumber() : null;
    }

    void updateNoteFromDto(UpdateNoteDto noteDto, @MappingTarget NoteEntity note);

}
