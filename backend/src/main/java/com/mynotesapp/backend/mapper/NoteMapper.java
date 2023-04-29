package com.mynotesapp.backend.mapper;

import com.mynotesapp.backend.domain.entity.NoteEntity;
import com.mynotesapp.backend.dto.note.CreateNoteDto;
import com.mynotesapp.backend.dto.note.NoteDto;
import com.mynotesapp.backend.dto.note.UpdateNoteDto;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    List<NoteDto> toListDtos(List<NoteEntity> notes);

//    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "createdDate", expression = "java(getDateTime())")
    NoteEntity toEntity(CreateNoteDto createNoteDto);

    @Named("getDateTime")
    default LocalDateTime getDateTime() {
        return LocalDateTime.now();
    }

    NoteEntity toEntity(UpdateNoteDto updateNoteDto);

    @Mapping(target = "orderNumber", expression = "java(getOrderNumber(entity))")
    NoteDto toDto(NoteEntity entity);

    @Named("getOrderNumber")
    default Long getOrderNumber(NoteEntity entity) {
        return Objects.nonNull(entity.getOrderNumber()) ? entity.getOrderNumber() : null;
    }

    void updateNoteFromDto(UpdateNoteDto noteDto, @MappingTarget NoteEntity note);

}
