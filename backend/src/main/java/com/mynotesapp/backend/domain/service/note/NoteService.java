package com.mynotesapp.backend.domain.service.note;

import com.mynotesapp.backend.dto.note.CreateNoteDto;
import com.mynotesapp.backend.dto.note.NoteDto;
import com.mynotesapp.backend.dto.note.SearchNoteCriteriaDto;
import com.mynotesapp.backend.dto.note.UpdateNoteDto;

import java.util.List;
import java.util.UUID;

public interface NoteService {
    List<NoteDto> getAllByUser(String id);

    NoteDto create(CreateNoteDto createNoteDto);

    NoteDto update(UpdateNoteDto updateDto);

    void delete(String noteId, String ownerId);

    List<NoteDto> search(SearchNoteCriteriaDto criteria);

    List<UUID> convertCategoryIds(List<String> categories);
}
