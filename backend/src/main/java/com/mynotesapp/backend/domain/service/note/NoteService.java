package com.mynotesapp.backend.domain.service.note;

import com.mynotesapp.backend.dto.note.*;

import java.util.List;

public interface NoteService {

    NoteDto create(CreateNoteDto createNoteDto);

    NoteDto update(UpdateNoteDto updateDto);

    void delete(Long noteId, Long ownerId);

    List<NoteDto> search(SearchNoteCriteriaDto criteria);

    List<NoteDto> updateOnDrag(List<UpdateNoteDto> updateDto);
}
