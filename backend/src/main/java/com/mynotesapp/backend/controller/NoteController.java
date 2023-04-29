package com.mynotesapp.backend.controller;

import com.mynotesapp.backend.domain.service.note.NoteService;
import com.mynotesapp.backend.dto.note.CreateNoteDto;
import com.mynotesapp.backend.dto.note.NoteDto;
import com.mynotesapp.backend.dto.note.SearchNoteCriteriaDto;
import com.mynotesapp.backend.dto.note.UpdateNoteDto;
import com.mynotesapp.backend.util.ControllerApi;
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
@RequestMapping(ControllerApi.BACKEND + ControllerApi.NOTES)
public class NoteController {

    private final NoteService noteService;

    @GetMapping(ControllerApi.BY_USER)
    public ResponseEntity<List<NoteDto>> getAllByUser(@PathVariable Long id) {
        List<NoteDto> notes = noteService.getAllByUser(id);

        return ResponseEntity.ok().body(notes);
    }

    @PostMapping(ControllerApi.CREATE)
    public ResponseEntity<NoteDto> create(@Valid @RequestBody CreateNoteDto createNoteDto) {
        NoteDto note = noteService.create(createNoteDto);

        return new ResponseEntity<>(note, HttpStatus.CREATED);
    }

    @PatchMapping(ControllerApi.UPDATE)
    public ResponseEntity<NoteDto> update(@Valid @RequestBody UpdateNoteDto updateDto) {
        NoteDto note = noteService.update(updateDto);

        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @DeleteMapping(ControllerApi.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long noteId, @PathVariable Long ownerId) {
        noteService.delete(noteId, ownerId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(ControllerApi.SEARCH)
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
}
