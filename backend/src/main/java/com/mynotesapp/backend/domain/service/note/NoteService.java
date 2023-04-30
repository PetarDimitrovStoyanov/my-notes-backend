package com.mynotesapp.backend.domain.service.note;

import com.mynotesapp.backend.dto.note.*;

import java.util.List;

public interface NoteService {
    List<NoteDto> getAllByUser(Long id);

    NoteDto create(CreateNoteDto createNoteDto);

    NoteDto update(UpdateNoteDto updateDto);

    void delete(Long noteId, Long ownerId);

    List<NoteDto> search(SearchNoteCriteriaDto criteria);

    NoteDto updateOnDrag(UpdateOnDragDto updateDto);
}
