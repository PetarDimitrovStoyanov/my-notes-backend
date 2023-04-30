package com.mynotesapp.backend.controller;

import com.mynotesapp.backend.domain.service.note.NoteService;
import com.mynotesapp.backend.dto.note.*;
import com.mynotesapp.backend.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(Constants.BACKEND + Constants.NOTES)
public class NoteController {

    private final NoteService noteService;

    @GetMapping(Constants.BY_USER)
    public ResponseEntity<List<NoteDto>> getAllByUser(@PathVariable Long id) {
        List<NoteDto> notes = noteService.getAllByUser(id);

        return ResponseEntity.ok().body(notes);
    }

    @PostMapping(Constants.CREATE)
    public ResponseEntity<NoteDto> create(@Valid @RequestBody CreateNoteDto createNoteDto) {
        NoteDto note = noteService.create(createNoteDto);

        return new ResponseEntity<>(note, HttpStatus.CREATED);
    }

    @PatchMapping(Constants.UPDATE)
    public ResponseEntity<NoteDto> update(@Valid @RequestBody UpdateNoteDto updateDto) {
        NoteDto note = noteService.update(updateDto);

        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @DeleteMapping(Constants.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long noteId, @PathVariable Long ownerId) {
        noteService.delete(noteId, ownerId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(Constants.SEARCH)
    public ResponseEntity<List<NoteDto>> search(
            @RequestParam(name = "text", required = false) String searchText,
            @RequestParam(value = "isImportant", required = false) Boolean isImportant,
            @RequestParam(value = "categories", required = false) List<Long> categories,
            @SortDefault(sort = "orderNumber", direction = Sort.Direction.DESC) Sort sort,
            @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable
    ) {
        List<NoteDto> notes = noteService.search(SearchNoteCriteriaDto.builder()
                .searchText(searchText)
                .isImportant(isImportant)
                .categories(categories)
                .pageable(pageable)
                .sort(sort)
                .build()
        );

        return ResponseEntity.ok().body(notes);
    }

    @PatchMapping(Constants.UPDATE_ON_DRAG)
    public ResponseEntity<NoteDto> updateOnDrag(@Valid @RequestBody UpdateOnDragDto updateDto) {
        NoteDto note = noteService.updateOnDrag(updateDto);

        return new ResponseEntity<>(note, HttpStatus.OK);
    }
}
