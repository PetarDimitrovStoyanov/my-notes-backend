package com.mynotesapp.backend.domain.service.note;

import com.mynotesapp.backend.domain.entity.NoteEntity;
import com.mynotesapp.backend.domain.repository.NoteRepository;
import com.mynotesapp.backend.domain.service.category.CategoryService;
import com.mynotesapp.backend.domain.service.user.UserService;
import com.mynotesapp.backend.dto.note.CreateNoteDto;
import com.mynotesapp.backend.dto.note.NoteDto;
import com.mynotesapp.backend.dto.note.SearchNoteCriteriaDto;
import com.mynotesapp.backend.dto.note.UpdateNoteDto;
import com.mynotesapp.backend.exception.EntityNotFoundException;
import com.mynotesapp.backend.mapper.NoteMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    private final NoteMapper mapper;

    private final UserService userService;

    private final CategoryService categoryService;

    @Override
    public List<NoteDto> getAllByUser(String id) {
        List<NoteEntity> notes = noteRepository.findAllByOwnerId(UUID.fromString(id));

        return mapper.toListDtos(notes);
    }

    @Override
    @Transactional
    public NoteDto create(CreateNoteDto createNoteDto) {
        NoteEntity note = mapper.toEntity(createNoteDto);
        note.setOwner(userService.findById(createNoteDto.getOwner().getId()));
        note.setCategory(categoryService.findById(createNoteDto.getCategory().getId()));
        note.setCreatedDate(LocalDateTime.now());

        return mapper.toDto(noteRepository.save(note));
    }

    @Override
    @Transactional
    public NoteDto update(UpdateNoteDto updateDto) {
        UUID noteId = UUID.fromString(updateDto.getId());
        UUID ownerId = UUID.fromString(updateDto.getOwner().getId());
        NoteEntity noteEntity = getNoteEntityByIdAndOwner(noteId, ownerId);
        mapper.updateNoteFromDto(updateDto, noteEntity);

        return mapper.toDto(noteRepository.save(noteEntity));
    }

    @Override
    public void delete(String noteId, String ownerId) {
        UUID noteEntityId = UUID.fromString(noteId);
        UUID ownerEntityId = UUID.fromString(ownerId);
        NoteEntity noteEntity = getNoteEntityByIdAndOwner(noteEntityId, ownerEntityId);

        noteRepository.delete(noteEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoteDto> search(SearchNoteCriteriaDto criteria) {
        SearchNoteSpecification specification = new SearchNoteSpecification(criteria);
        Pageable pageable = criteria.getPageable();
        Page<NoteEntity> notes = noteRepository.findAll(specification, pageable);

        return mapper.toListDtos(notes.getContent());
    }

    @Override
    public List<UUID> convertCategoryIds(List<String> categories) {

        return Objects.nonNull(categories) && !categories.isEmpty()
                ? categories.stream().map(UUID::fromString).collect(Collectors.toList())
                : null;
    }

    private NoteEntity getNoteEntityByIdAndOwner(UUID noteId, UUID ownerId) {

        return noteRepository
                .findByIdAndOwnerId(noteId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(noteId.toString(), NoteEntity.class.getSimpleName()));
    }
}
