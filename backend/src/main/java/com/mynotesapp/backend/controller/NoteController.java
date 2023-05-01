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
            @RequestParam(value = "ownerId", required = false) Long ownerId,
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "orderNumber", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "id", direction = Sort.Direction.DESC)
            }) Sort sort,
            @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable
    ) {
        List<NoteDto> notes = noteService.search(SearchNoteCriteriaDto.builder()
                .searchText(searchText)
                .isImportant(isImportant)
                .categories(categories)
                .ownerId(ownerId)
                .pageable(pageable)
                .sort(sort)
                .build()
        );

        return ResponseEntity.ok().body(notes);
    }

    @PatchMapping(Constants.UPDATE_ON_DRAG)
    public ResponseEntity<List<NoteDto>> updateOnDrag(@RequestBody List<@Valid UpdateNoteDto> notes) {
        List<NoteDto> note = noteService.updateOnDrag(notes);

        return new ResponseEntity<>(note, HttpStatus.OK);
    }
}
