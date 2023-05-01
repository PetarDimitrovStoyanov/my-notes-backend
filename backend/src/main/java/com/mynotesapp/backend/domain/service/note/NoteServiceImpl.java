package com.mynotesapp.backend.domain.service.note;

import com.mynotesapp.backend.domain.entity.NoteEntity;
import com.mynotesapp.backend.domain.repository.NoteRepository;
import com.mynotesapp.backend.dto.note.*;
import com.mynotesapp.backend.exception.EntityNotFoundException;
import com.mynotesapp.backend.mapper.NoteMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    private final NoteMapper mapper;

    @Override
    @Transactional
    public NoteDto create(CreateNoteDto createNoteDto) {
        NoteEntity note = mapper.toEntity(createNoteDto);

        return mapper.toDto(noteRepository.save(note));
    }

    @Override
    @Transactional
    public NoteDto update(UpdateNoteDto updateDto) {
        NoteEntity noteEntity = getNoteEntityByIdAndOwner(updateDto.getId(), updateDto.getOwner().getId());
        mapper.updateNoteFromDto(updateDto, noteEntity);
        NoteEntity updatedEntity = noteRepository.save(noteEntity);

        return mapper.toDto(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(Long noteId, Long ownerId) {
        NoteEntity noteEntity = getNoteEntityByIdAndOwner(noteId, ownerId);

        noteRepository.delete(noteEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoteDto> search(SearchNoteCriteriaDto criteria) {
        SearchNoteSpecification specification = new SearchNoteSpecification(criteria);
        PageRequest of = PageRequest.of(
                criteria.getPageable().getPageNumber(),
                criteria.getPageable().getPageSize(),
                criteria.getSort()
        );
        Page<NoteEntity> notes = noteRepository.findAll(specification, of);

        return mapper.toListDtos(notes.getContent());
    }

    @Override
    @Transactional
    public List<NoteDto> updateOnDrag(List<UpdateNoteDto> dtos) {
        List<NoteEntity> entities = mapper.toEntities(dtos);
        List<NoteEntity> noteEntities = noteRepository.saveAll(entities);

        return mapper.toListDtos(noteEntities);
    }

    private NoteEntity getNoteEntityByIdAndOwner(Long noteId, Long ownerId) {

        return noteRepository
                .findByIdAndOwnerId(noteId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(noteId, NoteEntity.class.getSimpleName()));
    }
}
