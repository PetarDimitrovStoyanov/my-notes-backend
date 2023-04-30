package com.mynotesapp.backend.dto.note;

import com.mynotesapp.backend.dto.category.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {

    private Long id;

    private String title;

    private String text;

    private Boolean isImportant;

    private LocalDateTime orderDateTime;

    private LocalDateTime createdDate;

    private CategoryDto category;
}
