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
import java.util.List;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    private final NoteMapper mapper;

//    private final UserService userService;
//
//    private final CategoryService categoryService;

    @Override
    public List<NoteDto> getAllByUser(Long id) {
        List<NoteEntity> notes = noteRepository.findAllByOwnerId(id);

        return mapper.toListDtos(notes);
    }

    @Override
    @Transactional
    public NoteDto create(CreateNoteDto createNoteDto) {
        NoteEntity note = mapper.toEntity(createNoteDto);
//        note.setOwner(userService.findById(createNoteDto.getOwner().getId()));
//        note.setCategory(categoryService.findById(createNoteDto.getCategory().getId()));
//        note.setCreatedDate(LocalDateTime.now());

        return mapper.toDto(noteRepository.save(note));
    }

    @Override
    @Transactional
    public NoteDto update(UpdateNoteDto updateDto) {
        NoteEntity noteEntity = getNoteEntityByIdAndOwner(updateDto.getId(), updateDto.getOwner().getId());
        mapper.updateNoteFromDto(updateDto, noteEntity);

        return mapper.toDto(noteRepository.save(noteEntity));
    }

    @Override
    public void delete(Long noteId, Long ownerId) {
        NoteEntity noteEntity = getNoteEntityByIdAndOwner(noteId, ownerId);

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

    private NoteEntity getNoteEntityByIdAndOwner(Long noteId, Long ownerId) {

        return noteRepository
                .findByIdAndOwnerId(noteId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(noteId, NoteEntity.class.getSimpleName()));
    }
}
